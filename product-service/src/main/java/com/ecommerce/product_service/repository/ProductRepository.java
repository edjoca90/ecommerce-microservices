package com.ecommerce.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import  com.ecommerce.product_service.model.Product;

@Repository
public interface  ProductRepository extends JpaRepository<Product,Integer> {
    
}
