package view;

import controller.PatientDAO;
import controller.DoctorDAO;
import controller.AppointmentDAO;
import controller.BedDAO;
import model.Patient;
import model.Doctor;
import model.Appointment;
import model.Bed;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ViewRecordsForm extends JFrame {
    public ViewRecordsForm() {
        setTitle("Hospital Records");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();

        // === Patients ===
        sb.append("=== Patients ===\n");
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAllPatients();
        if (patients.isEmpty()) {
            sb.append("No patient records found.\n");
        } else {
            for (Patient p : patients) {
                sb.append(p).append("\n");
            }
        }

        // === Doctors ===
        sb.append("\n=== Doctors ===\n");
        DoctorDAO doctorDAO = new DoctorDAO();
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        if (doctors.isEmpty()) {
            sb.append("No doctor records found.\n");
        } else {
            for (Doctor d : doctors) {
                sb.append(d).append("\n");
            }
        }

        // === Beds ===
        sb.append("\n=== Beds ===\n");
        BedDAO bedDAO = new BedDAO();
        List<Bed> beds = bedDAO.getAllBeds();
        if (beds.isEmpty()) {
            sb.append("No bed records found.\n");
        } else {
            for (Bed b : beds) {
                sb.append(b).append("\n");
            }
        }

        sb.append("=== Appointments ===\n");
        try{
        AppointmentDAO DAO = new AppointmentDAO();
        List<Appointment> app = DAO.getAllAppointments();
        if (app.isEmpty()) {
            sb.append("No patient records found.\n");
        } else {
            for (Appointment p : app) {
                sb.append(p).append("\n");
            }
        }
        }catch(SQLException e){

        }      

        textArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            new ReceptionDashboard();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
