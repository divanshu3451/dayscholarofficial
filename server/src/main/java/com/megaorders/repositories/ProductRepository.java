package com.megaorders.repositories;

import com.megaorders.models.Product;
import com.megaorders.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByNameAndCategory(String name, ProductCategory category);
}
