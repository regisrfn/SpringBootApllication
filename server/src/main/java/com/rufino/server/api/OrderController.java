package com.rufino.server.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.rufino.server.Database.DatabaseConnection;
import com.rufino.server.model.Order;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OrderController {

    @PostMapping("/api/v1/order")
    private void savePerson(@RequestBody Order order) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO orders " + "(id_client, id_parcel, total_value, order_address)" +
            "VALUES (123, 456, 1.99, 'Rua de cima')";
            stmt.executeUpdate(sql);
            System.out.println(order.getIdClient());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
