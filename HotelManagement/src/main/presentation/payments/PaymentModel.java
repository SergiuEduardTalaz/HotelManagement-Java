package ro.fortech.academy.presentation.payments;

import ro.fortech.academy.business.dto.PaymentDto;
import ro.fortech.academy.business.entities.Payment;

import java.util.List;

public class PaymentModel {
    private List<Payment> paymentList;
    private List<PaymentDto> paymentDtoList;
    public PaymentModel(List<Payment> paymentList, List<PaymentDto> paymentDtoList) {
        this.paymentList = paymentList;
        this.paymentDtoList = paymentDtoList;
    }

    public List<PaymentDto> getPaymentDtoList() {
        return paymentDtoList;
    }

    public void setPaymentDtoList(List<PaymentDto> paymentDtoList) {
        this.paymentDtoList = paymentDtoList;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public void addPayment (Payment payment) {
        paymentList.add(payment);
    }
}
