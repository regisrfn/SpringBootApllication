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
            saveSQL(id, order, stmt);
            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    private void saveSQL(UUID id, Order order, Statement stmt) throws SQLException {
        String command = "INSERT INTO";
        String fields = "(id_order,id_client, id_parcel, total_value, order_address)";
        String values = String.format("VALUES('%s',%s,%s,%s,'%s')", id,order.getIdClient(),order.getIdParcel(), order.getTotalValue(),order.getOrderAddress());
        String sql = String.format("%s orders %s %s",command,fields,values);
        stmt.executeUpdate(sql);
    }    
}
