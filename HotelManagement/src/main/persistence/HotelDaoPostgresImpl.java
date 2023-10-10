package ro.fortech.academy.persistence;


import ro.fortech.academy.business.dto.HotelDto;
import ro.fortech.academy.business.entities.Hotel;
import ro.fortech.academy.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HotelDaoPostgresImpl implements HotelDao {

    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from hotels");
            while (rs.next()) {
                Hotel hotel = extractedHotelFromResultSet(rs);
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return hotels;
    }

    public List<HotelDto> getAllHotelsName() {
        List<HotelDto> hotels = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT id, name from hotels");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                HotelDto hoteldto = new HotelDto(name, id);
                hotels.add(hoteldto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return hotels;
    }

    public List<Hotel> getAllHotelsServicesDao() {
        List<Hotel> hotels = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT services from hotels");
            while (rs.next()) {
                String services = rs.getString(1);
                Hotel hotel = new Hotel(services);
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return hotels;
    }

    @Override
    public void updateHotelPhoneDao(int hotelId, String phoneNumber) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String delete = "UPDATE hotels SET phone_number = ? WHERE ID = ?";
            statement = connection.prepareStatement(delete);
            statement.setString(1, phoneNumber);
            statement.setInt(2, hotelId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public List<Hotel> searchHotelServicesDao(String hotelServices) {
        List<Hotel> hotelList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String searchStringSql = "SELECT * from hotels where services = ?";
            statement = connection.prepareStatement(searchStringSql);
            statement.setString(1, hotelServices);
            rs = statement.executeQuery();
            while (rs.next()) {
                Hotel hotel = extractedHotelFromResultSet(rs);
                hotelList.add(hotel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return hotelList;
    }


    private Hotel extractedHotelFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString(2);
        String address = resultSet.getString(3);
        String phoneNumber = resultSet.getString(4);
        String services = resultSet.getString(5);
        Hotel result = new Hotel(id, name, address, phoneNumber, services);

        return result;
    }



}
