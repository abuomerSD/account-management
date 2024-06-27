package com.accountmanagement.main;

import com.accountmanagement.database.DatabaseTablesCreator;
import com.accountmanagement.database.DbConnection;
import com.accountmanagement.models.AccountMovement;
import com.accountmanagement.models.OutgoingDocument;
import com.accountmanagement.repositories.accountmovement.AccountMovementSqliteRepository;
import com.accountmanagement.repositories.outgoingdocument.OutgoingDocumentSqliteRepository;
import com.accountmanagement.ui.Home;
import com.accountmanagement.ui.Login;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String [] args) throws SQLException{
        
        try {
            // create Database Tables 
            DatabaseTablesCreator.createDbTables();
            Login login = new Login();
            login.setVisible(true);
                     
                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
   
    }
   
}
