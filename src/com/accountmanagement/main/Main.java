package com.accountmanagement.main;

import com.accountmanagement.database.DbConnection;
import com.accountmanagement.ui.Home;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String [] args) throws SQLException{
        
        Connection con = DbConnection.getConnection();
        
        if(!con.isClosed()){
            System.out.println("connected");
        }
        
        Home home = new Home();
        home.setExtendedState(home.MAXIMIZED_BOTH);
        home.setVisible(true);
        
    }
}
