package ro.fortech.academy.presentation.customer;

import ro.fortech.academy.business.dto.ReservationDto;
import ro.fortech.academy.business.entities.Customer;
import ro.fortech.academy.business.services.CustomerService;
import ro.fortech.academy.business.services.ReservationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CustomerController {
    private final CustomerModel model;
    private final CustomerView view;
    private final CustomerService service;
    private final ReservationService reservationService;

    public CustomerController(CustomerModel model, CustomerView view, CustomerService service, ReservationService reservationService) {
        this.model= model;
        this.view = view;
        this.service = service;
        this.reservationService = reservationService;
    }

    public void customerButton() {
        model.setCustomerList(service.getCustomers());
        view.showCustomers(model.getCustomerList());
    }


    public int generateRandomCustomerId() {
        Random random = new Random();
        int maxAttempts = 10000;
        int attempts = 0;

        while (attempts < maxAttempts) {
            int customerId = random.nextInt(10000);

            if (isCustomerIdUnique(customerId)) {
            return customerId;
            }
            attempts++;
        }
        view.showErrorMessageId();
        throw new IllegalStateException("Unable to generate a unique Customer ID after multiple attempts.");

        }

        public boolean isCustomerIdUnique(int customerId) {
            for (Customer customer : model.getCustomerList()) {
                if (customer.getCustomerId() == customerId) {
                    return false;
                }
            }
            return true;
        }

    public void addNewCustomerButton(Customer customer) {
        model.addCustomer(service.addNewCustomer(customer));
    }

    public void updateCustomerButton(Customer customer) {
        model.updateCustomer(service.updateCustomer(customer));
    }

    public boolean searchCustomerByNameButton(String lastName) {
        List<Customer> searchedCustomer = service.searchCustomer(lastName);
        if (searchedCustomer != null && !searchedCustomer.isEmpty()) {
            model.setCustomerList(searchedCustomer);
            view.showCustomers(model.getCustomerList());
            return true;
        } else {
            return false;
        }
    }

    public boolean searchCustomerByIdButton(int customerId) {
        List<Customer> searchedCustomerById = service.searchCustomer(customerId);
        if (searchedCustomerById != null && !searchedCustomerById.isEmpty()) {
            model.setCustomerList(searchedCustomerById);
            view.showCustomers(model.getCustomerList());
            return true;
        } else {
            return false;
        }
    }

    public void disableCustomer(int customerId) {
        service.disableCustomer(customerId);
        List<Customer> customerList = new ArrayList<>(model.getCustomerList());
        List<Customer> newCustomerList = customerList.stream().filter(customer -> customer.getCustomerId() == customerId).collect(Collectors.toList());
        model.setCustomerList(newCustomerList);
    }

    public List<ReservationDto> getReservationDto() {
        return reservationService.getReservationDto();
    }


}
