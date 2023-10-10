package ro.fortech.academy.business.dto;
import java.util.Date;
import java.util.Objects;

public class PaymentDto implements Comparable<PaymentDto> {
    private int paymentId;
    private Date dateOfPayment;
    private double amount;
    private String typeOfPayment;
    private String firstName;
    private String lastName;
    private boolean isVisible;

    public PaymentDto(int paymentId, Date dateOfPayment, double amount, String typeOfPayment, String firstName, String lastName, boolean isVisible) {
        this.paymentId = paymentId;
        this.dateOfPayment = dateOfPayment;
        this.amount = amount;
        this.typeOfPayment = typeOfPayment;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isVisible = isVisible;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDto that = (PaymentDto) o;
        return paymentId == that.paymentId && Double.compare(that.amount, amount) == 0 && isVisible == that.isVisible && Objects.equals(dateOfPayment, that.dateOfPayment) && Objects.equals(typeOfPayment, that.typeOfPayment) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, dateOfPayment, amount, typeOfPayment, firstName, lastName, isVisible);
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "paymentId=" + paymentId +
                ", dateOfPayment=" + dateOfPayment +
                ", amount=" + amount +
                ", typeOfPayment='" + typeOfPayment + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isVisible=" + isVisible +
                '}';
    }

    public int compareTo(PaymentDto o) {
        return this.firstName.compareTo(o.firstName);
    }

}



