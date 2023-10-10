package ro.fortech.academy.persistence;

import ro.fortech.academy.business.entities.Customer;

import java.util.List;

public interface CustomerDao {
    void disableCustomer(int customerId);

    List<Customer> loadAllCustomers();


    void insertCustomer(Customer customer);

    List<Customer> searchCustomer(int customerId);

    List<Customer> searchCustomer(String lastName);
    void updateCustomer(Customer customer);
}
