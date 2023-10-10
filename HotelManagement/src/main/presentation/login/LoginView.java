package ro.fortech.academy.presentation.login;

import ro.fortech.academy.presentation.aggregator.AggregatorApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton exitButton;
    private LoginController controller;
    private boolean isErrorMessageDisplayed;

    public LoginView() {
        super("Login");

        ImageIcon mainAppIcon = new ImageIcon("src/resources/icon.png");
        setIconImage(mainAppIcon.getImage());

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = createLoginButton();
        exitButton = createExitButton();

        GridBagConstraints imageConstraints = createConstraints(0, 0, 2, GridBagConstraints.CENTER, 10, 10);

        GridBagConstraints usernameLabelConstraints = createConstraints(0, 1, 1, GridBagConstraints.LINE_END, 10, 5);
        GridBagConstraints usernameFieldConstraints = createConstraints(1, 1, 1, GridBagConstraints.LINE_START, 0, 5);

        GridBagConstraints passwordLabelConstraints = createConstraints(0, 2, 1, GridBagConstraints.LINE_END, 10, 5);
        GridBagConstraints passwordFieldConstraints = createConstraints(1, 2, 1, GridBagConstraints.LINE_START, 0, 0);

        GridBagConstraints buttonsConstraints = createConstraints(0, 3, 2, GridBagConstraints.CENTER, 10, 10);
        buttonsConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonsConstraints.insets = new Insets(10, 5, 10, 5);

        ImageIcon loginImageIcon = new ImageIcon("src/resources/login_image.png");
        Image image = loginImageIcon.getImage();
        Image scaledImage = image.getScaledInstance(image.getWidth(null) / 4, image.getHeight(null) / 4, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(imageLabel, imageConstraints);
        panel.add(usernameLabel, usernameLabelConstraints);
        panel.add(usernameField, usernameFieldConstraints);
        panel.add(passwordLabel, passwordLabelConstraints);
        panel.add(passwordField, passwordFieldConstraints);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        passwordPanel.add(passwordField);

        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        passwordPanel.add(showPasswordCheckbox);

        GridBagConstraints passwordPanelConstraints = createConstraints(1, 2, 1, GridBagConstraints.LINE_START, 0, 5);
        panel.add(passwordPanel, passwordPanelConstraints);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(loginButton);
        buttonsPanel.add(exitButton);
        panel.add(buttonsPanel, buttonsConstraints);

        loginButton.addActionListener(e -> onLogInButtonPressed());
        passwordField.addActionListener(e -> onLogInButtonPressed());
        usernameField.addActionListener(e -> onLogInButtonPressed());
        exitButton.addActionListener(e -> exitApplication());
        showPasswordCheckbox.addActionListener(this::showPasswordToggleButton);

        add(panel);
        pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showPasswordToggleButton(ActionEvent e) {
        JToggleButton toggleButton = (JToggleButton) e.getSource();
        if (toggleButton.isSelected()) {
            passwordField.setEchoChar((char) 0);
        } else {
            passwordField.setEchoChar('•');
        }
    }

    public void setLoginController(LoginController controller) {
        this.controller = controller;
    }

    public void onLogInButtonPressed() {
        hideLoginError();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        controller.attemptLogin(username, password);
    }

    public void showLoginError() {
        if (!isErrorMessageDisplayed) {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Login Error", JOptionPane.ERROR_MESSAGE);
            isErrorMessageDisplayed = true;
        }
    }

    public void hideLoginError() {
        if (isErrorMessageDisplayed) {
            isErrorMessageDisplayed = false;
        }
    }

    public void resetErrorMessage() {
        isErrorMessageDisplayed = false;
    }

    private JButton createLoginButton() {
        JButton button = new JButton("Login");
        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 141, 188));
        button.setBorder(BorderFactory.createLineBorder(new Color(44, 101, 144), 2));
        button.setFocusPainted(false);
        return button;
    }

    private JButton createExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(150, 50));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.RED);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(144, 44, 44), 2));
        exitButton.setFocusPainted(false);
        return exitButton;
    }

    private void exitApplication() {
        int response = JOptionPane.showConfirmDialog(this, "Are You Sure You Want to Exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private GridBagConstraints createConstraints(int gridx, int gridy, int gridwidth, int anchor, int top, int right) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = 1;
        constraints.anchor = anchor;
        constraints.insets = new Insets(top, right, 0, 0);
        return constraints;
    }

    public void showMainApp() {
        JOptionPane.showMessageDialog(this, "Welcome back, admin!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new AggregatorApp();
    }
}

