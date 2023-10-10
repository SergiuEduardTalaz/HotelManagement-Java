package ro.fortech.academy.presentation.hotel;

import ro.fortech.academy.business.entities.Hotel;
import ro.fortech.academy.presentation.aggregator.AggregatorApp;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

public class HotelView extends JFrame {

    private DefaultTableModel tableModel;
    private HotelController controller;
    private JTable table;

    public HotelView(List<Hotel> hotelList) {
        super("Hotel Menu");

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton showAllHotels = createStyledButtons("Show All Hotels ", new ImageIcon("src/resources/show.png"), 24, 24);
        JButton filterHotels = createStyledButtons("Filter by Service ", new ImageIcon("src/resources/search.png"), 24, 24);
        JButton updatePhoneHotels = createStyledButtons("Update Phone ", new ImageIcon("src/resources/update1.png"), 24, 24);
        JButton buttonBack = createBackButton("Back", new ImageIcon("src/resources/backarrow.png"), 24, 24);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.weighty = 1.0;
        content.add(showAllHotels, constraints);

        constraints.gridy = 1;
        content.add(filterHotels, constraints);

        constraints.gridy = 2;
        content.add(updatePhoneHotels, constraints);

        constraints.gridy = 3;
        content.add(buttonBack, constraints);

        JScrollPane pane = getHotelTable();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        content.add(pane, constraints);

        showAllHotels.addActionListener(e -> controller.menuItemGetHotelDataPressed());
        filterHotels.addActionListener(e -> searchForHotelServicesButton());
        updatePhoneHotels.addActionListener(e -> handleUpdatePhoneHotel());
        buttonBack.addActionListener(e -> handleClickBackButton());

        ImageIcon roomIcon = new ImageIcon("src/resources/icon.png");
        setIconImage(roomIcon.getImage());
        this.setContentPane(content);
        this.addWindowListener(getExitConfirmation());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
    }

    private WindowAdapter getExitConfirmation() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(HotelView.this, "Are You Sure You Want to Exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else if (result == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        };
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

    private JScrollPane getHotelTable() {
        String[] header = {"Hotel ID", "Name", "Address", "Phone Number", "Services"};
        tableModel = new DefaultTableModel(header, 0);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 400));
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        JScrollPane pane = new JScrollPane(table);
        return pane;
    }

    private void handleUpdatePhoneHotel() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String currentPhoneNumber = table.getValueAt(row, 3).toString();
            while (true) {
                String newPhoneNumberInput = JOptionPane.showInputDialog(this, "Enter the new phone number:", currentPhoneNumber);
                if (newPhoneNumberInput == null) {
                    break;
                }
                if (newPhoneNumberInput.matches("\\d{10}")) {
                    try {
                        int hotelId = (int) table.getValueAt(row, 0);
                        controller.updateHotelPhone(hotelId, newPhoneNumberInput);
                        JOptionPane.showMessageDialog(this, "Hotel phone number updated successfully!", "Update Phone", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid numeric value for the phone number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid input! Phone number must be exactly 10 digits.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to update the phone.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void searchForHotelServicesButton() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        Set<String> uniqueServices = new HashSet<>();

        for (Hotel hotel : controller.getListOfHotelServices()) {
            uniqueServices.add(hotel.getServices());
        }

        List<String> sortedServices = new ArrayList<>(new TreeSet<>(uniqueServices));

        JComboBox<String> hotelJComboBox = new JComboBox<>();
        sortedServices.forEach(hotelJComboBox::addItem);
        inputPanel.add(new JLabel("Choose One of the Services:"));
        inputPanel.add(hotelJComboBox);

        int option = JOptionPane.showConfirmDialog(this, inputPanel, "Filter by Services", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String selectedService = (String) hotelJComboBox.getSelectedItem();

            if (selectedService != null) {
                controller.searchHotelServicesController(selectedService);
            }
        }
    }





    public HotelController getController() {
        return controller;
    }

    public void setController(HotelController controller) {
        this.controller = controller;
    }

    public void showHotels(List<Hotel> hotelList) {
        tableModel.setRowCount(0);
        for (Hotel hotel : hotelList) {
            tableModel.addRow(new Object[]{hotel.getHotelId(), hotel.getName(), hotel.getAddress(), hotel.getPhoneNumber(), hotel.getServices()});
        }
    }

}
