package com.rufino.server.dao;

import java.util.List;
import java.util.UUID;

import com.rufino.server.model.Order;

public interface OrderDAO {
    int insertOrder(UUID idOrder, Order order);
    int deleteOrder(UUID id);
    List<Order> getAllOrder();

    default int insertOrder(Order order){
        UUID id = UUID.randomUUID();
        return insertOrder(id, order);
    }
}
