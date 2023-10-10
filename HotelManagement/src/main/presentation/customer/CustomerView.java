package ro.fortech.academy.presentation.customer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ro.fortech.academy.business.dto.ReservationDto;
import ro.fortech.academy.business.entities.Customer;
import ro.fortech.academy.presentation.aggregator.AggregatorApp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerView extends JFrame {
    private DefaultTableModel tableModel;
    private CustomerController controller;
    private JTable table;

    public CustomerView(List<Customer> customerList) {
        super("Customer Window");

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton printCustomerData = createStyledButtons("Get Customer", new ImageIcon("src/resources/show.png"), 24, 24);
        JButton searchCustomerByName = createStyledButtons("Search Customer by name", new ImageIcon("src/resources/search.png"), 24, 24);
        JButton searchCustomerById = createStyledButtons("Search Customer by Id", new ImageIcon("src/resources/search.png"), 24, 24);
        JButton addNewCustomer = createStyledButtons("Insert New Customer ", new ImageIcon("src/resources/insert.png"), 24, 24);
        JButton updateCustomerItem = createStyledButtons("Update Customer", new ImageIcon("src/resources/update1.png"), 24, 24);
        JButton deleteCustomerItem = createStyledButtons("Disable Customer", new ImageIcon("src/resources/disable.png"), 24, 24);
        JButton buttonBack = buttonBack("Back", new ImageIcon("src/resources/backarrow.png"), 24, 24);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.weighty = 1.0;
        content.add(printCustomerData, constraints);

        constraints.gridy = 1;
        content.add(searchCustomerByName, constraints);

        constraints.gridy = 2;
        content.add(searchCustomerById, constraints);

        constraints.gridy = 3;
        content.add(addNewCustomer, constraints);

        constraints.gridy = 4;
        content.add(updateCustomerItem, constraints);

        constraints.gridy = 5;
        content.add(deleteCustomerItem, constraints);

        constraints.gridy = 6;
        content.add(buttonBack, constraints);

        JScrollPane pane = getCustomerTable();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        content.add(pane, constraints);

        printCustomerData.addActionListener(e -> controller.customerButton());
        searchCustomerByName.addActionListener(e -> searchCustomerByNameButton());
        searchCustomerById.addActionListener(e -> searchCustomerByIdButton());
        addNewCustomer.addActionListener(e -> createCustomerButton(controller.getReservationDto()));
        updateCustomerItem.addActionListener(e -> updateCustomerButton());
        deleteCustomerItem.addActionListener(e -> disableCustomer());
        buttonBack.addActionListener(e -> handleClickBackButton());


        ImageIcon customerIcon = new ImageIcon("src/resources/customers.png");
        setIconImage(customerIcon.getImage());
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

    private JButton buttonBack(String text, ImageIcon icon, int iconWidth, int iconHeight) {
        JButton button = new JButton(text);
        button.setIcon(resizeIcon(icon, iconWidth, iconHeight));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(220, 220, 220));
        button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
        button.setFocusPainted(false);
        return button;
    }

    private void disableCustomer() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to disable this customer?", "Confirm ", JOptionPane.YES_NO_OPTION);
            if (confirmResult == JOptionPane.YES_OPTION) {
                int customerId = (int) table.getValueAt(row, 0);
                controller.disableCustomer(customerId);
                JOptionPane.showMessageDialog(this, "Customer disabled successfully!", "Customer Reservation", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row in order to disable a customer.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void handleClickBackButton() {
        AggregatorApp aggregatorApp = new AggregatorApp();
        aggregatorApp.setVisible(true);
        this.dispose();
    }


    private void searchCustomerByIdButton() {
        while (true) {
        JPanel inputPanel = new JPanel(new GridLayout(3,2));

        JTextField customerIdField = new JTextField(10);
        inputPanel.add(new JLabel("Id number:"));
        inputPanel.add(customerIdField);

        int option = JOptionPane.showConfirmDialog(this, inputPanel , "Search customer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String customerIdInput = customerIdField.getText().trim();
            if (!customerIdInput.matches("^\\d+$")) {
                JOptionPane.showMessageDialog(this, "Invalid Customer ID! Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else if (!customerIdInput.isEmpty()) {
                boolean searchResult = controller.searchCustomerByIdButton(Integer.parseInt(customerIdInput));
                if (!searchResult) {
                    JOptionPane.showMessageDialog(this, "No Customer found for the given criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    controller.searchCustomerByIdButton(Integer.parseInt(customerIdInput));
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

    public void searchCustomerByNameButton() {
        while (true) {
            JPanel inputPanel = new JPanel(new GridLayout(3,2));

            JTextField lastNameField = new JTextField(10);
            inputPanel.add(new JLabel("Last name:"));
            inputPanel.add(lastNameField);

            int option = JOptionPane.showConfirmDialog(this, inputPanel , "Search customer", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String lastNameInput = lastNameField.getText().trim();
                if (!isValidName(lastNameInput)) {
                    JOptionPane.showMessageDialog(this, "Invalid last name! Please enter a valid name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else if (!lastNameInput.isEmpty()) {
                    boolean searchResult = controller.searchCustomerByNameButton(lastNameInput);
                    if (!searchResult) {
                        JOptionPane.showMessageDialog(this, "No Customer found for the given criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        controller.searchCustomerByNameButton(lastNameInput);
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

    public void createCustomerButton(List<ReservationDto> reservationIdList) {
        while (true) {
            JTextField firstNameField = new JTextField();
            JTextField lastNameField = new JTextField();
            JDatePickerImpl dateOfBirthField = createJDatePicker();
            JTextField phoneNumberField = new JTextField();
            JTextField emailField = new JTextField();
            JComboBox listOfReservationId = new JComboBox();
            reservationIdList.forEach(listOfReservationId::addItem);
            Object[] fields = {
                    "First Name: ", firstNameField,
                    "Last Name: ", lastNameField,
                    "Date of birth: ", dateOfBirthField,
                    "Phone number: ", phoneNumberField,
                    "Email: ", emailField,
                    "Reservation ID: ", listOfReservationId,
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Add new customer", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int customerID = controller.generateRandomCustomerId();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                Date selectedDate = (Date) dateOfBirthField.getModel().getValue();

                if (selectedDate == null || !validateFields(firstName, lastName, phoneNumberField.getText(), emailField.getText())) {
                    JOptionPane.showMessageDialog(this, "Please fill in all the required fields with valid values!", "Incomplete/Invalid Fields", JOptionPane.WARNING_MESSAGE);
                    continue;
                }

                LocalDate dateOfBirthInput = convertDateToLocalDate(selectedDate);
                String phoneNumber = phoneNumberField.getText();
                String email = emailField.getText();
                ReservationDto item = (ReservationDto) listOfReservationId.getSelectedItem();
                assert item != null;
                int newReservationID = item.getReservationId();

                boolean isVisible = true;
                Customer newCustomer = new Customer(customerID, firstName, lastName, dateOfBirthInput, phoneNumber, email, newReservationID, isVisible);
                controller.addNewCustomerButton(newCustomer);
                JOptionPane.showMessageDialog(this, "Customer successfully added!", "Adding customer", JOptionPane.INFORMATION_MESSAGE);
                break;
            } else {
                break;
            }
        }
    }


    private boolean isFieldEmpty(String customer) {
        return customer == null || customer.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^.+@.+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[0-9]{9,10}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        String nameRegex = "^[A-Za-z]+$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }


    private boolean validateFields(String firstName, String lastName, String phoneNumber, String email) {
        return !isFieldEmpty(firstName) && isValidName(firstName) && !isFieldEmpty(lastName) && isValidName(lastName) && !isFieldEmpty(phoneNumber) && !isFieldEmpty(email) && isValidEmail(email) && isValidPhoneNumber(phoneNumber);
    }

    public JDatePickerImpl createJDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);

        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    public LocalDate convertDateToLocalDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        LocalDate localDate = LocalDate.parse(dateString);

        return localDate;
    }
    public void updateCustomerButton() {
        int selectedRowIndex = table.getSelectedRow();
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.", "No Row Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int customerId = (int) table.getValueAt(selectedRowIndex, 0);
        int reservationID = (int) table.getValueAt(selectedRowIndex, 6);

        while (true) {
            JTextField firstNameField = new JTextField();
            JTextField lastNameField = new JTextField();
            JDatePickerImpl datePicker = createJDatePicker();
            JTextField phoneNumberField = new JTextField();
            JTextField emailField = new JTextField();

            Object[] fields = {
                    "First Name: ", firstNameField,
                    "Last Name: ", lastNameField,
                    "Date of birth: ", datePicker,
                    "Phone: ", phoneNumberField,
                    "Email: ", emailField,
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Customer updated", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String updatedFirstName = firstNameField.getText();
                String updatedLastName = lastNameField.getText();
                Date selectedDate = (Date) datePicker.getModel().getValue();
                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(this, "Please select a valid date.", "Invalid Date", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                LocalDate dateOfBirthInput = convertDateToLocalDate(selectedDate);
                String updatedPhoneNumber = phoneNumberField.getText();
                String updatedEmail = emailField.getText();
                boolean isVisible = true;

                if (!validateFields(updatedFirstName, updatedLastName, updatedPhoneNumber, updatedEmail)) {
                    JOptionPane.showMessageDialog(this, "Please fill in all the required fields with valid values!", "Incomplete/Invalid Fields", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                Customer updatedCustomer = new Customer(customerId, updatedFirstName, updatedLastName, dateOfBirthInput, updatedPhoneNumber, updatedEmail, reservationID, isVisible);
                controller.updateCustomerButton(updatedCustomer);

                JOptionPane.showMessageDialog(this, "Customer updated successfully!", "Update Successful", JOptionPane.INFORMATION_MESSAGE);

                break;
            } else {
                break;
            }
        }
    }


    public JScrollPane getCustomerTable() {
        String[] header = {"ID", "First Name", "Last Name", "Date of birth", "Phone", "Email", "Reservation"};
        tableModel = new DefaultTableModel(header, 0);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 400));
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setPreferredWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setPreferredWidth(0);
        return new JScrollPane(table);
    }

    public CustomerController getController() {
        return controller;
    }

    public void setController(CustomerController controller) {
        this.controller = controller;
    }

    public void showCustomers(List<Customer> customerList) {
        tableModel.setRowCount(0);
        for (Customer customer : customerList) {
            tableModel.addRow(new Object[]{customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getDateOfBirth(), customer.getPhoneNumber(), customer.getEmail(), customer.getReservationId()});
        }
    }

    public void showErrorMessageId() {
        JOptionPane.showMessageDialog(this, "Unable to generate a unique Customer ID after multiple attempts. Please try again later!", "Error Customer ID", JOptionPane.ERROR_MESSAGE);
    }

}