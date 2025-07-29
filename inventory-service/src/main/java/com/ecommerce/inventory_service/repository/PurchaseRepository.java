package com.ecommerce.inventory_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.inventory_service.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findByProductId(Integer productId);

    @Query("SELECT SUM(c.purchaseQuantity) FROM Purchases c WHERE c.productId = :productId")
    Integer totalByProduct(Integer productId);

}
