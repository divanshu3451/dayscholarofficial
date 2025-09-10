package com.megaorders.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "product_supplier")
public class ProductSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double costPrice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSupplier", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<StockHistory> stocks = new ArrayList<>();

    public Boolean addStock(StockHistory stockHistory) {
        stockHistory.setProductSupplier(this);
        return this.stocks.add(stockHistory);
    }

    public Boolean removeStock(StockHistory stockHistory) {
        stockHistory.setProductSupplier(null);
        return this.stocks.remove(stockHistory);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSupplier", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductCostPriceHistory> productCostPrices = new ArrayList<>();

    public Boolean addProductCostPrice(ProductCostPriceHistory productCostPriceHistory) {
        productCostPriceHistory.setProductSupplier(this);
        return this.productCostPrices.add(productCostPriceHistory);
    }

    public Boolean removeProductCostPrice(ProductCostPriceHistory productCostPriceHistory) {
        productCostPriceHistory.setProductSupplier(null);
        return this.productCostPrices.remove(productCostPriceHistory);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productSupplier", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    public Boolean addItem(Item item) {
        item.setProductSupplier(this);
        return this.items.add(item);
    }

    public Boolean removeItem(Item item) {
        item.setProductSupplier(null);
        return this.items.remove(item);
    }
}
