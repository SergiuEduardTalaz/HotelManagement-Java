package ro.fortech.academy.persistence;

import ro.fortech.academy.business.dto.RoomDto;
import ro.fortech.academy.business.entities.Room;

import java.util.List;

public interface RoomDAO {
    List<Room> getAllRooms();
    List<RoomDto> getAllDtoRooms();
    List<RoomDto> searchForRoom(String roomId, String hotelName);
    void insertRoomDao(Room room);
    void disableRoomDao(int roomId);
    void updateRoomPriceDAO(int roomId, int newRoomPrice);
    void updateRoomStatusDAO(int roomId, String roomStatus);

    List<RoomDto> getAllRoomsNumber();
}
