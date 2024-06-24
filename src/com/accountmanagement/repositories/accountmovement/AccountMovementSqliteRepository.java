
package com.accountmanagement.repositories.accountmovement;

import com.accountmanagement.models.AccountMovement;
import java.util.ArrayList;

public class AccountMovementSqliteRepository implements AccountMovementRepository{

    @Override
    public boolean save(AccountMovement accountMovement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(AccountMovement newAccountMovement) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(long incomingDocumentId, long outgoingDocumentId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<AccountMovement> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<AccountMovement> findByCustomerIdAndCurrencyId(int customerId, int currencyId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getCustomerBalance(int customerId, int currencyId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
