package ro.fortech.academy.persistence;

import ro.fortech.academy.business.dto.RoomDto;
import ro.fortech.academy.business.entities.Room;
import ro.fortech.academy.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOPostgresImpl implements RoomDAO {
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from rooms WHERE IsVisible = true");
            while (rs.next()) {
                Room room = getRoomsFromResultSet(rs);
                rooms.add(room);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return rooms;
    }

    public List<RoomDto> getAllDtoRooms() {
        List<RoomDto> rooms = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT rooms.numberofroom, rooms.type, rooms.capacity, rooms.price, rooms.status, hotels.name, rooms.id from rooms JOIN hotels ON rooms.hotelID = hotels.id WHERE rooms.isVisible = true");
            while (rs.next()) {
                RoomDto room = getRoomsDtoFromResultSet(rs);
                rooms.add(room);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return rooms;
    }

    private Room getRoomsFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String numberOfRooms = resultSet.getString(2);
        String type = resultSet.getString(3);
        int capacity = resultSet.getInt(4);
        int price = resultSet.getInt(5);
        String status = resultSet.getString(6);
        int hotelId = resultSet.getInt(7);
        boolean isVisible = resultSet.getBoolean(8);
        Room room = new Room(id, numberOfRooms, type, capacity, price, status, hotelId, isVisible);

        return room;
    }

    private RoomDto getRoomsDtoFromResultSet(ResultSet resultSet) throws SQLException {
        String numberOfRooms = resultSet.getString(1);
        String type = resultSet.getString(2);
        int capacity = resultSet.getInt(3);
        int price = resultSet.getInt(4);
        String status = resultSet.getString(5);
        String hotelName = resultSet.getString(6);
        int id = resultSet.getInt(7);
        RoomDto roomdto = new RoomDto(id, numberOfRooms, type, capacity, price, status, hotelName, true);

        return roomdto;
    }

    @Override
    public List<RoomDto> getAllRoomsNumber() {
        List<RoomDto> roomDtoList = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT id, numberOfRoom FROM rooms WHERE isVisible = true");
            while (rs.next()) {
                int id = rs.getInt(1);
                String numberOfRooms = rs.getString(2);
                RoomDto roomDto = new RoomDto(id, numberOfRooms);
                roomDtoList.add(roomDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return roomDtoList;
    }

    public List<RoomDto> searchForRoom(String roomNumber, String hotelName) {
        List<RoomDto> rooms = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String searchStringSql = "SELECT rooms.numberofroom, rooms.type, rooms.capacity, rooms.price, rooms.status, hotels.name, rooms.id from rooms JOIN hotels ON rooms.hotelID = hotels.id where rooms.numberofroom = ? and hotels.name = ?";
            statement = connection.prepareStatement(searchStringSql);
            statement.setString(1, roomNumber);
            statement.setString(2, hotelName);
            rs = statement.executeQuery();
            while (rs.next()) {
                RoomDto roomDto = getRoomsDtoFromResultSet(rs);
                rooms.add(roomDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return rooms;
    }

    public void insertRoomDao(Room room) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String createRoom = "INSERT INTO ROOMS (id, numberofroom, type, capacity, price, status, hotelid, isVisible) VALUES (?, ?, ?, ?, ?, ?,?, true)";
            statement = connection.prepareStatement(createRoom);
            setRoomStatement(statement, room);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    private void setRoomStatement(PreparedStatement statement, Room room) throws SQLException {
        statement.setInt(1, room.getRoomId());
        statement.setString(2, room.getNumberOfRoom());
        statement.setString(3, room.getType());
        statement.setInt(4, room.getCapacity());
        statement.setInt(5, room.getPrice());
        statement.setString(6, room.getStatus());
        statement.setInt(7, room.getHotelId());
    }

    public void disableRoomDao(int roomId) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String disableRoom = "UPDATE rooms SET IsVisible = false WHERE ID =?";
            statement = connection.prepareStatement(disableRoom);
            statement.setInt(1, roomId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public void updateRoomPriceDAO(int roomId, int newRoomPrice) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String delete = "UPDATE rooms SET price = ? WHERE ID = ?";
            statement = connection.prepareStatement(delete);
            statement.setInt(1, newRoomPrice);
            statement.setInt(2, roomId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public void updateRoomStatusDAO(int roomId, String roomStatus) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String delete = "UPDATE rooms SET status = ? WHERE ID = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, roomStatus);
            statement.setInt(2, roomId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }
}
