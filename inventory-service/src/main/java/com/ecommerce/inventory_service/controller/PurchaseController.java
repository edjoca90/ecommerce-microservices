package com.ecommerce.inventory_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventory_service.DTO.ProductDTO;
import com.ecommerce.inventory_service.DTO.PurchaseResponseDTO;
import com.ecommerce.inventory_service.model.Purchase;
import com.ecommerce.inventory_service.service.PurchaseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compras")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<Purchase> listarCompras() {
        return purchaseService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getPurchase(@PathVariable Integer id) {
        return purchaseService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public List<Purchase> getByProduct(@PathVariable Integer productoId) {
        return purchaseService.getByProduct(productoId);
    }

    @GetMapping("/stock/{productId}")
    public ResponseEntity<String> getStockByProduct(@PathVariable Integer productId) {
        Integer stock = purchaseService.getStockByProduct(productId);
        return ResponseEntity.ok("Total comprado del producto " + productId + ": " + stock);
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> registrarCompra(@Valid @RequestBody Purchase purchase) {
        PurchaseResponseDTO newPurchase = purchaseService.newPurchase(purchase);
        return ResponseEntity.ok(newPurchase);
    }
    
    @GetMapping("/{id}/detail")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseProduct(@PathVariable Integer id) {
        Optional<Purchase> purchaseOpt = purchaseService.getById(id);
        if (purchaseOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Purchase purchase = purchaseOpt.get();
        ProductDTO product = purchaseService.getProductById(purchase.getProductId());

        if (product == null) {
            return ResponseEntity.status(502).body(new PurchaseResponseDTO(purchase, null)); // producto no disponible
        }

        return ResponseEntity.ok(new PurchaseResponseDTO(purchase, product));
    }

}

