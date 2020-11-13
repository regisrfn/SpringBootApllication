package com.rufino.server.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import com.rufino.server.Database.DatabaseConnection;
import com.rufino.server.dao.OrderDAO;
import com.rufino.server.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("DB_H2")
public class OrderRepository implements OrderDAO{

    private static Connection conn = null;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertOrder(UUID id, Order order) {
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            return saveSQL(id, order, stmt);

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteOrder(UUID id) {
        return jdbcTemplate.update("delete from orders where id_order = ?", id);
    }

    private int saveSQL(UUID id, Order order, Statement stmt) throws SQLException {
        String command = "INSERT INTO";
        String fields = "(id_order,id_client, id_parcel, total_value, order_address)";
        String values = String.format("VALUES('%s',%s,%s,%s,'%s')", id,order.getIdClient(),order.getIdParcel(), order.getTotalValue(),order.getOrderAddress());
        String sql = String.format("%s orders %s %s",command,fields,values);
        return stmt.executeUpdate(sql);
    }    
}
