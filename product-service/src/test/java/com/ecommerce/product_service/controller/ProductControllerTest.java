package com.ecommerce.product_service.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setup() {
        sampleProduct = new Product();
        sampleProduct.setId(1);
        sampleProduct.setName("Producto A");
        sampleProduct.setPrice(BigDecimal.valueOf(9.99));
        sampleProduct.setDescription("Descripción A");
    }

    @Test
    void testListAll() throws Exception {
        when(productService.listAll()).thenReturn(List.of(sampleProduct));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Producto A"));
    }

    @Test
    void testFindByIdFound() throws Exception {
        when(productService.findById(1)).thenReturn(Optional.of(sampleProduct));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Producto A"));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        when(productService.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(sampleProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Producto A"));
    }

    @Test
    void testUpdateProductSuccess() throws Exception {
        when(productService.update(eq(1), any(Product.class))).thenReturn(sampleProduct);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Producto A"));
    }

    @Test
    void testUpdateProductNotFound() throws Exception {
        when(productService.update(eq(99), any(Product.class)))
                .thenThrow(new RuntimeException("Item Not found. id: 99"));

        mockMvc.perform(put("/products/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleProduct)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).delete(1);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}
