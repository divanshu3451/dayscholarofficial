package com.megaorders.services;

import com.megaorders.dtos.ProductStatsDTO;
import com.megaorders.models.Order;
import com.megaorders.models.Product;
import com.megaorders.models.ProductTotalRevenue;
import com.megaorders.repositories.OrderRepository;
import com.megaorders.repositories.Product30DaysRevenueRepository;
import com.megaorders.repositories.ProductTotalRevenueRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@NoArgsConstructor(force = true)
public class OncePerDayDBUpdateService {
    private final Product30DaysRevenueRepository product30DaysRevenueRepository;
    private final OrderRepository orderRepository;
    private final ProductTotalRevenueRepository productTotalRevenueRepository;

    @Autowired
    public OncePerDayDBUpdateService(Product30DaysRevenueRepository product30DaysRevenueRepository, OrderRepository orderRepository, ProductTotalRevenueRepository productTotalRevenueRepository) {
        this.product30DaysRevenueRepository = product30DaysRevenueRepository;
        this.orderRepository = orderRepository;
        this.productTotalRevenueRepository = productTotalRevenueRepository;
    }

    private void updateTotalRevenue() {
        List<Order> firstDayOrders = orderRepository.findByOrderDate(LocalDate.now().minusDays(1));

        Map<Product, ProductStatsDTO> firstDayProductQtyMap = new HashMap<>();
        firstDayOrders.forEach(order -> {
            order.getOrderedProducts().forEach(orderedProduct -> {
                Product product = orderedProduct.getProduct();
                Double revenue = orderedProduct.getPrice();
                Long volumeSold = orderedProduct.getQty();

                ProductStatsDTO productStats = firstDayProductQtyMap.get(product);

                if (productStats == null) {
                    // No stats yet for this product, create new
                    firstDayProductQtyMap.put(product, new ProductStatsDTO(volumeSold, revenue));
                } else {
                    // Update existing stats
                    productStats.setRevenue(productStats.getRevenue() + revenue);
                    productStats.setVolumeSold(productStats.getVolumeSold() + volumeSold);
                }
            });
        });

        for (Map.Entry<Product, ProductStatsDTO> entry : firstDayProductQtyMap.entrySet()) {
            Product product = entry.getKey();
            ProductStatsDTO productStats = entry.getValue();

            ProductTotalRevenue productTotalRevenue = productTotalRevenueRepository.findByProduct(product).orElseThrow(RuntimeException::new);

            productTotalRevenue.setRevenue(productTotalRevenue.getRevenue() + productStats.getRevenue());
            productTotalRevenue.setVolumeSold(productTotalRevenue.getVolumeSold() + productStats.getVolumeSold());
        }
    }
}
