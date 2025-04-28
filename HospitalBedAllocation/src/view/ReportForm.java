package view;

import controller.AppointmentDAO;
import controller.BedDAO;
import controller.ReportDAO;
import model.Appointment;
import model.Report;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ReportForm extends JFrame{
    private JComboBox<Appointment> appointmentBox;
    private JTextArea detailsArea, suggestionsArea;
    private JComboBox<String> roomTypeBox;
    private JComboBox<String> admissionBox;

    public ReportForm(){
        setTitle("Create Report");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Select Appointment:"));
        appointmentBox = new JComboBox<>();
        try{
            loadAppointments();
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load appointments: " + e.getMessage());
        }
        add(appointmentBox);

        add(new JLabel("Report Details:"));
        detailsArea = new JTextArea(3, 20);
        add(new JScrollPane(detailsArea));

        add(new JLabel("Suggestions:"));
        suggestionsArea = new JTextArea(3, 20);
        add(new JScrollPane(suggestionsArea));

        add(new JLabel("Room Type:"));
        roomTypeBox = new JComboBox<>(new String[]{"ICU", "General", "Emergency"});
        add(roomTypeBox);

        add(new JLabel("Admission (Y/N):"));
        admissionBox = new JComboBox<>(new String[]{"Y", "N"});
        add(admissionBox);

        JButton submitBtn = new JButton("Submit Report");
        submitBtn.addActionListener(e -> {
            try {
                handleSubmit();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error submitting report: " + ex.getMessage());
            }
        });
        add(submitBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            new ReceptionDashboard();
        });
        add(backBtn);

        setVisible(true);
    }

    private void loadAppointments() throws SQLException{
        List<Appointment> appointments = AppointmentDAO.getAllAppointments();
        for (Appointment appt : appointments) {
            appointmentBox.addItem(appt);
        }
    }

    private void handleSubmit() throws SQLException{
        Appointment appt = (Appointment) appointmentBox.getSelectedItem();
        String details = detailsArea.getText();
        String suggestions = suggestionsArea.getText();
        String roomType = (String) roomTypeBox.getSelectedItem();
        String admission = (String) admissionBox.getSelectedItem();

        if (appt == null || details.isEmpty() || suggestions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        Report report = new Report(
            appt.getPatientId(),
            appt.getDoctorId(),
            details,
            suggestions,
            LocalDate.now(),
            roomType,
            admission
        );

        boolean inserted = ReportDAO.createReport(report);

        if (admission.equals("Y")) {
            boolean bedAllocated = BedDAO.allocateBed(roomType);
            if (!bedAllocated) {
                JOptionPane.showMessageDialog(this, "No beds available for selected room type.");
                return;
            }
        }

        if (inserted) {
            JOptionPane.showMessageDialog(this, "Report created successfully.");
            dispose();
            new ReceptionDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Error creating report.");
        }
    }
}
