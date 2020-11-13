package com.rufino.server.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.rufino.server.Database.DatabaseConnection;
import com.rufino.server.dao.OrderDAO;
import com.rufino.server.model.Order;

import org.springframework.stereotype.Repository;

@Repository("DB_H2")
public class OrderRepository implements OrderDAO{

    private static Connection conn = null;

    @Override
    public int insertOrder(UUID id, Order order) {
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            String sql = String.format("INSERT INTO orders " + "(id_order,id_client, id_parcel, total_value, order_address)" +
            "VALUES ('%s',123, 456, 1.99, 'Rua de cima')", id);
            stmt.executeUpdate(sql);
            System.out.println(order.getIdClient());
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }
    
    
}
