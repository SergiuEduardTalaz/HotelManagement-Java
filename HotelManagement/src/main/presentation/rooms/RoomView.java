package ro.fortech.academy.presentation.rooms;

import ro.fortech.academy.business.dto.HotelDto;
import ro.fortech.academy.business.dto.RoomDto;
import ro.fortech.academy.business.entities.Room;
import ro.fortech.academy.presentation.aggregator.AggregatorApp;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class RoomView extends JFrame {
    private DefaultTableModel tableModel;
    private RoomController controller;
    private JTable table;

    public RoomView(List<Room> roomList) {
        super("Room Window");

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton showRoomsButton = createStyledButtons("Show Rooms", new ImageIcon("src/resources/show.png"), 24, 24);
        JButton searchRoomButton = createStyledButtons("Search for Room ", new ImageIcon("src/resources/search.png"), 24, 24);
        JButton insertRoomButton = createStyledButtons("Insert New Room ", new ImageIcon("src/resources/insert.png"), 24, 24);
        JButton updatePriceButton = createStyledButtons("Update Price", new ImageIcon("src/resources/update1.png"), 24, 24);
        JButton updateStatusButton = createStyledButtons("Update Status", new ImageIcon("src/resources/update1.png"), 24, 24);
        JButton deleteRoomButton = createStyledButtons("Disable Room", new ImageIcon("src/resources/disable.png"), 24, 24);
        JButton buttonBack = createBackButton("Back", new ImageIcon("src/resources/backarrow.png"), 24, 24);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.weighty = 1.0;
        content.add(showRoomsButton, constraints);

        constraints.gridy = 1;
        content.add(searchRoomButton, constraints);

        constraints.gridy = 2;
        content.add(insertRoomButton, constraints);

        constraints.gridy = 3;
        content.add(updatePriceButton, constraints);

        constraints.gridy = 4;
        content.add(updateStatusButton, constraints);

        constraints.gridy = 5;
        content.add(deleteRoomButton, constraints);

        constraints.gridy = 6;
        content.add(buttonBack, constraints);

        JScrollPane pane = getRoomTable();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        content.add(pane, constraints);

        showRoomsButton.addActionListener(e -> controller.roomsButton());
        searchRoomButton.addActionListener(e -> searchRoom());
        insertRoomButton.addActionListener(e -> createRoomButton(controller.getHotelNames()));
        updatePriceButton.addActionListener(e -> handlePriceButton());
        updateStatusButton.addActionListener(e -> handleStatusButton());
        deleteRoomButton.addActionListener(e -> disableRoom());
        buttonBack.addActionListener(e -> handleClickBackButton());

        ImageIcon roomIcon = new ImageIcon("src/resources/room.png");
        setIconImage(roomIcon.getImage());
        this.setContentPane(content);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);
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



    public JScrollPane getRoomTable() {
        String[] header = {"Room Nr.", "Type", "Capacity", "Price", "Status", "Hotel Name", "ID", "IsVisible"};
        tableModel = new DefaultTableModel(header, 0);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 400));
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setPreferredWidth(0);
        table.getColumnModel().getColumn(7).setMaxWidth(0);
        table.getColumnModel().getColumn(7).setMinWidth(0);
        table.getColumnModel().getColumn(7).setPreferredWidth(0);
        return new JScrollPane(table);
    }

    private void handlePriceButton() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String currentPrice = table.getValueAt(row, 3).toString();

            while (true) {
                String newPriceInput = JOptionPane.showInputDialog(this, "Enter the new price:", currentPrice);
                if (newPriceInput == null) {
                    break;
                }
                try {
                    int newPrice = Integer.parseInt(newPriceInput);
                    int roomId = (int) table.getValueAt(row, 6);
                    controller.updateRoomPrice(roomId, newPrice);
                    JOptionPane.showMessageDialog(this, "Room price updated successfully!", "Update Price", JOptionPane.INFORMATION_MESSAGE);
                    break;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid numeric value for the price.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to update the price.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void handleStatusButton() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String[] roomStatusOptions = {"", "disponibila", "ocupata"};
            JComboBox<String> statusComboBox = new JComboBox<>(roomStatusOptions);

            String currentStatus = table.getValueAt(row, 4).toString();
            statusComboBox.setSelectedItem(currentStatus);

            int result = JOptionPane.showConfirmDialog(this, statusComboBox, "Select Room Status", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String newStatusInput = (String) statusComboBox.getSelectedItem();
                assert newStatusInput != null;
                if (newStatusInput.equals("disponibila") || newStatusInput.equals("ocupata")) {
                    int roomId = (int) table.getValueAt(row, 6);
                    controller.updateRoomStatus(roomId, newStatusInput);
                    JOptionPane.showMessageDialog(this, "Room status updated successfully!", "Update Room Status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a valid room status.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to update the room status.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void handleClickBackButton() {
        AggregatorApp aggregatorApp = new AggregatorApp();
        aggregatorApp.setVisible(true);
        this.dispose();
    }

    public RoomController getController() {
        return controller;
    }

    public void setController(RoomController controller) {
        this.controller = controller;
    }

    private JComboBox<String> createComboBoxForTypeOfRoom(JComboBox<Integer> capacityField) {
        String[] typeOfRoomStrings = {"", "single", "double", "standard", "twin"};
        JComboBox<String> listWithTypesOfRoom = new JComboBox<>(typeOfRoomStrings);
        listWithTypesOfRoom.setSelectedIndex(0);
        listWithTypesOfRoom.setVisible(true);

        JTextField typeOfRoomField = new JTextField();

        listWithTypesOfRoom.addActionListener(e -> {
            String selectedRoomType = (String) listWithTypesOfRoom.getSelectedItem();
            if ("single".equals(selectedRoomType)) {
                typeOfRoomField.setText("single");
                capacityField.setSelectedItem(1);
            } else if ("double".equals(selectedRoomType)) {
                typeOfRoomField.setText("double");
                capacityField.setSelectedItem(2);
            } else if ("standard".equals(selectedRoomType)) {
                typeOfRoomField.setText("standard");
                capacityField.setSelectedItem(3);
            } else if ("twin".equals(selectedRoomType)) {
                typeOfRoomField.setText("twin");
                capacityField.setSelectedItem(4);
            } else {
                capacityField.setSelectedItem(null);
            }
        });

        return listWithTypesOfRoom;
    }

    private JComboBox<Integer> createComboBoxForRoomCapacity() {
        Integer[] roomCapacity = {1, 2, 3, 4};
        JComboBox<Integer> listWithRoomCapacity = new JComboBox<>(roomCapacity);
        listWithRoomCapacity.setSelectedIndex(0);
        listWithRoomCapacity.setEnabled(false);
        return listWithRoomCapacity;
    }

    public void searchRoom() {
        while (true) {
            JPanel inputPanel = new JPanel(new GridLayout(3, 2));

            JTextField roomNumberField = new JTextField(10);
            inputPanel.add(new JLabel("Room Number:"));
            inputPanel.add(roomNumberField);

            JComboBox<HotelDto> jcd = new JComboBox<>();
            controller.getHotelNames().forEach(jcd::addItem);

            inputPanel.add(new JLabel("Select Hotel:"));
            inputPanel.add(jcd);

            int option = JOptionPane.showConfirmDialog(this, inputPanel, "Search Room", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String roomNumberInput = roomNumberField.getText().trim();
                HotelDto selectedHotel = (HotelDto) jcd.getSelectedItem();

                if (!roomNumberInput.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Invalid room number! Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else if (!roomNumberInput.isEmpty() && selectedHotel != null) {
                    boolean searchResult = controller.searchRoomsButton(roomNumberInput, selectedHotel.getName());
                    if (!searchResult) {
                        JOptionPane.showMessageDialog(this, "No rooms found for the given criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        controller.searchRoomsButton(roomNumberInput, selectedHotel.getName());
                    }
                    break;
                } else {
                    int retryOption = JOptionPane.showConfirmDialog(this, "Please fill in all the required fields!\nDo you want to retry?", "Incomplete Fields", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (retryOption == JOptionPane.NO_OPTION) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    public void createRoomButton(List<HotelDto> hotelNames) {
        while (true) {
            JTextField numberOfRoomField = new JTextField();
            JComboBox<Integer> capacityField = createComboBoxForRoomCapacity();
            JComboBox<String> typeField = createComboBoxForTypeOfRoom(capacityField);

            JTextField priceField = new JTextField();

            JComboBox jcd = new JComboBox();
            hotelNames.forEach(jcd::addItem);

            Object[] dialogFields = {
                    "Room Number:", numberOfRoomField,
                    "Type:", typeField,
                    "Capacity:", capacityField,
                    "Price:", priceField,
                    "Hotel Name:", jcd};

            int option = JOptionPane.showConfirmDialog(null, dialogFields, "Create Room", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    int roomId = controller.generateRandomRoomId();
                    String numberOfRoom = numberOfRoomField.getText().trim();
                    Object type = typeField.getSelectedItem();
                    String roomTypeString = (String) type;
                    Object capacity = capacityField.getSelectedItem();
                    int capacityInt = (int) capacity;
                    int price = Integer.parseInt(priceField.getText());
                    HotelDto item = (HotelDto) jcd.getSelectedItem();
                    assert item != null;
                    int hotelId = item.getId();

                    if (numberOfRoom.isEmpty() || Objects.requireNonNull(roomTypeString).isEmpty() || String.valueOf(capacityInt).isEmpty() || String.valueOf(price).isEmpty() || String.valueOf(hotelId).isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all the required fields!", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
                    } else if (!numberOfRoom.matches("\\d+")) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number for Room Number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String status = "disponibila";
                        boolean isVisible = true;
                        Room newRoom = new Room(roomId, numberOfRoom, roomTypeString, capacityInt, price, status, hotelId, isVisible);
                        controller.addNewRoom(newRoom);

                        JOptionPane.showMessageDialog(this, "Room added successfully!", "Adding room", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numeric values for Room Number and Price.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                break;
            }
        }
    }



    public void showErrorMessageId() {
        JOptionPane.showMessageDialog(this, "Unable to generate a unique Room ID after multiple attempts. Please try again later!", "Error Room ID", JOptionPane.ERROR_MESSAGE);
    }

    public void disableRoom() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to disable the room?", "Confirm ", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                int roomId = (int) table.getValueAt(row, 6);
                controller.disableRoom(roomId);
                JOptionPane.showMessageDialog(this, "Room disabled successfully!", "Disable Room", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row in order to disable a room.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


    public void showRooms(List<RoomDto> roomList) {
        tableModel.setRowCount(0);
        for (RoomDto roomDto : roomList) {
            tableModel.addRow(new Object[]{roomDto.getNumberOfRoom(), roomDto.getType(), roomDto.getCapacity(), roomDto.getPrice(), roomDto.getStatus(), roomDto.getHotelName(), roomDto.getId(), roomDto.isVisible()});
        }
    }
}
