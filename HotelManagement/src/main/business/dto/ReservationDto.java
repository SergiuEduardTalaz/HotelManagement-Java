package ro.fortech.academy.business.dto;

import java.util.Date;
import java.util.Objects;

public class ReservationDto implements Comparable<ReservationDto> {
    private int reservationId;
    private Date dateOfReservation;

    private Date dateOfCheckIn;

    private Date dateOfCheckOut;

    private int numberOfPersons;

    private String roomNumber;
    private int price;

    private boolean isVisible;

    public ReservationDto(int reservationId, Date dateOfReservation, Date dateOfCheckIn, Date dateOfCheckOut, int numberOfPersons, String roomNumber, int price, boolean isVisible) {
        this.reservationId = reservationId;
        this.dateOfReservation = dateOfReservation;
        this.dateOfCheckIn = dateOfCheckIn;
        this.dateOfCheckOut = dateOfCheckOut;
        this.numberOfPersons = numberOfPersons;
        this.roomNumber = roomNumber;
        this.price = price;
        this.isVisible = isVisible;

    }

    public ReservationDto(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(Date dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public Date getDateOfCheckIn() {
        return dateOfCheckIn;
    }

    public void setDateOfCheckIn(Date dateOfCheckIn) {
        this.dateOfCheckIn = dateOfCheckIn;
    }

    public Date getDateOfCheckOut() {
        return dateOfCheckOut;
    }

    public void setDateOfCheckOut(Date dateOfCheckOut) {
        this.dateOfCheckOut = dateOfCheckOut;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

        ReservationDto that = (ReservationDto) o;

        if (reservationId != that.reservationId) return false;
        if (numberOfPersons != that.numberOfPersons) return false;
        if (price != that.price) return false;
        if (isVisible != that.isVisible) return false;
        if (!Objects.equals(dateOfReservation, that.dateOfReservation))
            return false;
        if (!Objects.equals(dateOfCheckIn, that.dateOfCheckIn))
            return false;
        if (!Objects.equals(dateOfCheckOut, that.dateOfCheckOut))
            return false;
        return Objects.equals(roomNumber, that.roomNumber);
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + (dateOfReservation != null ? dateOfReservation.hashCode() : 0);
        result = 31 * result + (dateOfCheckIn != null ? dateOfCheckIn.hashCode() : 0);
        result = 31 * result + (dateOfCheckOut != null ? dateOfCheckOut.hashCode() : 0);
        result = 31 * result + numberOfPersons;
        result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (isVisible ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(reservationId);
    }


    @Override
    public int compareTo(ReservationDto o) {
        return Integer.compare(this.reservationId, o.reservationId);
    }
}