package com.ecommerce.inventory_service.DTO;


import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String description;
}