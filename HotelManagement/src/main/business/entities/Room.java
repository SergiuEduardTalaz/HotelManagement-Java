package ro.fortech.academy.business.entities;

import java.util.Objects;

public class Room implements Comparable<Room> {
    private int roomId;
    private String numberOfRoom;
    private String type;
    private int capacity;
    private int price;
    private String status;
    private int hotelId;
    private boolean isVisible;

    public Room(int roomId, String numberOfRoom, String type, int capacity, int price, String status, int hotelId, boolean isVisible) {
        this.roomId = roomId;
        this.numberOfRoom = numberOfRoom;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
        this.hotelId = hotelId;
        this.isVisible = isVisible;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(String numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
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
        Room room = (Room) o;
        return roomId == room.roomId && capacity == room.capacity && Double.compare(room.price, price) == 0 && hotelId == room.hotelId && Objects.equals(numberOfRoom, room.numberOfRoom) && Objects.equals(type, room.type) && Objects.equals(status, room.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, numberOfRoom, type, capacity, price, status, hotelId);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", numberOfRoom='" + numberOfRoom + '\'' +
                ", type='" + type + '\'' +
                ", capacity=" + capacity +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", hotelId=" + hotelId +
                '}';
    }

    @Override
    public int compareTo(Room o) {
        return Integer.compare(this.roomId, o.roomId);
    }
}


