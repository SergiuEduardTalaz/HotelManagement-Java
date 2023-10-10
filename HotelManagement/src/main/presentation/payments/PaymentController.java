package ro.fortech.academy.presentation.payments;

import ro.fortech.academy.business.dto.PaymentDto;
import ro.fortech.academy.business.entities.Payment;
import ro.fortech.academy.business.services.PaymentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PaymentController {
    PaymentModel model;
    PaymentView view;
    PaymentService service;

    public PaymentController(PaymentModel model, PaymentView view, PaymentService service) {
        this.model = model;
        this.view = view;
        this.service = service;
    }

    public void showPaymentButton() {
        model.setPaymentDtoList(service.getPaymentsWithCustomerData());
        view.showPaymentsWithCustomerData(model.getPaymentDtoList());
    }

    public void OKButtonAddNewPayment(Payment payment) {
        model.addPayment(service.addNewPayment(payment));

    }

    public void updatePaymentAmount(int paymentId, double paymentAmount) {
        service.updatePayment(paymentId, paymentAmount);
    }

    public void searchPayment(LocalDate dateOfPayment, String lastName) {
        List<PaymentDto> searchedPayment = service.searchPayment(dateOfPayment, lastName);
        if (searchedPayment.size() != 0) {
            model.setPaymentDtoList(searchedPayment);
            view.showPaymentsWithCustomerData(model.getPaymentDtoList());
        } else {
            view.showMessageForUpdateMethod();
        }
    }

    public int generateRandomPaymentId() {
        Random random = new Random();
        int maxAttempts = 10000;
        int attempts = 0;

        while (attempts < maxAttempts) {
            int paymentId = random.nextInt(10000);

            if (isPaymentIdUnique(paymentId)) {
                return paymentId;
            }
            attempts++;
        }
        view.showMessageForGenerateRoomId();
        throw new IllegalStateException("Unable to generate a unique Room ID after multiple attempts.");
    }

    public boolean isPaymentIdUnique(int paymentId) {
        for (Payment payment : model.getPaymentList()) {
            if (payment.getPaymentId() == paymentId) {
                return false;
            }
        }
        return true;
    }

    public void disablePayment(int paymentId) {
        service.disablePayment(paymentId);
        List<Payment> paymentList = new ArrayList<>(model.getPaymentList());
        List<Payment> newPaymentList = paymentList.stream().filter(payment -> payment.getPaymentId() == paymentId).collect(Collectors.toList());
            model.setPaymentList(newPaymentList);
        }

    public List<Payment> getCustomerIds() {
        return service.getCustomerIds();
    }

}




