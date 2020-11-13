package com.rufino.server.api;

import java.io.Console;

import com.rufino.server.model.Order;
import com.rufino.server.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/order")
    public String saveOrder(@RequestBody Order order) {
        int op = orderService.addOrder(order);
        return (op > 0) ? "order added successfully" : "Error operation"; 
    }
}
