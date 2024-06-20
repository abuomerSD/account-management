
package com.accountmanagement.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    
    static Connection con = null;
    
    public static Connection getConnection() {
        
        
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Database.db");
                       
        } catch (Exception e) {
        }
        return con;
    }
    
}
