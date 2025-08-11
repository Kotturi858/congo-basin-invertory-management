package com.inventory.management.service.inventory_management_service.controller;

import com.inventory.management.service.inventory_management_service.dto.InventoryItemDTO;
import com.inventory.management.service.inventory_management_service.dto.StockReservationRequest;
import com.inventory.management.service.inventory_management_service.dto.StockUpdateRequest;
import com.inventory.management.service.inventory_management_service.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<?> addInventoryItem(@RequestBody InventoryItemDTO inventoryItemDTO) {
        try {
            InventoryItemDTO savedItem = inventoryService.addInventoryItem(inventoryItemDTO);
            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding inventory item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getInventoryItem(@PathVariable String productId) {
        try {
            InventoryItemDTO inventoryItem = inventoryService.getInventoryItem(productId);
            return new ResponseEntity<>(inventoryItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving inventory item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateStockQuantity(@PathVariable String productId, @RequestBody StockUpdateRequest request) {
        try {
            InventoryItemDTO updatedItem = inventoryService.updateStockQuantity(productId, request.getQuantity());
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating stock quantity: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveStock(@RequestBody StockReservationRequest request) {
        try {
            InventoryItemDTO updatedItem = inventoryService.reserveStock(request);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new InventoryItemDTO(request.getProductId(), false), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error reserving stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/release")
    public ResponseEntity<?> releaseStock(@RequestBody StockReservationRequest request) {
        try {
            InventoryItemDTO updatedItem = inventoryService.releaseStock(request);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error releasing stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStockItems() {
        try {
            List<InventoryItemDTO> lowStockItems = inventoryService.getLowStockItems();
            return new ResponseEntity<>(lowStockItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving low stock items: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
