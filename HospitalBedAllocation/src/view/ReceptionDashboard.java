package view;

import javax.swing.*;
import java.awt.*;

public class ReceptionDashboard extends JFrame {

    public ReceptionDashboard() {
        setTitle("Receptionist Dashboard");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10)); // Adjusted to accommodate the new button

        JButton patientEntryBtn = new JButton("Add New Patient");
        patientEntryBtn.addActionListener(e -> {
            dispose();
            new AddPatientForm(); // Reusing for patient entry
        });

        JButton reportBtn = new JButton("Create Report");
        reportBtn.addActionListener(e -> {
            dispose();
            new ReportForm();
        });

        JButton viewBtn = new JButton("View Records");
        viewBtn.addActionListener(e -> {
            dispose();
            new ViewRecordsForm();  // New form to show data
        });

        JButton addDoctorBtn = new JButton("Add Doctor");
        addDoctorBtn.addActionListener(e -> {
            dispose();
            new AddDoctorForm(); // Open the AddDoctorForm page
        });

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginView();
        });

        add(patientEntryBtn);
        add(reportBtn);
        add(viewBtn);
        add(addDoctorBtn); // Add the Add Doctor button
        add(logoutBtn);

        setVisible(true);
    }
}
