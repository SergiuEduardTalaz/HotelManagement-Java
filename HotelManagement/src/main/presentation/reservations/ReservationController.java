package ro.fortech.academy.presentation.reservations;

import ro.fortech.academy.business.dto.ReservationDto;
import ro.fortech.academy.business.dto.RoomDto;
import ro.fortech.academy.business.entities.Reservation;
import ro.fortech.academy.business.services.ReservationService;
import ro.fortech.academy.business.services.RoomService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ReservationController {
    private final ReservationModel model;
    private final ReservationView view;
    private final ReservationService service;
    private final RoomService roomService;

    public ReservationController(ReservationModel model, ReservationView view, ReservationService service, RoomService roomService) {
        this.model = model;
        this.view = view;
        this.service = service;
        this.roomService = roomService;
    }

    public void reservationButton() {
        model.setReservationDtoList(service.getReservationsDto());
        view.showReservations(model.getReservationDtoList());
    }

    public void addNewReservation(Reservation reservation) {
        model.addNewReservationInDB(service.createReservationInDB(reservation));
    }

    public boolean searchReservationButton(LocalDate dateOfReservation, String roomNumber) {
        List<ReservationDto> searchReservation = service.searchReservation(dateOfReservation, roomNumber);
        if (searchReservation != null && !searchReservation.isEmpty()) {
            model.setReservationDtoList(searchReservation);
            view.showReservations(model.getReservationDtoList());
            return true;
        } else {
            return false;
        }
    }

    public void disableReservation(int reservationId){
        service.disableReservationService(reservationId);
        List<Reservation> reservationsList = new ArrayList<>(model.getReservationList());
        List<Reservation> newReservationsList = reservationsList.stream().filter(reservation -> reservation.getReservationId() == reservationId).collect(Collectors.toList());
        model.setReservationList(newReservationsList);
    }

    public List<RoomDto> roomNumberDtoList() {
        return roomService.roomNumberDtoList();
    }

    public boolean isRoomIdUnique(int reservationId) {
        for (Reservation reservation : model.getReservationList()) {
            if (reservation.getReservationId() == reservationId) {
                return false;
            }
        }

        return true;
    }

    public int generateRandomReservationId() {
        Random random = new Random();
        int maxAttempts = 10000;
        int attempts = 0;

        while (attempts < maxAttempts) {
            int roomId = random.nextInt(10000);

            if (isRoomIdUnique(roomId)) {
                return roomId;
            }
            attempts++;
        }
        view.showErrorMessageId();
        throw new IllegalStateException("Unable to generate a unique Room ID after multiple attempts.");
    }
}
