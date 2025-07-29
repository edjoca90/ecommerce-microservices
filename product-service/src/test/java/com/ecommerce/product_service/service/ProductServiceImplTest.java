package com.ecommerce.product_service.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleProduct = new Product();
        sampleProduct.setId(1);
        sampleProduct.setName("Producto A");
        sampleProduct.setPrice(BigDecimal.valueOf(10.5));
        sampleProduct.setDescription("Descripción del producto A");
    }

    @Test
    void testListAll() {
        List<Product> productList = Arrays.asList(sampleProduct);
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.listAll();

        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(sampleProduct));

        Optional<Product> result = productService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Producto A", result.get().getName());
        verify(productRepository).findById(1);
    }

    @Test
    void testCreate() {
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        Product created = productService.create(sampleProduct);

        assertEquals("Producto A", created.getName());
        verify(productRepository).save(sampleProduct);
    }

    @Test
    void testUpdateSuccess() {
        Product updated = new Product();
        updated.setName("Producto Modificado");
        updated.setPrice(BigDecimal.valueOf(20.00));
        updated.setDescription("Nueva descripción");

        when(productRepository.findById(1)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        Product result = productService.update(1, updated);

        assertEquals("Producto Modificado", result.getName());
        verify(productRepository).save(sampleProduct);
    }

    @Test
    void testUpdateNotFound() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                productService.update(99, sampleProduct));

        assertEquals("Item Not found. id: 99", exception.getMessage());
    }

    @Test
    void testDelete() {
        productService.delete(1);
        verify(productRepository).deleteById(1);
    }
}
