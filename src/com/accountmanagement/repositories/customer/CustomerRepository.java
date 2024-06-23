
package com.accountmanagement.repositories.customer;

import com.accountmanagement.models.Customer;
import java.util.ArrayList;

public interface CustomerRepository {
    
    boolean save(Customer customer);
    boolean update(Customer newCustomer);
    boolean delete(int id);
    Customer findById(int id);
    ArrayList<Customer> findAll();
    ArrayList<Customer> findBySearchWords(String searchWords);
    
}
