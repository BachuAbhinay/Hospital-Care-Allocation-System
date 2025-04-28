package view;

import controller.AppointmentDAO;
import controller.DoctorDAO;
import controller.PatientDAO;
import model.Appointment;
import model.Doctor;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AppointmentForm extends JFrame {
    private JComboBox<Patient> patientBox;
    private JComboBox<Doctor> doctorBox;
    private JTextField dateField;

    public AppointmentForm() {
        setTitle("Create Appointment");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Select Patient:"));
        patientBox = new JComboBox<>();
        loadPatients();
        add(patientBox);

        add(new JLabel("Select Doctor:"));
        doctorBox = new JComboBox<>();
        loadDoctors();
        add(doctorBox);

        add(new JLabel("Appointment Date (YYYY-MM-DD):"));
        dateField = new JTextField(LocalDate.now().toString());
        add(dateField);

        JButton createBtn = new JButton("Create Appointment");
        createBtn.addActionListener(e -> createAppointment());
        add(createBtn);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            new ReceptionDashboard();
        });
        add(backBtn);

        setVisible(true);
    }

    private void loadPatients() {
        List<Patient> patients = PatientDAO.getAllPatients();
        for (Patient p : patients) {
            patientBox.addItem(p);
        }
    }

    private void loadDoctors() {
        List<Doctor> doctors = DoctorDAO.getAllDoctors();
        for (Doctor d : doctors) {
            doctorBox.addItem(d);
        }
    }

    private void createAppointment() {
        Patient selectedPatient = (Patient) patientBox.getSelectedItem();
        Doctor selectedDoctor = (Doctor) doctorBox.getSelectedItem();
        LocalDate date = LocalDate.now();

        if (selectedPatient == null || selectedDoctor == null || date == null){
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        Appointment appointment = new Appointment(
            selectedPatient.getPatientId(),
            selectedDoctor.getDoctorId(),
            date
        );

        boolean success = AppointmentDAO.createAppointment(appointment);
        if (success) {
            JOptionPane.showMessageDialog(this, "Appointment created.");
            dispose();
            new ReceptionDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create appointment.");
        }
    }
}
