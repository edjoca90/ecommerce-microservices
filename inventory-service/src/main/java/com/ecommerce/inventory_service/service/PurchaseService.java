package com.ecommerce.inventory_service.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.inventory_service.DTO.ProductDTO;
import com.ecommerce.inventory_service.DTO.PurchaseResponseDTO;
import com.ecommerce.inventory_service.model.Purchase;

public interface PurchaseService {
    List<Purchase> listAll();
    Optional<Purchase> getById(Integer id);
    List<Purchase> getByProduct(Integer productId);
    PurchaseResponseDTO newPurchase(Purchase purchase);
    // Integer getStockByProduct(Integer productId);
    ProductDTO getProductById(Integer productId);
}
