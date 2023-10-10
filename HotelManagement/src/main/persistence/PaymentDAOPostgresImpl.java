package ro.fortech.academy.persistence;

import ro.fortech.academy.business.dto.PaymentDto;
import ro.fortech.academy.business.entities.Payment;
import ro.fortech.academy.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentDAOPostgresImpl implements PaymentDAO {
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * from payments");
            while (rs.next()) {
                Payment payment = getPaymentsFromResultSet(rs);
                payments.add(payment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return payments;
    }

    private Payment getPaymentsFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        Date dateOfPaymentSQL = resultSet.getDate(2);
        LocalDate dateOfPayment = java.sql.Date.valueOf(dateOfPaymentSQL.toString()).toLocalDate();
        double amount = resultSet.getDouble(3);
        String typeOfPayment = resultSet.getString(4);
        int customerId = resultSet.getInt(5);
        boolean isVisible = resultSet.getBoolean(6);

        return new Payment(id, dateOfPayment, amount, typeOfPayment, customerId, isVisible);
    }

    public void insertPayment(Payment payment) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement("INSERT INTO payments (id, date_of_payment, amount, type_of_payment, customer_id) VALUES (?, ?, ?, ?, ?)");
            setPaymentData(statement, payment);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    private void setPaymentData(PreparedStatement statement, Payment payment) throws SQLException {
        statement.setInt(1, payment.getPaymentId());
        statement.setDate(2, java.sql.Date.valueOf(payment.getDateOfPayment()));
        statement.setDouble(3, payment.getAmount());
        statement.setString(4, payment.getTypeOfPayment());
        statement.setInt(5, payment.getCustomerId());

    }

    public List<PaymentDto> searchForPayment(LocalDate dateOfPayment, String lastName) {
        List<PaymentDto> payments = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement("SELECT payments.date_of_payment, payments.amount, payments.type_of_payment, customers.firstname, customers.lastname, payments.id from payments JOIN customers ON payments.customer_id = customers.id WHERE payments.date_of_payment = ? and customers.lastname = ?");
            statement.setDate(1, java.sql.Date.valueOf(dateOfPayment));
            statement.setString(2, lastName);
            rs = statement.executeQuery();
            while (rs.next()) {
                PaymentDto paymentDto = getPaymentsWithCustomerDataFromResultSet(rs);
                payments.add(paymentDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return payments;
    }

    public void updatePaymentAmount(int paymentId, double amount) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement("UPDATE payments SET amount = ? WHERE Id = ?");
            statement.setDouble(1, amount);
            statement.setInt(2, paymentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    public List<PaymentDto> getAllPaymentsWithCustomerData() {
        List<PaymentDto> payments = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT payments.date_of_payment, payments.amount, payments.type_of_payment, customers.firstname, customers.lastname, payments.id from payments JOIN customers ON payments.customer_id = customers.id WHERE payments.isVisible = true");
            while (rs.next()) {
                PaymentDto payment = getPaymentsWithCustomerDataFromResultSet(rs);
                payments.add(payment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return payments;
    }

    private PaymentDto getPaymentsWithCustomerDataFromResultSet(ResultSet resultSet) throws SQLException {
        Date dateOfPayment = resultSet.getDate(1);
        double amount = resultSet.getDouble(2);
        String typeOfPayment = resultSet.getString(3);
        String firstName = resultSet.getString(4);
        String lastName = resultSet.getString(5);
        int id = resultSet.getInt(6);

        return new PaymentDto(id, dateOfPayment, amount, typeOfPayment, firstName, lastName, true);
    }

    public void disablePayment(int paymentId) {
        Connection connection = null;
        PreparedStatement statement;
        try {
            connection = DBUtil.getConnection();
            String delete = "UPDATE payments SET IsVisible = false WHERE ID =?";
            statement = connection.prepareStatement(delete);
            statement.setInt(1, paymentId);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
    }

    @Override
    public List<Payment> getAllCostumersId() {
        List<Payment> payments = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT customer_id from payments WHERE isVisible = true");
            while (rs.next()) {
                int id = rs.getInt(1);
                Payment payment = new Payment(id);
                payments.add(payment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeConnection(connection);
        }
        return payments;
    }
}
