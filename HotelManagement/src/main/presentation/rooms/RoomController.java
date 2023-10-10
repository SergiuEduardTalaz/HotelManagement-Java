package ro.fortech.academy.presentation.rooms;

import ro.fortech.academy.business.dto.HotelDto;
import ro.fortech.academy.business.dto.RoomDto;
import ro.fortech.academy.business.entities.Room;
import ro.fortech.academy.business.services.HotelService;
import ro.fortech.academy.business.services.RoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RoomController {
    private final RoomModel model;
    private final RoomView view;
    private final RoomService service;
    private final HotelService hotelService;

    public RoomController(RoomModel model, RoomView view, RoomService service, HotelService hotelService) {
        this.model = model;
        this.view = view;
        this.service = service;
        this.hotelService = hotelService;
    }

    public void roomsButton() {
        model.setRoomListDto(service.getRoomsDtoService());
        view.showRooms(model.getModelRoomDtoList());
    }

    public boolean searchRoomsButton(String roomId, String hotelName) {
        List<RoomDto> searchedRoom = service.searchRoom(roomId, hotelName);
        if (searchedRoom != null && !searchedRoom.isEmpty()) {
            model.setRoomListDto(searchedRoom);
            view.showRooms(model.getModelRoomDtoList());
            return true;
        } else {
            return false;
        }
    }


    public void addNewRoom(Room room) {
        model.addNewRoomInDB(service.createNewRoomService(room));
    }

    public boolean isRoomIdUnique(int roomId) {
        for (Room room : model.getRoomList()) {
            if (room.getRoomId() == roomId) {
                return false;
            }
        }

        return true;
    }

    public int generateRandomRoomId() {
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

    public List<HotelDto> getHotelNames() {
        return hotelService.getHotelNames();
    }

    public void disableRoom(int roomId) {
        service.disableRoomService(roomId);
        List<Room> roomList = new ArrayList<>(model.getRoomList());
        List<Room> newRoomList = roomList.stream().filter(room -> room.getRoomId() == roomId).collect(Collectors.toList());
        model.setRoomList(newRoomList);
    }

    public void updateRoomPrice(int roomId, int newRoomPrice) {
        service.updateRoomPriceService(roomId, newRoomPrice);
    }

    public void updateRoomStatus(int roomId, String newRoomStatus) {
        service.updateRoomStatusService(roomId, newRoomStatus);
    }
}
