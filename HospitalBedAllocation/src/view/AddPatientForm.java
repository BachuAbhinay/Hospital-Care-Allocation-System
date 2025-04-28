package view;

import controller.AppointmentDAO;
import controller.DoctorDAO;
import controller.PatientDAO;
import model.Appointment;
import model.Doctor;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class AddPatientForm extends JFrame {
    private JTextField nameField, phoneField, ageField;
    private JComboBox<String> genderBox;
    private JComboBox<Doctor> doctorBox;
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;

    public AddPatientForm() {
        setTitle("Add New Patient");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10));  // Adjusted for an additional row for the doctor dropdown

        patientDAO = new PatientDAO();
        doctorDAO = new DoctorDAO();

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Age:"));
        ageField = new JTextField();
        add(ageField);

        add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[] { "M", "F", "O" });
        add(genderBox);

        // Add Dropdown for Doctors
        add(new JLabel("Select Doctor:"));
        doctorBox = new JComboBox<>();
        loadDoctors();  // This loads doctors from the database
        add(doctorBox);

        JButton submitBtn = new JButton("Add Patient");
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

    private void loadDoctors() {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        for (Doctor d : doctors) {
            doctorBox.addItem(d); // Doctor.toString() should return doctor's name
        }
    }

    private void handleSubmit(ActionEvent e) {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String ageText = ageField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        Doctor selectedDoctor = (Doctor) doctorBox.getSelectedItem();

        // Validate input
        if (name.isEmpty() || phone.isEmpty() || ageText.isEmpty() || gender == null || selectedDoctor == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        // Validate age
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid age entered.");
            return;
        }

        // Create a patient object
        Patient patient = new Patient(name, phone, age, gender.charAt(0));
        
        // Add the patient to the database
        boolean success = patientDAO.addPatient(patient);

        if (success) {
            // After successfully adding the patient, get the generated patient ID
            int patientId = patientDAO.getPatientIdByDetails(name, phone);

            if (patientId > 0) {
                // Create an appointment with the selected doctor
                Appointment appointment = new Appointment(patientId, selectedDoctor.getDoctorId(), LocalDate.now());
                boolean appointmentCreated = AppointmentDAO.createAppointment(appointment);

                if (appointmentCreated) {
                    JOptionPane.showMessageDialog(this, "Patient added and appointment created.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create appointment.");
                }
            }
            dispose();
            new ReceptionDashboard(); // Go back to the Reception Dashboard after submission
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add patient.");
        }
    }
}
