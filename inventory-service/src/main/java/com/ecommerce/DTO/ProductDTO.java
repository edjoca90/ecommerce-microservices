package com.ecommerce.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer quantity;
}