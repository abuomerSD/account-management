
package com.accountmanagement.repositories.salesinvoiceheader;

import com.accountmanagement.models.SalesInvoiceHeader;
import java.util.ArrayList;


public interface SalesInvoiceHeaderRepository {
    
    boolean save(SalesInvoiceHeader invoiceHeader);
    boolean update(SalesInvoiceHeader invoiceHeader);
    boolean delete(long id);
    SalesInvoiceHeader findById(long id);
    ArrayList<SalesInvoiceHeader> findAll();
    
}