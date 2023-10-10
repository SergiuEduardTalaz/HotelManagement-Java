package ro.fortech.academy.persistence;

import ro.fortech.academy.business.dto.ReservationDto;
import ro.fortech.academy.business.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
    List<Reservation> getAllReservations();
    List<ReservationDto> getAllDtoReservations();
    List<ReservationDto> getReservationDtoList();
    List<ReservationDto> searchReservation(LocalDate dateOfReservation, String roomNumber);
    void insertReservation(Reservation reservation);
    void updateDateOfReservationInDB(int reservationId, LocalDate newDateOfReservation);
    void disableReservationDao(int reservationId);
}
