package ro.fortech.academy.presentation.aggregator;

import ro.fortech.academy.business.services.*;
import ro.fortech.academy.persistence.*;
import ro.fortech.academy.presentation.customer.CustomerController;
import ro.fortech.academy.presentation.customer.CustomerModel;
import ro.fortech.academy.presentation.customer.CustomerView;
import ro.fortech.academy.presentation.hotel.HotelController;
import ro.fortech.academy.presentation.hotel.HotelModel;
import ro.fortech.academy.presentation.hotel.HotelView;
import ro.fortech.academy.presentation.login.LoginController;
import ro.fortech.academy.presentation.login.LoginModel;
import ro.fortech.academy.presentation.login.LoginView;
import ro.fortech.academy.presentation.payments.PaymentController;
import ro.fortech.academy.presentation.payments.PaymentModel;
import ro.fortech.academy.presentation.payments.PaymentView;
import ro.fortech.academy.presentation.reservations.ReservationController;
import ro.fortech.academy.presentation.reservations.ReservationModel;
import ro.fortech.academy.presentation.reservations.ReservationView;
import ro.fortech.academy.presentation.rooms.RoomController;
import ro.fortech.academy.presentation.rooms.RoomModel;
import ro.fortech.academy.presentation.rooms.RoomView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class AggregatorApp extends JFrame {
    private HotelService hotelService;
    private HotelController hotelController;
    private HotelModel hotelModel;
    private HotelView hotelView;
    private RoomService roomService;
    private RoomController roomController;
    private RoomModel roomModel;
    private RoomView roomView;
    private ReservationService reservationService;
    private ReservationController reservationController;
    private ReservationModel reservationModel;
    private ReservationView reservationView;
    private CustomerService customerService;
    private CustomerController customerController;
    private CustomerModel customerModel;
    private CustomerView customerView;
    private PaymentService paymentService;
    private PaymentController paymentController;
    private PaymentModel paymentModel;
    private PaymentView paymentView;
    private LoginModel loginModel;
    private LoginController loginController;
    private LoginView loginView;

    public AggregatorApp() {
        super("Main App");
        JPanel content = new JPanel(new GridBagLayout());

        ImageIcon mainAppIcon = new ImageIcon("src/resources/icon.png");
        setIconImage(mainAppIcon.getImage());

        GridBagConstraints imageConstraints = new GridBagConstraints();
        imageConstraints.gridx = 0;
        imageConstraints.gridy = 0;
        imageConstraints.gridwidth = 3;
        imageConstraints.insets = new Insets(10, 10, 10, 10);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.gridwidth = 1;
        buttonConstraints.insets = new Insets(10, 10, 10, 10);

        JButton hotelButton = createButton("To Hotel Menu");
        JButton roomButton = createButton("To Rooms Menu");
        JButton customerButton = createButton("To Customers Menu");
        JButton reservationButton = createButton("To Reservations Menu");
        JButton paymentButton = createButton("To Payments Menu");
        JButton exitButton = createLogOutButton();

        hotelButton.addActionListener(e -> showHotelView());
        customerButton.addActionListener(e -> showCustomerView());
        roomButton.addActionListener(e -> showRoomView());
        reservationButton.addActionListener(e -> showReservationView());
        paymentButton.addActionListener(e -> showPaymentView());
        exitButton.addActionListener(e -> logOutFromApplication());

        ImageIcon imageIcon = new ImageIcon("src/resources/welcome-image.jpg");
        JLabel imageLabel = new JLabel(imageIcon);

        content.add(imageLabel, imageConstraints);
        content.add(hotelButton, buttonConstraints);
        buttonConstraints.gridx++;
        content.add(roomButton, buttonConstraints);
        buttonConstraints.gridx++;
        content.add(customerButton, buttonConstraints);
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy++;
        content.add(reservationButton, buttonConstraints);
        buttonConstraints.gridx++;
        content.add(paymentButton, buttonConstraints);
        buttonConstraints.gridx++;
        content.add(exitButton, buttonConstraints);

        this.add(content);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 141, 188));
        button.setBorder(BorderFactory.createLineBorder(new Color(44, 101, 144), 2));
        button.setFocusPainted(false);
        return button;
    }

    private JButton createLogOutButton() {
        JButton exitButton = new JButton("Log Out");
        exitButton.setPreferredSize(new Dimension(150, 50));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.RED);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(144, 44, 44), 2));
        exitButton.setFocusPainted(false);
        return exitButton;
    }

    private void logOutFromApplication() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out from the app?", "Confirm Log Out", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            loginModel = new LoginModel();
            loginView = new LoginView();
            loginController = new LoginController(loginModel, loginView);
            loginController.setLoginView(loginView);
            loginView.setLoginController(loginController);
            this.dispose();
        }
    }

    private void showHotelView() {
        hotelService = new HotelService(new HotelDaoPostgresImpl());
        hotelModel = new HotelModel(hotelService.getAllHotels());
        hotelView = new HotelView(hotelModel.getHotelList());
        hotelController = new HotelController(hotelView, hotelModel, hotelService);
        hotelView.setController(hotelController);
        hotelView.setVisible(true);
        this.dispose();
    }

    private void showRoomView() {
        roomService = new RoomService(new RoomDAOPostgresImpl());
        hotelService = new HotelService(new HotelDaoPostgresImpl());
        roomModel = new RoomModel(roomService.getRooms(), roomService.getRoomsDtoService());
        roomView = new RoomView(roomModel.getRoomList());
        roomController = new RoomController(roomModel, roomView, roomService, hotelService);
        roomView.setController(roomController);
        roomView.setVisible(true);
        this.dispose();
    }

    private void showReservationView() {
        reservationService = new ReservationService(new ReservationDAOPostgresImpl());
        roomService = new RoomService(new RoomDAOPostgresImpl());
        reservationModel = new ReservationModel(reservationService.getReservations(), reservationService.getReservationsDto());
        reservationView = new ReservationView(reservationModel.getReservationList());
        reservationController = new ReservationController(reservationModel, reservationView, reservationService, roomService);
        reservationView.setController(reservationController);
        reservationView.setVisible(true);
        this.dispose();
    }

    private void showCustomerView() {
        customerService = new CustomerService(new CustomerDAOPostgresImpl());
        reservationService = new ReservationService(new ReservationDAOPostgresImpl());
        customerModel = new CustomerModel(customerService.getCustomers());
        customerView = new CustomerView(customerModel.getCustomerList());
        customerController = new CustomerController(customerModel, customerView, customerService, reservationService);
        customerView.setController(customerController);
        customerView.setVisible(true);
        this.dispose();
    }

    private void showPaymentView() {
        paymentService = new PaymentService(new PaymentDAOPostgresImpl());
        paymentModel = new PaymentModel(paymentService.getPayments(),paymentService.getPaymentsWithCustomerData());
        paymentView = new PaymentView(paymentModel.getPaymentList());
        paymentController = new PaymentController(paymentModel, paymentView, paymentService);
        paymentView.setController(paymentController);
        paymentView.setVisible(true);
        this.dispose();
    }
}
