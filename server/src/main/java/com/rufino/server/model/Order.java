package com.rufino.server.model;

public class Order {

    private Integer idOrder;
    private Integer idClient;
    private Integer idParcel;
    private Float totalValue;
    private String orderAddress;

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public void setIdParcel(Integer idParcel) {
        this.idParcel = idParcel;
    }

    public void setTotalValue(Float totalValue) {
        this.totalValue = totalValue;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public Integer getIdParcel() {
        return idParcel;
    }

    public Float getTotalValue() {
        return totalValue;
    }

    public String getOrderAddress() {
        return orderAddress;
    }
}
