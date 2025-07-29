package com.ecommerce.inventory_service.service;


import java.util.List;

import com.ecommerce.DTO.ProductDTO;
import com.ecommerce.inventory_service.model.Inventory;

public interface InventoryService {
    
    List<Inventory> listAll();
    ProductDTO findByProductId(Integer productId);
    Inventory save(Inventory inventory);
    boolean disccountStock(Integer productId, int quantity);
    void delete(Integer id);
}
