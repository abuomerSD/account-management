package com.accountmanagement.main;

import com.accountmanagement.database.DatabaseTablesCreator;
import com.accountmanagement.models.AccountMovement;
import com.accountmanagement.repositories.accountmovement.AccountMovementSqliteRepository;
import com.accountmanagement.ui.Login;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String [] args) throws SQLException{
        
        try {
            // create Database Tables 
            DatabaseTablesCreator.createDbTables();
            
            // flatlaf look and feel
            
            FlatLightLaf.setup();
            
            // check if the app activated
            
            if(!isActivated()){
                JOptionPane.showMessageDialog(null, "يرجى تحديث النظام");
                return;
            } 
            
            Login login = new Login();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
                     
                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
   
    }
    
    private static boolean isActivated() {
        
        boolean activated = false;
        
        try {
            AccountMovementSqliteRepository repo = new AccountMovementSqliteRepository();
            ArrayList<AccountMovement> list = repo.findAll();
            
            if(activated == false){
                
                if(list.size() > 100) {                    
                    return activated;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
        return true;
    }
   
}
