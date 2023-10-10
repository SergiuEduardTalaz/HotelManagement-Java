package ro.fortech.academy.business.dto;
import java.util.Objects;
public class RoomDto implements Comparable<RoomDto> {
    private int id;
    private String numberOfRoom;
    private String type;
    private int capacity;
    private int price;
    private String status;
    private String hotelName;
    private boolean isVisible;
    public RoomDto(int id, String numberOfRoom, String type, int capacity, int price, String status, String hotelName, boolean isVisible) {
        this.id = id;
        this.numberOfRoom = numberOfRoom;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
        this.hotelName = hotelName;
        this.isVisible = isVisible;
    }
    public RoomDto(int id, String numberOfRoom){
        this.id = id;
        this.numberOfRoom = numberOfRoom;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNumberOfRoom() {
        return numberOfRoom;
    }
    public void setNumberOfRoom(String numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }
    public String getHotelName() {
        return hotelName;
    }
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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
        RoomDto roomDto = (RoomDto) o;
        if (capacity != roomDto.capacity) return false;
        if (price != roomDto.price) return false;
        if (isVisible != roomDto.isVisible) return false;
        if (!Objects.equals(numberOfRoom, roomDto.numberOfRoom))
            return false;
        if (!Objects.equals(type, roomDto.type)) return false;
        return Objects.equals(status, roomDto.status);
    }
    @Override
    public int hashCode() {
        int result = numberOfRoom != null ? numberOfRoom.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + capacity;
        result = 31 * result + price;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isVisible ? 1 : 0);
        return result;
    }
    @Override
    public String toString() {
        return numberOfRoom;
    }
    @Override
    public int compareTo(RoomDto o) {
        return this.numberOfRoom.compareTo(o.numberOfRoom);
    }
}