package com.inventory.management.service.inventory_management_service.dto;

import lombok.Data;

@Data
public class InventoryItemDTO {
    private String productId;
    private String productName;
    private Integer stockQuantity;
    private Integer reservedQuantity;
    private Integer lowStockThreshold;
    private boolean isStockAvailable;
    private Double price;

    public InventoryItemDTO(String productId, boolean isStockAvailable) {
        this.productId = productId;
        this.isStockAvailable = isStockAvailable;
    }

    public InventoryItemDTO(String productId, String productName, Integer stockQuantity,
                            Integer reservedQuantity, Integer lowStockThreshold,
                            boolean isStockAvailable, Double price) {
        this.productId = productId;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
        this.reservedQuantity = reservedQuantity;
        this.lowStockThreshold = lowStockThreshold;
        this.isStockAvailable = isStockAvailable;
        this.price = price;
    }
}
