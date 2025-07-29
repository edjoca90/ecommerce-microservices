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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.DTO.ProductDTO;
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

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId) {
         Optional<ProductDTO> product = inventoryService.findByProductId(productId);
         if (compraOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Compra compra = compraOpt.get();
    ProductoDTO producto = compraService.obtenerProductoPorId(compra.getProductoId());

    if (producto == null) {
        return ResponseEntity.status(502).body(new CompraConProductoDTO(compra, null)); // producto no disponible
    }

    return ResponseEntity.ok(new CompraConProductoDTO(compra, producto));
    } 

    @PostMapping
    public ResponseEntity<Inventory> save(@Valid @RequestBody Inventory inventory) {
        Inventory newItem = inventoryService.save(inventory);
        return ResponseEntity.ok(newItem);
    }

    @PostMapping("/disscount")
    public ResponseEntity<String> disccountStock(
            @RequestParam Integer productId,
            @RequestParam int quantity) {

        boolean exito = inventoryService.disccountStock(productId, quantity);
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
