
package com.accountmanagement.repositories.currency;

import com.accountmanagement.database.DbConnection;
import com.accountmanagement.models.Currency;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class CurrencySqliteRepository implements CurrencyRepository {

    @Override
    public boolean save(Currency currency) {
        String sql = "INSERT INTO tb_currency (Name) VALUES (?);";
        
        try {
            Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, currency.getName());
            
            System.out.println(ps.toString());
            
            int result = ps.executeUpdate();
            
            if(result == 1) {
                return true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
        
        return false;
    }

    @Override
    public boolean update(Currency newCurrency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Currency findById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Currency> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
