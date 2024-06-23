/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountmanagement.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author asdf
 */
public class DatabaseTablesCreator {
    
    public static void createDbTables() {
        createProductsTable();
    }

    private static void createProductsTable() {
        
        String sql = "CREATE TABLE IF NOT EXISTS tb_products (\n" +
                            "	Id  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	Serial VARCHAR(255) UNIQUE,\n" +
                            "	Buyer_Name VARCHAR(100),\n" +
                            "	Buyer_Phone VARCHAR(50),\n" +
                            "	Buyer_Email VARCHAR(100),\n" +
                            "	Password VARCHAR(100),\n" +
                            "	Subscribtion_Date VARCHAR(50),\n" +
                            "	Subscribtion_Value DOUBLE\n" +
                            ");";
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.execute();
//            System.out.println(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    
}
