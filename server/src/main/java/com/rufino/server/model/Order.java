package com.rufino.server.model;

import java.util.UUID;

public class Order {

    private UUID idOrder;
    private int idClient;
    private int idParcel;
    private float totalValue;
    private String orderAddress;

    public void setIdOrder(UUID idOrder) {
        this.idOrder = idOrder;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdParcel(int idParcel) {
        this.idParcel = idParcel;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public UUID getIdOrder() {
        return idOrder;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdParcel() {
        return idParcel;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public String getOrderAddress() {
        return orderAddress;
    }
}
