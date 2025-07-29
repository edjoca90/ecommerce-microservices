package com.ecommerce.DTO;

import lombok.Data;

@Data
public class StockRequest {
    private Integer productId;
    private int quantity;
}