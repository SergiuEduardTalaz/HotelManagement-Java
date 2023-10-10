package ro.fortech.academy.business.services;

import ro.fortech.academy.business.entities.Customer;
import ro.fortech.academy.persistence.CustomerDao;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    private final CustomerDao customerDAO;

    public CustomerService(CustomerDao customerDao) {
        this.customerDAO = customerDao;
    }
    public List<Customer> getCustomers() {
        List<Customer> list = customerDAO.loadAllCustomers();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public void disableCustomer(int customerId) {
        customerDAO.disableCustomer(customerId);
    }

    public Customer addNewCustomer(Customer customer) {
        customerDAO.insertCustomer(customer);
        return customer;
    }

    public List<Customer> searchCustomer(int customerId) {
        return customerDAO.searchCustomer(customerId);
    }

    public List<Customer> searchCustomer(String lastName) {
        return customerDAO.searchCustomer(lastName);
    }

    public Customer updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
        return customer;
    }

}
