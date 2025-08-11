package com.inventory.management.service.inventory_management_service.service;

import com.inventory.management.service.inventory_management_service.dto.InventoryItemDTO;
import com.inventory.management.service.inventory_management_service.dto.StockReservationRequest;
import com.inventory.management.service.inventory_management_service.model.InventoryItem;
import com.inventory.management.service.inventory_management_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public InventoryItemDTO addInventoryItem(InventoryItemDTO inventoryItemDTO) {
        if (inventoryRepository.existsByProductId(inventoryItemDTO.getProductId())) {
            throw new IllegalArgumentException("Product with ID " + inventoryItemDTO.getProductId() + " already exists in inventory");
        }

        InventoryItem inventoryItem = new InventoryItem(
                inventoryItemDTO.getProductId(),
                inventoryItemDTO.getProductName(),
                inventoryItemDTO.getStockQuantity(),
                inventoryItemDTO.getLowStockThreshold()
        );

        InventoryItem savedItem = inventoryRepository.save(inventoryItem);
        return convertToDTO(savedItem);
    }

    public InventoryItemDTO getInventoryItem(String productId) {
        InventoryItem inventoryItem = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new NoSuchElementException("Product with ID " + productId + " not found in inventory"));
        return convertToDTO(inventoryItem);
    }

    @Transactional
    public InventoryItemDTO updateStockQuantity(String productId, Integer quantity) {
        InventoryItem inventoryItem = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new NoSuchElementException("Product with ID " + productId + " not found in inventory"));

        inventoryItem.setStockQuantity(quantity);
        InventoryItem updatedItem = inventoryRepository.save(inventoryItem);
        return convertToDTO(updatedItem);
    }

    @Transactional
    public InventoryItemDTO reserveStock(StockReservationRequest request) {
        InventoryItem inventoryItem = inventoryRepository.findByProductId(request.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product with ID " + request.getProductId() + " not found in inventory"));

        int availableQuantity = inventoryItem.getStockQuantity() - inventoryItem.getReservedQuantity();
        if (availableQuantity < request.getQuantity()) {
            throw new IllegalStateException("Insufficient stock available for product " + request.getProductId() + 
                    ". Requested: " + request.getQuantity() + ", Available: " + availableQuantity);
        }

        inventoryItem.setReservedQuantity(inventoryItem.getReservedQuantity() + request.getQuantity());
        InventoryItem updatedItem = inventoryRepository.save(inventoryItem);
        return convertToDTO(updatedItem);
    }

    @Transactional
    public InventoryItemDTO releaseStock(StockReservationRequest request) {
        InventoryItem inventoryItem = inventoryRepository.findByProductId(request.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product with ID " + request.getProductId() + " not found in inventory"));

        if (inventoryItem.getReservedQuantity() < request.getQuantity()) {
            throw new IllegalStateException("Cannot release more stock than is reserved for product " + request.getProductId());
        }

        inventoryItem.setReservedQuantity(inventoryItem.getReservedQuantity() - request.getQuantity());
        InventoryItem updatedItem = inventoryRepository.save(inventoryItem);
        return convertToDTO(updatedItem);
    }

    public List<InventoryItemDTO> getLowStockItems() {
        List<InventoryItem> lowStockItems = inventoryRepository.findLowStockItems();
        return lowStockItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InventoryItemDTO convertToDTO(InventoryItem inventoryItem) {
        return new InventoryItemDTO(
                inventoryItem.getProductId(),
                inventoryItem.getProductName(),
                inventoryItem.getStockQuantity(),
                inventoryItem.getReservedQuantity(),
                inventoryItem.getLowStockThreshold(),
                true
        );
    }
}
