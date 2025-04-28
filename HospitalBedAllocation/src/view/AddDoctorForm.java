package view;

import controller.DoctorDAO;
import model.Doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddDoctorForm extends JFrame {
    private JTextField nameField, phoneField, departmentField;
    private DoctorDAO doctorDAO;

    public AddDoctorForm() {
        setTitle("Add New Doctor");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        doctorDAO = new DoctorDAO();

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Department:"));
        departmentField = new JTextField();
        add(departmentField);

        JButton submitBtn = new JButton("Add Doctor");
        submitBtn.addActionListener(this::handleSubmit);
        add(submitBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            new ReceptionDashboard();
        });
        add(backBtn);

        setVisible(true);
    }

    private void handleSubmit(ActionEvent e) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String department = departmentField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || department.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        Doctor doctor = new Doctor(name, phone, department);
        boolean success = doctorDAO.addDoctor(doctor);

        if (success) {
            JOptionPane.showMessageDialog(this, "Doctor added successfully.");
            dispose();
            new ReceptionDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add doctor.");
        }
    }
}
