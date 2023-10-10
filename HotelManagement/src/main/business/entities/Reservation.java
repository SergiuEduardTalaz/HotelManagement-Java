package ro.fortech.academy.business.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation implements Comparable<Reservation> {

    private int reservationId;
    private LocalDate dateOfReservation;

    private LocalDate dateOfCheckIn;

    private LocalDate dateOfCheckOut;

    private int numberOfPersons;

    private int roomId;

    private boolean isVisible;

    public Reservation(int reservationId, LocalDate dateOfReservation, LocalDate dateOfCheckIn, LocalDate dateOfCheckOut, int numberOfPersons, int roomId, boolean isVisible) {
        this.reservationId = reservationId;
        this.dateOfReservation = dateOfReservation;
        this.dateOfCheckIn = dateOfCheckIn;
        this.dateOfCheckOut = dateOfCheckOut;
        this.numberOfPersons = numberOfPersons;
        this.roomId = roomId;
        this.isVisible = isVisible;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDate getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(LocalDate dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }

    public LocalDate getDateOfCheckIn() {
        return dateOfCheckIn;
    }

    public void setDateOfCheckIn(LocalDate dateOfCheckIn) {
        this.dateOfCheckIn = dateOfCheckIn;
    }

    public LocalDate getDateOfCheckOut() {
        return dateOfCheckOut;
    }

    public void setDateOfCheckOut(LocalDate dateOfCheckOut) {
        this.dateOfCheckOut = dateOfCheckOut;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

        Reservation that = (Reservation) o;

        if (reservationId != that.reservationId) return false;
        if (numberOfPersons != that.numberOfPersons) return false;
        if (roomId != that.roomId) return false;
        if (isVisible != that.isVisible) return false;
        if (!Objects.equals(dateOfReservation, that.dateOfReservation))
            return false;
        if (!Objects.equals(dateOfCheckIn, that.dateOfCheckIn))
            return false;
        return Objects.equals(dateOfCheckOut, that.dateOfCheckOut);
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + (dateOfReservation != null ? dateOfReservation.hashCode() : 0);
        result = 31 * result + (dateOfCheckIn != null ? dateOfCheckIn.hashCode() : 0);
        result = 31 * result + (dateOfCheckOut != null ? dateOfCheckOut.hashCode() : 0);
        result = 31 * result + numberOfPersons;
        result = 31 * result + roomId;
        result = 31 * result + (isVisible ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", dateOfReservation=" + dateOfReservation +
                ", dateOfCheckIn=" + dateOfCheckIn +
                ", dateOfCheckOut=" + dateOfCheckOut +
                ", numberOfPersons=" + numberOfPersons +
                ", roomId=" + roomId +
                ", isVisible=" + isVisible +
                '}';
    }

    @Override
    public int compareTo(Reservation o) {
        return Integer.compare(this.reservationId, o.reservationId);
    }
}


