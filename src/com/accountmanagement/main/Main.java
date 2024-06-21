package com.accountmanagement.main;

import com.accountmanagement.database.DatabaseTablesCreator;
import com.accountmanagement.database.DbConnection;
import com.accountmanagement.ui.Home;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String [] args) throws SQLException{
        
        try {
            // create Database Tables 
            DatabaseTablesCreator.createDbTables();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        Connection con = DbConnection.getConnection();
        Connection con2 = DbConnection.getConnection();
        
        if(con == con2) {
            System.out.println("con == con2");
        }
        
        
        
        if(!con.isClosed()){
            System.out.println("connected");
        }
        
        Home home = new Home();
        home.setExtendedState(home.MAXIMIZED_BOTH);
        home.setVisible(true);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        WindowListener listener = new WindowAdapter() {
            @Override 
            public void windowClosing(WindowEvent we) {
                
                closeDbConnection();
                home.setVisible(false);
                home.dispose();

            }
};
        
        
        home.addWindowListener(listener);
   
    }
    
    
    private static void closeDbConnection() {
        Connection con = DbConnection.getConnection();
        
        try{
            if(! con.isClosed()){
            con.close();
            if(con.isClosed()) 
                    System.out.println("con closed");
        }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
