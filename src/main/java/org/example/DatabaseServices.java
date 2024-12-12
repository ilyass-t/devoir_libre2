package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseServices {
    public boolean isCustomerExist(int id) {
        DatabaseConnection db = new DatabaseConnection();
        final String sql="select * from `customer` where id=?";  // Use backticks around 'order'

        try {
            Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Simplified return statement
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Ensure false is returned in case of error
        }
    }

    public void ajouterOrder(Order order) {
        DatabaseConnection db = new DatabaseConnection();
        final String sql="insert into `order` (id, date, amount, customer_id) values(?,?,?,?)";  // Use backticks around 'order'

        try {
            Connection connection = db.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(order.getId()));  // Ensure proper conversion of order ID
            ps.setDate(2, order.getDate());
            ps.setDouble(3, order.getAmount());
            ps.setInt(4, order.getCustomer_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
