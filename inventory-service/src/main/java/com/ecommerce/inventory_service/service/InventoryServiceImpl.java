package com.ecommerce.inventory_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    
    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Inventory> listAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public Optional<Inventory> findByProductId(Integer productId) {        
        return inventoryRepository.findByProductId(productId);
    }

    @Override
    public Inventory save(Inventory inventory) {
        Optional<Inventory> existente = inventoryRepository.findByProductId(inventory.getProductId());
        if (existente.isPresent()) {
            Inventory inv = existente.get();
            inv.setQuantity(inventory.getQuantity());
            return inventoryRepository.save(inv);
        } else {
            return inventoryRepository.save(inventory);
        }
    }

    @Override
    public boolean disccountStock(Integer productId, int quantity) {
        Optional<Inventory> opt = inventoryRepository.findByProductId(productId);
        if (opt.isPresent() && opt.get().getQuantity() >= quantity) {
            Inventory inv = opt.get();
            inv.setQuantity(inv.getQuantity() - quantity);
            inventoryRepository.save(inv);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Integer id) {
        inventoryRepository.deleteById(id);
    }
}
