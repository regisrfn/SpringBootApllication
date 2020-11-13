package com.rufino.server.dao;

import java.util.UUID;

import com.rufino.server.model.Order;

public interface OrderDAO {
    int insertOrder(UUID idOrder, Order order);
    int deleteOrder(UUID id);

    default int insertOrder(Order order){
        UUID id = UUID.randomUUID();
        return insertOrder(id, order);
    }
}
