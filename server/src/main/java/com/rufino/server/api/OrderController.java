package com.rufino.server.api;

import java.util.UUID;

import com.rufino.server.model.Order;
import com.rufino.server.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/order")
    public String saveOrder(@RequestBody Order order) {
        int op = orderService.addOrder(order);
        return (op > 0) ? "order added successfully" : "Error operation";
    }

    @DeleteMapping("/api/v1/order/{id}")
    public String deleteOrder(@PathVariable String id) {
        try {
            UUID idOrder = UUID.fromString(id);
            int op = orderService.delete(idOrder);
            return (op > 0) ? "successfully operation" : "error operation";
        } catch (Exception e) {
            return "error operation";
        }

    }
}
