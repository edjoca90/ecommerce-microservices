package com.ecommerce.inventory_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.service.InventoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> listar() {
        return inventoryService.listAll();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getProductById(@PathVariable Integer productId) {
        return inventoryService.findByProductId(productId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    } 

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody Inventory inventory) {
        Object newItem = inventoryService.save(inventory);
        return ResponseEntity.ok(newItem);
    }

    @PostMapping("/disscount")
    public ResponseEntity<String> disccountStock(@Valid @RequestBody Inventory inventory) {

        boolean exito = inventoryService.disccountStock(inventory.getProductId(), inventory.getQuantity());
        if (exito) {
            return ResponseEntity.ok("Stock descontado correctamente");
        } else {
            return ResponseEntity.badRequest().body("No hay stock suficiente");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
