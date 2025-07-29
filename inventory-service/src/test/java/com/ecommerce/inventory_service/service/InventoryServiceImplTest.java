package com.ecommerce.inventory_service.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;

class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(10);
        inventory.setQuantity(50);
    }

    @Test
    void testDisccountStock_Success() {
        when(inventoryRepository.findByProductId(10)).thenReturn(Optional.of(inventory));

        boolean result = inventoryService.disccountStock(10, 20);

        assertTrue(result);
        assertEquals(30, inventory.getQuantity());
        verify(inventoryRepository).save(inventory);
    }

    @Test
    void testDisccountStock_Failure_NotEnoughStock() {
        when(inventoryRepository.findByProductId(10)).thenReturn(Optional.of(inventory));

        boolean result = inventoryService.disccountStock(10, 100);

        assertFalse(result);
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void testDisccountStock_Failure_ProductNotFound() {
        when(inventoryRepository.findByProductId(999)).thenReturn(Optional.empty());

        boolean result = inventoryService.disccountStock(999, 10);

        assertFalse(result);
        verify(inventoryRepository, never()).save(any());
    }

    @Test
void testListAll() {
    List<Inventory> lista = List.of(inventory);
    when(inventoryRepository.findAll()).thenReturn(lista);

    List<Inventory> result = inventoryService.listAll();

    assertEquals(1, result.size());
    assertEquals(10, result.get(0).getProductId());
}

@Test
void testFindByProductIdFound() {
    when(inventoryRepository.findByProductId(10)).thenReturn(Optional.of(inventory));

    Optional<Inventory> result = inventoryService.findByProductId(10);

    assertTrue(result.isPresent());
    assertEquals(10, result.get().getProductId());
}

@Test
void testFindByProductIdNotFound() {
    when(inventoryRepository.findByProductId(99)).thenReturn(Optional.empty());

    Optional<Inventory> result = inventoryService.findByProductId(99);

    assertFalse(result.isPresent());
}

@Test
void testSave_NewInventory() {
    when(inventoryRepository.findByProductId(10)).thenReturn(Optional.empty());
    when(inventoryRepository.save(inventory)).thenReturn(inventory);

    Inventory result = inventoryService.save(inventory);

    assertEquals(10, result.getProductId());
    verify(inventoryRepository).save(inventory);
}

@Test
void testSave_UpdateExisting() {
    Inventory existing = new Inventory();
    existing.setId(2);
    existing.setProductId(10);
    existing.setQuantity(30);

    when(inventoryRepository.findByProductId(10)).thenReturn(Optional.of(existing));
    when(inventoryRepository.save(existing)).thenReturn(existing);

    inventory.setQuantity(60); // nuevo valor
    Inventory result = inventoryService.save(inventory);

    assertEquals(60, result.getQuantity());
    verify(inventoryRepository).save(existing);
}

@Test
void testDelete() {
    inventoryService.delete(1);
    verify(inventoryRepository).deleteById(1);
}

}
