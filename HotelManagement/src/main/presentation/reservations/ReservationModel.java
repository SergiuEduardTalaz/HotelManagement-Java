package ro.fortech.academy.presentation.reservations;

import ro.fortech.academy.business.dto.ReservationDto;
import ro.fortech.academy.business.entities.Reservation;

import java.util.List;

public class ReservationModel {
    private List<Reservation> reservationList;
    private List<ReservationDto> reservationDtoList;
    public ReservationModel(List<Reservation> reservationList, List<ReservationDto> reservationDtoList){
        this.reservationList= reservationList;
        this.reservationDtoList = reservationDtoList;
    }
    public List<ReservationDto> getReservationDtoList() {
        return reservationDtoList;
    }


    public void setReservationDtoList(List<ReservationDto> reservationDtoList) {
        this.reservationDtoList = reservationDtoList;
    }
    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public void addNewReservationInDB(Reservation reservation) {reservationList.add(reservation); }

    public void updateReservation(Reservation reservation){
    }

}
