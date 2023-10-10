package ro.fortech.academy.persistence;

import ro.fortech.academy.business.entities.Customer;
import ro.fortech.academy.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDAOPostgresImpl implements CustomerDao {
    public List<Customer> loadAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from customers where isVisible = true");
            while (rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return customers;
    }

    private Customer getCustomerFromResultSet(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        Date dateOfBirthSQL = resultSet.getDate(4);
        LocalDate dateOfBirth = java.sql.Date.valueOf(dateOfBirthSQL.toString()).toLocalDate();
        String phoneNumber = resultSet.getString(5);
        String email = resultSet.getString(6);
        int reservationId = resultSet.getInt(7);
        boolean isVisible = resultSet.getBoolean(8);
        Customer customer = new Customer(id,firstName, lastName, dateOfBirth, phoneNumber, email, reservationId, isVisible);
        return customer;
    }


    @Override
    public void disableCustomer(int customerId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement("update customers set isVisible = false where ID =?");
            statement.setInt(1, customerId);
            statement.executeUpdate();
            System.out.println("Customer deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public void insertCustomer(Customer customer) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement("insert into customers(id , firstname , lastname , date_of_birth , phone_number , email , reservation_id, isVisible) VALUES (? ,? , ? , ? , ? , ? , ?, true)");
            setCustomerData(statement ,customer);
            statement.executeUpdate();
            System.out.println("Customer inserted successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    private static void setCustomerData(PreparedStatement statement ,Customer customer) throws SQLException {
        statement.setInt(1, customer.getCustomerId());
        statement.setString(2, customer.getFirstName());
        statement.setString(3, customer.getLastName());
        statement.setDate(4, java.sql.Date.valueOf(customer.getDateOfBirth()));
        statement.setString(5, customer.getPhoneNumber());
        statement.setString(6, customer.getEmail());
        statement.setInt(7, customer.getReservationId());
    }

    @Override
    public List<Customer> searchCustomer(int customerId) {
        List<Customer> customers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement("select * from customers where id = ? and isVisible = true");
            statement.setInt(1, customerId);
            rs = statement.executeQuery();
            while(rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }

        return customers;


    }

    @Override
    public List<Customer> searchCustomer(String lastName) {
        List<Customer> customers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement("select * from customers where lastname = ? and isVisible = true");
            statement.setString(1, lastName);
            rs = statement.executeQuery();
            while(rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }

        return customers;


    }

    @Override
    public void updateCustomer(Customer customer) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement("Update customers set firstname = ?, lastname = ? , date_of_birth = ? , phone_number = ? , email = ? , reservation_id = ? where id = ?");
            statement.setString(1,customer.getFirstName());
            statement.setString(2,customer.getLastName());
            statement.setDate(3, java.sql.Date.valueOf(customer.getDateOfBirth()));
            statement.setString(4,customer.getPhoneNumber());
            statement.setString(5,customer.getEmail());
            statement.setInt(6,customer.getReservationId());
            statement.setInt(7, customer.getCustomerId());


            statement.executeUpdate();
            System.out.println("Customer Successfully Updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

}
