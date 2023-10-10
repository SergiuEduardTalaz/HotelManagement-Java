package ro.fortech.academy.persistence;

import ro.fortech.academy.business.dto.ReservationDto;
import ro.fortech.academy.business.entities.Reservation;
import ro.fortech.academy.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDAOPostgresImpl implements ReservationDAO {
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from reservations WHERE IsVisible = true");
            while (rs.next()) {
                Reservation reservation = getReservationsFromResultSet(rs);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return reservations;
    }

    private Reservation getReservationsFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        Date dateOfReservationSQL = resultSet.getDate(2);
        LocalDate dateOfReservation = java.sql.Date.valueOf(dateOfReservationSQL.toString()).toLocalDate();
        Date dateOfCheckInSQL = resultSet.getDate(3);
        LocalDate dateOfCheckIn = java.sql.Date.valueOf(dateOfCheckInSQL.toString()).toLocalDate();
        Date dateOfCheckOutSQL = resultSet.getDate(4);
        LocalDate dateOfCheckOut = java.sql.Date.valueOf(dateOfCheckOutSQL.toString()).toLocalDate();
        int numberOfPersons = resultSet.getInt(5);
        int roomId = resultSet.getInt(6);
        boolean isVisible = resultSet.getBoolean(7);
        Reservation reservation = new Reservation(id, dateOfReservation, dateOfCheckIn, dateOfCheckOut, numberOfPersons, roomId, isVisible);

        return reservation;
    }

    @Override
    public List<ReservationDto> getAllDtoReservations() {
        List<ReservationDto> reservations = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT reservations.reservationId, reservations.dateOfReservation, reservations.dateOfCheckIn, reservations.dateOfCheckOut, reservations.numberOfPersons, rooms.numberofroom, rooms.price, reservations.isVisible from reservations JOIN rooms ON reservations.roomID = rooms.id where reservations.isVisible = true;");
            while (rs.next()) {
                ReservationDto reservation = getReservationsDtoFromResultSet(rs);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return reservations;
    }

    private ReservationDto getReservationsDtoFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        Date dateOfReservation = resultSet.getDate(2);
        Date dateOfCheckIn = resultSet.getDate(3);
        Date dateOfCheckOut = resultSet.getDate(4);
        int numberOfPersons = resultSet.getInt(5);
        String roomNumber = resultSet.getString(6);
        int price = resultSet.getInt(7);
        boolean isVisible = resultSet.getBoolean(8);
        ReservationDto reservationDto = new ReservationDto(id, dateOfReservation, dateOfCheckIn, dateOfCheckOut, numberOfPersons, roomNumber, price, isVisible);

        return reservationDto;
    }

    @Override
    public List<ReservationDto> searchReservation(LocalDate dateOfReservation, String roomNumber) {
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String searchReservationSql = "SELECT reservations.reservationId, reservations.dateOfReservation, reservations.dateOfCheckIn, reservations.dateOfCheckOut, reservations.numberOfPersons, rooms.numberofroom, rooms.price, rooms.isVisible from reservations JOIN rooms ON reservations.roomID = rooms.id where reservations.dateOfReservation = ? and rooms.numberofroom = ?";
            statement = connection.prepareStatement(searchReservationSql);
            statement.setDate(1, java.sql.Date.valueOf(dateOfReservation));
            statement.setString(2, roomNumber);
            rs = statement.executeQuery();
            while (rs.next()) {
                ReservationDto reservationDto = getReservationsDtoFromResultSet(rs);
                reservationDtoList.add(reservationDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return reservationDtoList;
    }

    @Override
    public void insertReservation(Reservation reservation) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String createReservation = "INSERT INTO RESERVATIONS (reservationId, dateOfReservation, dateOfCheckIn, dateOfCheckOut, numberOfPersons, roomId, isVisible) VALUES (?, ?, ?, ?, ?, ?, true)";
            statement = connection.prepareStatement(createReservation);
            setRoomStatement(statement, reservation);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    private void setRoomStatement(PreparedStatement statement, Reservation reservation) throws SQLException {
        statement.setInt(1, reservation.getReservationId());
        statement.setDate(2,java.sql.Date.valueOf(reservation.getDateOfReservation()));
        statement.setDate(3,java.sql.Date.valueOf(reservation.getDateOfCheckIn()));
        statement.setDate(4,java.sql.Date.valueOf(reservation.getDateOfCheckOut()));
        statement.setInt(5, reservation.getNumberOfPersons());
        statement.setInt(6, reservation.getRoomId());
    }

    @Override
    public void updateDateOfReservationInDB(int reservationId, LocalDate newDateOfReservation) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String delete = "UPDATE reservations SET dateofreservation = ? WHERE RESERVATIONID = ?";
            statement = connection.prepareStatement(delete);
            statement.setDate(1, java.sql.Date.valueOf(newDateOfReservation));
            statement.setInt(2, reservationId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    public List<ReservationDto> getReservationDtoList() {
        List<ReservationDto> reservation = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("Select reservationid FROM reservations WHERE isVisible = true");
            while (rs.next()) {
                int id = rs.getInt(1);
                ReservationDto reservationDto = new ReservationDto(id);
                reservation.add(reservationDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return reservation;
    }

    @Override
    public void disableReservationDao(int reservationId) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String disable = "UPDATE reservations SET IsVisible = false WHERE RESERVATIONID =?";
            statement = connection.prepareStatement(disable);
            statement.setInt(1, reservationId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }
}
