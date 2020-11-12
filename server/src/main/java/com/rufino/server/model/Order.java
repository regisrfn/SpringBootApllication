package com.rufino.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "order")
public class Order {

    @Id
    public Integer idOrder;
    @Column(nullable = false, length = 50)
    public Integer idClient;
    @Column(nullable = false, length = 50)
    public Integer idParcel;
    @Column(nullable = false, length = 50)
    public Float totalValue;
    @Column(nullable = false, length = 1000)
    public String orderAddress;
}
