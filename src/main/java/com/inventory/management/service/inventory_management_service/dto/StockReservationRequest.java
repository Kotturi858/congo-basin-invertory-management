package com.inventory.management.service.inventory_management_service.dto;

public class StockReservationRequest {
    private String productId;
    private Integer quantity;
    private String orderId;

    public StockReservationRequest() {
    }

    public StockReservationRequest(String productId, Integer quantity, String orderId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
