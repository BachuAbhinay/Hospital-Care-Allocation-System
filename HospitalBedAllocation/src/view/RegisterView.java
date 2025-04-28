package view;

import controller.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField roleField;

    public RegisterView() {
        setTitle("Register");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 5, 5));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleField = new JTextField();

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> handleRegister());

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Role (Patient/Receptionist):"));
        add(roleField);
        add(registerBtn);

        setVisible(true);
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = roleField.getText();

        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        User newUser = new User(username, password, role);
        boolean success = UserDAO.createUser(newUser);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful.");
            dispose();
            new LoginView();
        } else {
            JOptionPane.showMessageDialog(this, "Error during registration.");
        }
    }
}
