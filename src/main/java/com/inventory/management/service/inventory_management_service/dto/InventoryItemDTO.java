package com.inventory.management.service.inventory_management_service.dto;

public class InventoryItemDTO {
    private String productId;
    private String productName;
    private Integer stockQuantity;
    private Integer reservedQuantity;
    private Integer lowStockThreshold;

    public InventoryItemDTO() {
    }

    public InventoryItemDTO(String productId, String productName, Integer stockQuantity, Integer reservedQuantity, Integer lowStockThreshold) {
        this.productId = productId;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
        this.reservedQuantity = reservedQuantity;
        this.lowStockThreshold = lowStockThreshold;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Integer getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(Integer lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public Integer getAvailableQuantity() {
        return stockQuantity - reservedQuantity;
    }
}
