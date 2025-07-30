package com.ecommerce.inventory_service.DTO;

import lombok.Data;

@Data
public class StockRequest {
    private Integer productId;
    private int quantity;
}