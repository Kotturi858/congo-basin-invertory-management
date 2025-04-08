package com.inventory.management.service.inventory_management_service.repository;

import com.inventory.management.service.inventory_management_service.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    
    Optional<InventoryItem> findByProductId(String productId);
    
    boolean existsByProductId(String productId);
    
    @Query("SELECT i FROM InventoryItem i WHERE i.stockQuantity - i.reservedQuantity <= i.lowStockThreshold")
    List<InventoryItem> findLowStockItems();
}
