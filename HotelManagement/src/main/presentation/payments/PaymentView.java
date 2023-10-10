package ro.fortech.academy.presentation.payments;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import ro.fortech.academy.business.dto.PaymentDto;
import ro.fortech.academy.business.entities.Payment;
import ro.fortech.academy.presentation.aggregator.AggregatorApp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;


public class PaymentView extends JFrame {
    private DefaultTableModel tableModel;
    private PaymentController controller;
    private JTable table;

    public PaymentView(List<Payment> paymentList) {
        super("Payment Window");

        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton showPaymentsButton = createStyledButtons("Show Payments", new ImageIcon("src/resources/show.png"), 24, 24);
        JButton searchPaymentButton = createStyledButtons("Search for Payments ", new ImageIcon("src/resources/search.png"), 24, 24);
        JButton insertPaymentButton = createStyledButtons("Insert New Payments ", new ImageIcon("src/resources/insert.png"), 24, 24);
        JButton updatePaymentButton = createStyledButtons("Update Payments", new ImageIcon("src/resources/update1.png"), 24, 24);
        JButton deletePaymentButton = createStyledButtons("Disable Payments", new ImageIcon("src/resources/disable.png"), 24, 24);
        JButton buttonBack = createBackButton("Back", new ImageIcon("src/resources/backarrow.png"), 24, 24);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 0;
        constraints.weighty = 1.0;
        content.add(showPaymentsButton, constraints);

        constraints.gridy = 1;
        content.add(searchPaymentButton, constraints);

        constraints.gridy = 2;
        content.add(insertPaymentButton, constraints);

        constraints.gridy = 3;
        content.add(updatePaymentButton, constraints);

        constraints.gridy = 4;
        content.add(deletePaymentButton, constraints);

        constraints.gridy = 5;
        content.add(buttonBack, constraints);

        JScrollPane pane = getPaymentTable();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 7;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        content.add(pane, constraints);

        showPaymentsButton.addActionListener(e -> controller.showPaymentButton());
        searchPaymentButton.addActionListener(e -> searchPaymentButton());
        insertPaymentButton.addActionListener(e -> createInsertionFieldForCreateNewPayment(controller.getCustomerIds()));
        updatePaymentButton.addActionListener(e -> updatePaymentAmount());
        deletePaymentButton.addActionListener(e -> disablePayment());
        buttonBack.addActionListener(e -> handleClickBackButton());

        ImageIcon paymentIcon = new ImageIcon("src/resources/payment.png");
        setIconImage(paymentIcon.getImage());
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

    public JScrollPane getPaymentTable() {
        String[] header = {"Date of Payment", "Amount", "Type of Payment", "First Name", "Last Name", "ID"};
        tableModel = new DefaultTableModel(header, 0);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 400));
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setMinWidth(0);
        table.getColumnModel().getColumn(5).setPreferredWidth(0);
        return new JScrollPane(table);
    }

    private void handleClickBackButton() {
        AggregatorApp aggregatorApp = new AggregatorApp();
        aggregatorApp.setVisible(true);
        this.dispose();
    }

    public void showMessageForGenerateRoomId() {
        JOptionPane.showMessageDialog(this, "Unable to generate a unique Room ID after multiple attempts. Please try again later!", "Error Room ID", JOptionPane.ERROR_MESSAGE);
    }

    private JComboBox<String> createComboBoxForTypeOfPayment() {
        String[] typeOfPaymentStrings = {" ", "cash", "card"};

        JComboBox<String> typeOfPaymentList = new JComboBox<>(typeOfPaymentStrings);
        typeOfPaymentList.setSelectedIndex(0);
        typeOfPaymentList.setVisible(true);

        JTextField typeOfPaymentField = new JTextField();
        typeOfPaymentList.addActionListener(e -> {
            String selectedPaymentType = (String) typeOfPaymentList.getSelectedItem();
            if ("cash".equals(selectedPaymentType)) {
                typeOfPaymentField.setText("cash");
            } else if ("card".equals(selectedPaymentType)) {
                typeOfPaymentField.setText("card");
            }
        });

        return typeOfPaymentList;
    }

    private void createInsertionFieldForCreateNewPayment(List<Payment> customerIds) {
        JDatePickerImpl dateOfPaymentField = createJDatePicker();
        JTextField amountField = new JTextField();
        JComboBox<String> typeOfPaymentList = createComboBoxForTypeOfPayment();
        JComboBox customerIdField = new JComboBox();
        customerIds.forEach(customerIdField::addItem);

        Object[] fields = {
                "Date of payment: ", dateOfPaymentField,
                "Type of payment: ", typeOfPaymentList,
                "Amount: ", amountField,
                "Customer ID: ", customerIdField
        };

        boolean validData = false;

        do {
            int option = JOptionPane.showConfirmDialog(null, fields, "Add new payment", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {

                JTextField[] inputFields = {amountField};

                if (isEachFieldValid(inputFields, typeOfPaymentList)) {
                    int paymentId = controller.generateRandomPaymentId();
                    Date selectedDate = (Date) dateOfPaymentField.getModel().getValue();
                    LocalDate dateOfPayment = convertDateToLocalDate(selectedDate);
                    double amount = Double.parseDouble(amountField.getText());
                    Object typeOfPayment = typeOfPaymentList.getSelectedItem();
                    String typeOfPaymentString = typeOfPayment.toString();
                    Payment item = (Payment) customerIdField.getSelectedItem();
                    assert item != null;
                    int customerId = item.getCustomerId();
                    boolean isVisible = true;

                    Payment newPayment = new Payment(paymentId, dateOfPayment, amount, typeOfPaymentString, customerId, isVisible);
                    controller.OKButtonAddNewPayment(newPayment);

                    JOptionPane.showMessageDialog(this, "Payment successfully added!", "Adding payment", JOptionPane.INFORMATION_MESSAGE);

                    validData = true;
                }
            } else {
                break;
            }
        } while (!validData);
    }

    private boolean isEachFieldValid(JTextField[] inputFields, JComboBox<String> typeOfPaymentComboBox) {

        for (JTextField field : inputFields) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all the required fields!", "Incomplete Fields", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        Object typeOfPayment = typeOfPaymentComboBox.getSelectedItem();
        if (typeOfPayment == " ") {
            JOptionPane.showMessageDialog(this, "Please choose a payment type!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Double.parseDouble(inputFields[0].getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter valid numeric values for amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        double amount = Double.parseDouble(inputFields[0].getText());
        if (amount <= 0.0) {
            JOptionPane.showMessageDialog(this, "Invalid amount! Amount should be a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }



    public void showPaymentsWithCustomerData(List<PaymentDto> paymentDtoList) {
        tableModel.setRowCount(0);
        for (PaymentDto payment : paymentDtoList) {
            tableModel.addRow(new Object[]{payment.getDateOfPayment(), payment.getAmount(), payment.getTypeOfPayment(), payment.getFirstName(), payment.getLastName(), payment.getPaymentId()});
        }
    }

    public JDatePickerImpl createJDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    public LocalDate convertDateToLocalDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        return LocalDate.parse(dateString);
    }

    public void searchPaymentButton() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        JTextField lastNameField = new JTextField(10);
        inputPanel.add(new JLabel("Last name:"));
        inputPanel.add(lastNameField);

        JDatePickerImpl datePicker = createJDatePicker();
        inputPanel.add(new JLabel("Date: "));
        inputPanel.add(datePicker);

        boolean isPaymentDataValid = false;

        do {
            try {
                int option = JOptionPane.showConfirmDialog(this, inputPanel, "Search Payment", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    String lastNameInput = lastNameField.getText().trim();
                    Date selectedDate = (Date) datePicker.getModel().getValue();
                    LocalDate dateOfPaymentInput = convertDateToLocalDate(selectedDate);

                    if (isValidData(lastNameInput, selectedDate)) {
                        controller.searchPayment(dateOfPaymentInput, lastNameInput);
                        isPaymentDataValid = true;
                    } else {
                        JOptionPane.showMessageDialog(this, "Please fill in all the required fields correctly!", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    break;
                }
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(this, "Please fill in all the required fields!", "Incomplete Fields", JOptionPane.WARNING_MESSAGE);
            }
        } while (!isPaymentDataValid);
    }

    private boolean isValidLastName(String lastNameInput) {
        return Pattern.matches("^[a-zA-Z]+$", lastNameInput);
    }

    private boolean isDateFieldFilled(Date selectedDate) {
        return selectedDate != null;
    }

    private boolean isValidData(String lastNameInput, Date selectedDate) {
        return isValidLastName(lastNameInput) && isDateFieldFilled(selectedDate);
    }

    private void updatePaymentAmount() {
        int row = table.getSelectedRow();

        if (row >= 0) {
            String currentAmount = table.getValueAt(row, 1).toString();
            String newAmountInput;
            boolean isPaymentDataValid = false;

            do {
                newAmountInput = JOptionPane.showInputDialog(this, "Enter the new amount:", currentAmount);

                if (newAmountInput != null) {
                    if (isAmountValid(newAmountInput, currentAmount)) {
                        int paymentId = (int) table.getValueAt(row, 5);
                        double newAmount = Double.parseDouble(newAmountInput);
                        controller.updatePaymentAmount(paymentId, newAmount);
                        JOptionPane.showMessageDialog(this, "Payment amount updated successfully!", "Update Amount", JOptionPane.INFORMATION_MESSAGE);
                        isPaymentDataValid = true;
                    }
                } else {
                    return;
                }
            } while (!isPaymentDataValid);
        } else {
            JOptionPane.showMessageDialog(this, "Please go to the payment table, and select a row to update!", "Unprocessable command ", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean isAmountValid(String amountInput, String currentAmount) {
        if (amountInput.equals(currentAmount)) {
            JOptionPane.showMessageDialog(this, "Please enter the new amount!", "Unprocessable command ", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Double.parseDouble(amountInput);
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid numeric value for the amount.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void showMessageForUpdateMethod() {
        JOptionPane.showMessageDialog(this, "This payment is not exist!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }

    private void disablePayment() {
        int row = table.getSelectedRow();

        if (row >= 0) {
            int paymentId = (int) table.getValueAt(row, 5);
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to disable this payment?", "Delete payment", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                controller.disablePayment(paymentId);
                JOptionPane.showMessageDialog(this, "Payment disabled successfully!", "Disabling payment", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please go to the payment table, and select a row to disable!", "Unprocessable command ", JOptionPane.WARNING_MESSAGE);
        }
    }

    public PaymentController getController() {
        return controller;
    }

    public void setController(PaymentController controller) {
        this.controller = controller;
    }
}
