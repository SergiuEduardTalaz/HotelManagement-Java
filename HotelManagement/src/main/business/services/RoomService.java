package ro.fortech.academy.business.services;

import ro.fortech.academy.business.dto.RoomDto;
import ro.fortech.academy.business.entities.Room;
import ro.fortech.academy.persistence.RoomDAO;

import java.util.List;
import java.util.stream.Collectors;

public class RoomService {
    private final RoomDAO roomDAO;

    public RoomService(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public List<Room> getRooms() {
        List<Room> list = roomDAO.getAllRooms();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public List<RoomDto>roomNumberDtoList(){
        List<RoomDto> list = roomDAO.getAllRoomsNumber();
        return list.stream().sorted().collect(Collectors.toList());
    }

    public List<RoomDto> searchRoom(String roomId, String hotelName) {
        return roomDAO.searchForRoom(roomId, hotelName);
    }

    public Room createNewRoomService(Room room) {
        roomDAO.insertRoomDao(room);
        return room;
    }

    public void disableRoomService(int roomId) {
        roomDAO.disableRoomDao(roomId);
    }

    public List<RoomDto> getRoomsDtoService() {
        return roomDAO.getAllDtoRooms();
    }

    public void updateRoomPriceService(int roomId, int newRoomPrice) {
        roomDAO.updateRoomPriceDAO(roomId,newRoomPrice);
    }

    public void updateRoomStatusService(int roomId, String newRoomStatus) {
        roomDAO.updateRoomStatusDAO(roomId,newRoomStatus);
    }
}
