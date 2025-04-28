package view;

import controller.UserDAO;
import model.User;
import util.Session;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginView() {
        setTitle("Hospital System - Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 5, 5));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> handleLogin());

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterView();
        });

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginBtn);
        add(registerBtn);

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = UserDAO.login(username, password);

        if (user != null) {
            Session.setCurrentUser(user);
            dispose();

            switch (user.getRole()) {
                case "Receptionist":
                    new ReceptionDashboard();
                    break;
                case "Patient":
                    new PatientDashboard();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown role!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }
}
