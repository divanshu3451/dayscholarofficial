package com.megaorders.repositories;

import com.megaorders.models.OrderProduct;
import com.megaorders.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findByProduct(Product product);
}