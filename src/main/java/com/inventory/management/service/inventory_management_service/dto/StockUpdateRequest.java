package com.inventory.management.service.inventory_management_service.dto;

public class StockUpdateRequest {
    private Integer quantity;

    public StockUpdateRequest() {
    }

    public StockUpdateRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
