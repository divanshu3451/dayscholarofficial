package com.megaorders.repositories;

import com.megaorders.models.Item;
import com.megaorders.models.Product;
import com.megaorders.models.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByStatus(DeliveryStatus status);

    Optional<Item> findBySerialNumber(String serialNumber);

    List<Item> findByProduct(Product product);
}