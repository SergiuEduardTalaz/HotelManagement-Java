package ro.fortech.academy.presentation.customer;

import ro.fortech.academy.business.entities.Customer;

import java.util.List;

public class CustomerModel {
    private List<Customer> customerList;
    public CustomerModel(List<Customer>customerList){
        this.customerList = customerList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public void updateCustomer(Customer customer) {
        Customer toUpdate = null;
        for (int i = 0 ; i < customerList.size(); i++) {
            if (customerList.get(i).getCustomerId() == customer.getCustomerId()) {
                toUpdate = customerList.get(i);
            }
        }
        toUpdate.setCustomerId(customer.getCustomerId());
        toUpdate.setFirstName(customer.getFirstName());
        toUpdate.setLastName(customer.getLastName());
        toUpdate.setDateOfBirth(customer.getDateOfBirth());
        toUpdate.setPhoneNumber(customer.getPhoneNumber());
        toUpdate.setEmail(customer.getEmail());
        toUpdate.setReservationId(customer.getReservationId());
    }
}
