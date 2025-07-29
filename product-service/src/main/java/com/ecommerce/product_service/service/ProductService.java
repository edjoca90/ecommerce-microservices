package com.ecommerce.product_service.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.product_service.model.Product;

public interface ProductService {
    List<Product> listAll();
    Optional<Product> findById(Integer id);
    Product create(Product producto);
    Product update(Integer id, Product producto);
    void delete(Integer id);
}