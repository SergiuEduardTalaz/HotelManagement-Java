package ro.fortech.academy.presentation.rooms;

import ro.fortech.academy.business.dto.RoomDto;
import ro.fortech.academy.business.entities.Room;

import java.util.List;

public class RoomModel {
    private List<Room> roomList;
    private List<RoomDto> roomListDto;

    public RoomModel(List<Room> roomList, List<RoomDto> roomListDto) {
        this.roomList = roomList;
        this.roomListDto = roomListDto;
    }

    public List<RoomDto> getModelRoomDtoList() {
        return roomListDto;
    }

    public void setRoomListDto(List<RoomDto> roomListDto) {
        this.roomListDto = roomListDto;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public void addNewRoomInDB(Room room) {
        roomList.add(room);
    }
}
