package com.ecommerce.inventory_service.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.service.InventoryService;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void testDisccountStock_Success() throws Exception {
        when(inventoryService.disccountStock(10, 5)).thenReturn(true);

        mockMvc.perform(post("/inventory/disscount")
                        .param("productId", "10")
                        .param("quantity", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock descontado correctamente"));
    }

    @Test
    void testDisccountStock_Failure() throws Exception {
        when(inventoryService.disccountStock(10, 100)).thenReturn(false);

        mockMvc.perform(post("/inventory/disscount")
                        .param("productId", "10")
                        .param("quantity", "100"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No hay stock suficiente"));
    }

    @Test
    void testListar() throws Exception {
        when(inventoryService.listAll()).thenReturn(List.of());

        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetProductById_Found() throws Exception {
        Inventory inv = new Inventory();
        inv.setId(1);
        inv.setProductId(20);
        inv.setQuantity(100);

        when(inventoryService.findByProductId(20)).thenReturn(Optional.of(inv));

        mockMvc.perform(get("/inventory/product/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(20));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(inventoryService.findByProductId(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/inventory/product/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveInventory() throws Exception {
        Inventory inv = new Inventory();
        inv.setId(1);
        inv.setProductId(55);
        inv.setQuantity(200);

        when(inventoryService.save(any())).thenReturn(inv);

        mockMvc.perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "productId": 55,
                                "quantity": 200
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(55));
    }

    @Test
    void testDeleteInventory() throws Exception {
        doNothing().when(inventoryService).delete(1);

        mockMvc.perform(delete("/inventory/1"))
                .andExpect(status().isNoContent());
    }

}
