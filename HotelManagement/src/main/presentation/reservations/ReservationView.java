package ro.fortech.academy.presentation.reservations;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ro.fortech.academy.business.dto.ReservationDto;
import ro.fortech.academy.business.dto.RoomDto;
import ro.fortech.academy.business.entities.Reservation;
import ro.fortech.academy.presentation.aggregator.AggregatorApp;
import ro.fortech.academy.presentation.payments.DateLabelFormatter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class ReservationView extends JFrame {
    private DefaultTableModel tableModel;
    private ReservationController controller;

    private JTable table;

    public ReservationView(List<Reservation> reservationList) {
        super("Reservation Window");

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton showReservationsButton = createStyledButtons("Show Reservations", new ImageIcon("src/resources/show.png"), 24, 24);
        JButton searchReservationButton = createStyledButtons("Search for Reservation ", new ImageIcon("src/resources/search.png"), 24, 24);
        JButton insertReservationButton = createStyledButtons("Insert New Reservation ", new ImageIcon("src/resources/insert.png"), 24, 24);
        JButton deleteReservationButton = createStyledButtons("Disable Reservation", new ImageIcon("src/resources/disable.png"), 24, 24);
        JButton buttonBack = createBackButton("Back", new ImageIcon("src/resources/backarrow.png"), 24, 24);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.weighty = 1.0;
        content.add(showReservationsButton, constraints);

        constraints.gridy = 1;
        content.add(searchReservationButton, constraints);

        constraints.gridy = 2;
        content.add(insertReservationButton, constraints);

        constraints.gridy = 3;
        content.add(deleteReservationButton, constraints);

        constraints.gridy = 4;
        content.add(buttonBack, constraints);

        JScrollPane pane = getReservationTable();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        content.add(pane, constraints);

        showReservationsButton.addActionListener(e -> controller.reservationButton());
        searchReservationButton.addActionListener(e -> searchReservation());
        insertReservationButton.addActionListener(e -> createReservation(controller.roomNumberDtoList()));
        deleteReservationButton.addActionListener(e -> disableReservation());
        buttonBack.addActionListener(e -> handleClickBackButton());


        ImageIcon reservationIcon = new ImageIcon("src/resources/reservation.png");
        setIconImage(reservationIcon.getImage());
        this.setContentPane(content);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private JButton createStyledButtons(String text, ImageIcon icon, int iconWidth, int iconHeight) {
        JButton button = new JButton(text);
        button.setIcon(resizeIcon(icon, iconWidth, iconHeight));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(251, 255, 209));
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
        button.setFocusPainted(false);
        return button;
    }

    private JButton createBackButton(String text, ImageIcon icon, int iconWidth, int iconHeight) {
        JButton button = new JButton(text);
        button.setIcon(resizeIcon(icon, iconWidth, iconHeight));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(220, 220, 220));
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
        button.setFocusPainted(false);
        return button;
    }

    private void handleClickBackButton() {
        AggregatorApp aggregatorApp = new AggregatorApp();
        aggregatorApp.setVisible(true);
        this.dispose();
    }

    public JScrollPane getReservationTable() {
        String[] header = {"ReservationID", "Date of Reservation", "Date of Check-in", "Date of Check-out", "Number of people", "Room Number", "Price", "Is Visible"};
        tableModel = new DefaultTableModel(header, 0);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 400));
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        table.getColumnModel().getColumn(7).setMaxWidth(0);
        table.getColumnModel().getColumn(7).setMinWidth(0);
        table.getColumnModel().getColumn(7).setPreferredWidth(0);
        JScrollPane pane = new JScrollPane(table);
        return pane;
    }

    public ReservationController getController() {
        return controller;
    }

    public void setController(ReservationController controller) {
        this.controller = controller;
    }

    public void searchReservation() {
        while (true) {
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));

            JDatePickerImpl datePicker = createJDatePicker();
            inputPanel.add(new JLabel("Date of Reservation:"));
            inputPanel.add(datePicker);

            JComboBox<RoomDto> box = new JComboBox<>();
            controller.roomNumberDtoList().forEach(box::addItem);
            inputPanel.add(new JLabel("Room Number:"));
            inputPanel.add(box);

            int option = JOptionPane.showConfirmDialog(this, inputPanel, "Search Reservation", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Date selectedDate = (Date) datePicker.getModel().getValue();
                RoomDto selectedRoom = (RoomDto) box.getSelectedItem();
                if (selectedDate == null || selectedRoom == null) {
                    JOptionPane.showMessageDialog(this, "Please fill in all the required fields!", "Incomplete Fields", JOptionPane.ERROR_MESSAGE);
                } else {
                    LocalDate dateOfReservationInput = convertDateToLocalDate(selectedDate);

                    boolean reservationsFound = controller.searchReservationButton(dateOfReservationInput, selectedRoom.getNumberOfRoom());

                    if (!reservationsFound) {
                        JOptionPane.showMessageDialog(this, "No reservations found for the given criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    public void createReservation(List<RoomDto> roomNumbers) {
        while (true) {
            JDatePickerImpl dateOfReservationField = createJDatePicker();
            JDatePickerImpl dateOfCheckInField = createJDatePicker();
            JDatePickerImpl dateOfCheckOutField = createJDatePicker();
            JTextField numberOfPersonsField = new JTextField();
            JComboBox<RoomDto> listOfRoomNumbers = new JComboBox<>();
            roomNumbers.forEach(listOfRoomNumbers::addItem);

            Object[] dialogFields = {
                    "Date of Reservation:", dateOfReservationField,
                    "Date of Check-in:", dateOfCheckInField,
                    "Date of Check-out:", dateOfCheckOutField,
                    "Number of Persons:", numberOfPersonsField,
                    "Room Number:", listOfRoomNumbers
            };

            int option = JOptionPane.showConfirmDialog(null, dialogFields, "Create Reservation", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    int reservationId = controller.generateRandomReservationId();
                    Date dateOfReservationUnconverted = (Date) dateOfReservationField.getModel().getValue();
                    LocalDate dateOfReservation = null;
                    if (dateOfReservationUnconverted != null) {
                        dateOfReservation = convertDateToLocalDate(dateOfReservationUnconverted);
                    }
                    Date dateOfCheckInUnconverted = (Date) dateOfCheckInField.getModel().getValue();
                    LocalDate dateOfCheckIn = null;
                    if (dateOfCheckInUnconverted != null) {
                        dateOfCheckIn = convertDateToLocalDate(dateOfCheckInUnconverted);
                    }
                    Date dateOfCheckOutUnconverted = (Date) dateOfCheckOutField.getModel().getValue();
                    LocalDate dateOfCheckOut = null;
                    if (dateOfCheckOutUnconverted != null) {
                        dateOfCheckOut = convertDateToLocalDate(dateOfCheckOutUnconverted);
                    }
                    int numberOfPersons = Integer.parseInt(numberOfPersonsField.getText());
                    RoomDto item = (RoomDto) listOfRoomNumbers.getSelectedItem();
                    assert item != null;
                    int roomId = item.getId();

                    if (dateOfReservation == null || dateOfCheckIn == null || dateOfCheckOut == null || String.valueOf(numberOfPersons).isEmpty() || String.valueOf(roomId).isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all the required fields!", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
                    } else if (dateOfCheckIn.isBefore(dateOfReservation)) {
                        JOptionPane.showMessageDialog(this, "Date of Check-in cannot be earlier than Date of Reservation!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    } else if (dateOfCheckOut.isBefore(dateOfCheckIn)) {
                        JOptionPane.showMessageDialog(this, "Date of Check-out cannot be earlier than Date of Check-in!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean isVisible = true;
                        Reservation newReservation = new Reservation(reservationId, dateOfReservation, dateOfCheckIn, dateOfCheckOut, numberOfPersons, roomId, isVisible);
                        controller.addNewReservation(newReservation);

                        JOptionPane.showMessageDialog(this, "Reservation added successfully!", "Adding reservation", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numeric values for Number Of Persons.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                break;
            }
        }
    }


    public void disableReservation() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to disable this reservation?", "Confirm ", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                int reservationId = (int) table.getValueAt(row, 0);
                controller.disableReservation(reservationId);
                JOptionPane.showMessageDialog(this, "Reservation disabled successfully!", "Disable Reservation", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row in order to disable a reservation.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private LocalDate convertDateToLocalDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        return LocalDate.parse(dateString);
    }

    private JDatePickerImpl createJDatePicker() {
        UtilDateModel dateModel = new UtilDateModel();
        Properties p = new Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }


    public void showReservations(List<ReservationDto> reservationList) {
        tableModel.setRowCount(0);
        for (ReservationDto reservationDto : reservationList) {
            tableModel.addRow(new Object[]{
                    reservationDto.getReservationId(),
                    reservationDto.getDateOfReservation(),
                    reservationDto.getDateOfCheckIn(),
                    reservationDto.getDateOfCheckOut(),
                    reservationDto.getNumberOfPersons(),
                    reservationDto.getRoomNumber(),
                    reservationDto.getPrice(),
                    reservationDto.isVisible()
            });
        }
    }

    public void showErrorMessageId() {
        JOptionPane.showMessageDialog(this, "Unable to generate a unique Reservation ID after multiple attempts. Please try again later!", "Error Room ID", JOptionPane.ERROR_MESSAGE);
    }
}
