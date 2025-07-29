package com.ecommerce.inventory_service.DTO;

import com.ecommerce.inventory_service.model.Purchase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseResponseDTO {
    private Purchase purchase;
    private ProductDTO product;
    
}
