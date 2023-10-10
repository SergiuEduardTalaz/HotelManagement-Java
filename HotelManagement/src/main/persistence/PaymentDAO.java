package ro.fortech.academy.persistence;

import ro.fortech.academy.business.dto.PaymentDto;
import ro.fortech.academy.business.entities.Payment;

import java.time.LocalDate;
import java.util.List;

public interface PaymentDAO {
    List<Payment> getAllPayments();
    List<PaymentDto> getAllPaymentsWithCustomerData();
    void insertPayment(Payment payment);
    List<PaymentDto> searchForPayment(LocalDate dateOfPayment, String lastName);
    void updatePaymentAmount(int paymentId, double amount);
    void disablePayment(int paymentId);
    List<Payment> getAllCostumersId();
}
