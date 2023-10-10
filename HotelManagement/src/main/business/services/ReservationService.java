package ro.fortech.academy.business.services;

import ro.fortech.academy.business.dto.ReservationDto;
import ro.fortech.academy.business.entities.Reservation;
import ro.fortech.academy.persistence.ReservationDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> getReservations() {
        List<Reservation> list = reservationDAO.getAllReservations();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public Reservation createReservationInDB(Reservation reservation) {
        reservationDAO.insertReservation(reservation);
        return reservation;
    }

    public List<ReservationDto> searchReservation(LocalDate dateOfReservation, String roomNumber) {
        return reservationDAO.searchReservation(dateOfReservation, roomNumber);
    }

    public void disableReservationService(int reservationId) {
        reservationDAO.disableReservationDao(reservationId);
    }

    public List<ReservationDto> getReservationsDto() {
        return reservationDAO.getAllDtoReservations();
    }

    public List<ReservationDto> getReservationDto() {
        List<ReservationDto> reservationDtoList = reservationDAO.getReservationDtoList();
        return reservationDtoList.stream().sorted().collect(Collectors.toList());
    }

    public void updateDateOfReservationService(int reservationId, LocalDate newDateOfReservation) {
        reservationDAO.updateDateOfReservationInDB(reservationId, newDateOfReservation);
    }
}
