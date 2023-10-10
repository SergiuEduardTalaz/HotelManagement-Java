package ro.fortech.academy.business.services;

import ro.fortech.academy.business.dto.PaymentDto;
import ro.fortech.academy.business.entities.Payment;
import ro.fortech.academy.persistence.PaymentDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentService {
    private final PaymentDAO paymentDAO;

    public PaymentService(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public List<Payment> getPayments() {
        List<Payment> list = paymentDAO.getAllPayments();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public List<PaymentDto> getPaymentsWithCustomerData() {
        List<PaymentDto> list = paymentDAO.getAllPaymentsWithCustomerData();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public Payment addNewPayment(Payment payment) {
        paymentDAO.insertPayment(payment);
        return payment;
    }

    public List<PaymentDto> searchPayment(LocalDate dateOfPayment, String lastName) {
        return paymentDAO.searchForPayment(dateOfPayment, lastName);
    }

    public void updatePayment(int paymentId, double amount) {
        paymentDAO.updatePaymentAmount(paymentId, amount);
    }
    public void disablePayment(int paymentId) {
        paymentDAO.disablePayment(paymentId);
    }

    public List<Payment> getCustomerIds() {
        return paymentDAO.getAllCostumersId();
    }
}
