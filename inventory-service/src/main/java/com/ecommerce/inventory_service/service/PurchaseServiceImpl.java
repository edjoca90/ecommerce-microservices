package com.ecommerce.inventory_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.inventory_service.DTO.ProductDTO;
import com.ecommerce.inventory_service.DTO.PurchaseResponseDTO;
import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.model.Purchase;
import com.ecommerce.inventory_service.repository.PurchaseRepository;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository puchaseRepository;
    private InventoryService inventoryService;
    @Autowired
    private RestTemplate restTemplate;
    private final String PRODUCTS_URL = "http://localhost:8081/products"; 

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository puchaseRepository) {
        this.puchaseRepository = puchaseRepository;
    }

    @Override
    public List<Purchase> listAll() {
        return puchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> getById(Integer id) {
        return puchaseRepository.findById(id);
    }
    public ProductDTO getProductById(Integer productId) {
        try {
            return restTemplate.getForObject(PRODUCTS_URL + "/" + productId, ProductDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public List<Purchase> getByProduct(Integer productId) {
        return puchaseRepository.findByProductId(productId);
    }

    @Override
    public PurchaseResponseDTO newPurchase(Purchase purchase) {
        //verify product quantity
        Optional<Inventory> opt = inventoryService.findByProductId(purchase.getProductId());
        if (opt.isPresent() && opt.get().getQuantity() >= purchase.getPurchaseQuantity()) {
            Inventory inv = opt.get();
            purchase.setPurchaseDate(LocalDateTime.now());
            Purchase newPurchase= puchaseRepository.save(purchase);
            ProductDTO product= getProductById(purchase.getProductId());
            PurchaseResponseDTO response = new PurchaseResponseDTO(newPurchase, product);
            inventoryService.disccountStock(purchase.getProductId(), purchase.getPurchaseQuantity());
            return response;
            
           
        }else return null;
    }

    @Override
    public Integer getStockByProduct(Integer productId) {
        Integer total = puchaseRepository.totalByProduct(productId);
        return total != null ? total : 0;
    }
    
}
