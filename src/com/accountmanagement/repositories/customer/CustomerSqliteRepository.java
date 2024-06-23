
package com.accountmanagement.repositories.customer;

import com.accountmanagement.database.DbConnection;
import com.accountmanagement.models.Customer;
import com.accountmanagement.models.CustomerBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CustomerSqliteRepository implements CustomerRepository {

    @Override
    public boolean save(Customer customer) {
        String sql = "INSERT INTO tb_customers (Name, Phone) VALUES (?, ?);";
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            
            System.out.println(ps.toString());
            int result = ps.executeUpdate();
            
            if(result == 1) {
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "خطأ", 0);
        }
        
        
        return false;
    }

    @Override
    public boolean update(Customer newCustomer) {
        String sql = "UPDATE tb_customers SET Name = ?, Phone = ? WHERE Id = ?;";
        
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newCustomer.getName());
            ps.setString(2, newCustomer.getPhone());
            ps.setInt(3, newCustomer.getId());
            
            System.out.println(ps.toString());
            
            int result = ps.executeUpdate();
            
            if(result == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "خطأ", 0);
        }
        
        return false; 
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM tb_customers WHERE Id = ?";
        
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, id);
            System.out.println(ps.toString());
            int result = ps.executeUpdate();
            
            if(result == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "خطأ", 0);
        }
        
        return false;
    }

    @Override
    public Customer findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Customer> findAll() {
        ArrayList<Customer> list = new ArrayList<Customer>();
        String sql = "SELECT * FROM tb_customers;";
        
        try {
            Connection con = DbConnection.getConnection();
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()) {
                Customer customer = new CustomerBuilder()
                        .id(rs.getInt("Id"))
                        .name(rs.getString("Name"))
                        .phone(rs.getString("Phone"))
                        .build();
                
                list.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "خطأ", 0);
        }
        
        return list;
    }

    @Override
    public ArrayList<Customer> findBySearchWords(String searchWords) {
        ArrayList<Customer> list = new ArrayList<>();
        
        String sql = "SELECT * FROM tb_customers WHERE Name LIKE '%" + searchWords +"%'";
        
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Customer customer = new CustomerBuilder()
                        .id(rs.getInt("Id"))
                        .name(rs.getString("Name"))
                        .phone(rs.getString("Phone"))
                        .build();
                
                list.add(customer);
                        
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
        
        return list;
    }
    
}
