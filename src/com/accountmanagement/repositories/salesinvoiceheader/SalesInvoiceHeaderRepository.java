
package com.accountmanagement.repositories.salesinvoiceheader;

import com.accountmanagement.models.SalesInvoiceHeader;
import java.util.ArrayList;


public interface SalesInvoiceHeaderRepository {
    
    long save(SalesInvoiceHeader invoiceHeader);
    boolean update(SalesInvoiceHeader invoiceHeader);
    boolean delete(long id);
    SalesInvoiceHeader findById(long id);
    ArrayList<SalesInvoiceHeader> findAll();
    ArrayList<SalesInvoiceHeader> findAllBySearchWords(long id, int customerId, String date, boolean isFileType);
    ArrayList<SalesInvoiceHeader> findAllDesc();
    
}
