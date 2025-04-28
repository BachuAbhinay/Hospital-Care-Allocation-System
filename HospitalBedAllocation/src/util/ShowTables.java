package util;

import java.sql.*;

public class ShowTables {

    private static final String URL = "jdbc:oracle:thin:@//Abhinay15:1522/xepdb1";
    private static final String USER = "hospital_admin";
    private static final String PASS = "hospital123";

    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();

            // Create tables
            createUsersTable(stmt);
            createPatientsTable(stmt);
            createDoctorsTable(stmt);
            createAppointmentsTable(stmt);
            createReportsTable(stmt);
            createBedsTable(stmt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create USERS Table
    private static void createUsersTable(Statement stmt) throws SQLException {
        String createTableSQL = "CREATE TABLE USERS (" +
                "USER_ID NUMBER PRIMARY KEY," +
                "USERNAME VARCHAR2(50) NOT NULL," +
                "PASSWORD VARCHAR2(50) NOT NULL," +
                "ROLE VARCHAR2(20) NOT NULL" +
                ")";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Created USERS table");

        // Creating Sequence for auto increment
        String createSequenceSQL = "CREATE SEQUENCE USERS_SEQ START WITH 1 INCREMENT BY 1";
        stmt.executeUpdate(createSequenceSQL);

        // Creating Trigger for auto increment
        String createTriggerSQL = "CREATE OR REPLACE TRIGGER USERS_TRG " +
                "BEFORE INSERT ON USERS FOR EACH ROW " +
                "BEGIN " +
                "  SELECT USERS_SEQ.NEXTVAL INTO :new.USER_ID FROM dual; " +
                "END;";
        stmt.executeUpdate(createTriggerSQL);
    }

    // Create PATIENTS Table
    private static void createPatientsTable(Statement stmt) throws SQLException {
        String createTableSQL = "CREATE TABLE PATIENTS (" +
                "PATIENT_ID NUMBER PRIMARY KEY," +
                "NAME VARCHAR2(20) NOT NULL," +
                "PHONE VARCHAR2(12) NOT NULL," +
                "AGE NUMBER NOT NULL," +
                "GENDER CHAR(1) NOT NULL" +
                ")";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Created PATIENTS table");

        // Creating Sequence for auto increment
        String createSequenceSQL = "CREATE SEQUENCE PATIENTS_SEQ START WITH 1 INCREMENT BY 1";
        stmt.executeUpdate(createSequenceSQL);

        // Creating Trigger for auto increment
        String createTriggerSQL = "CREATE OR REPLACE TRIGGER PATIENTS_TRG " +
                "BEFORE INSERT ON PATIENTS FOR EACH ROW " +
                "BEGIN " +
                "  SELECT PATIENTS_SEQ.NEXTVAL INTO :new.PATIENT_ID FROM dual; " +
                "END;";
        stmt.executeUpdate(createTriggerSQL);
    }

    // Create DOCTORS Table
    private static void createDoctorsTable(Statement stmt) throws SQLException {
        String createTableSQL = "CREATE TABLE DOCTORS (" +
                "DOCTOR_ID NUMBER PRIMARY KEY," +
                "NAME VARCHAR2(20) NOT NULL," +
                "PHONE VARCHAR2(12) NOT NULL," +
                "DEPARTMENT VARCHAR2(20) NOT NULL" +
                ")";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Created DOCTORS table");

        // Creating Sequence for auto increment
        String createSequenceSQL = "CREATE SEQUENCE DOCTORS_SEQ START WITH 1 INCREMENT BY 1";
        stmt.executeUpdate(createSequenceSQL);

        // Creating Trigger for auto increment
        String createTriggerSQL = "CREATE OR REPLACE TRIGGER DOCTORS_TRG " +
                "BEFORE INSERT ON DOCTORS FOR EACH ROW " +
                "BEGIN " +
                "  SELECT DOCTORS_SEQ.NEXTVAL INTO :new.DOCTOR_ID FROM dual; " +
                "END;";
        stmt.executeUpdate(createTriggerSQL);
    }

    // Create APPOINTMENTS Table
    private static void createAppointmentsTable(Statement stmt) throws SQLException {
        String createTableSQL = "CREATE TABLE APPOINTMENTS (" +
                "APPOINTMENT_ID NUMBER PRIMARY KEY," +
                "PATIENT_ID NUMBER NOT NULL," +
                "DOCTOR_ID NUMBER NOT NULL," +
                "APPOINTMENT_DATE DATE NOT NULL," +
                "FOREIGN KEY (PATIENT_ID) REFERENCES PATIENTS(PATIENT_ID)," +
                "FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(DOCTOR_ID)" +
                ")";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Created APPOINTMENTS table");

        // Creating Sequence for auto increment
        String createSequenceSQL = "CREATE SEQUENCE APPOINTMENTS_SEQ START WITH 1 INCREMENT BY 1";
        stmt.executeUpdate(createSequenceSQL);

        // Creating Trigger for auto increment
        String createTriggerSQL = "CREATE OR REPLACE TRIGGER APPOINTMENTS_TRG " +
                "BEFORE INSERT ON APPOINTMENTS FOR EACH ROW " +
                "BEGIN " +
                "  SELECT APPOINTMENTS_SEQ.NEXTVAL INTO :new.APPOINTMENT_ID FROM dual; " +
                "END;";
        stmt.executeUpdate(createTriggerSQL);
    }

    // Create REPORTS Table
    private static void createReportsTable(Statement stmt) throws SQLException {
        String createTableSQL = "CREATE TABLE REPORTS (" +
                "REPORT_ID NUMBER PRIMARY KEY," +
                "PATIENT_ID NUMBER," +
                "DOCTOR_ID NUMBER," +
                "REPORT_DETAILS CLOB NOT NULL," +
                "SUGGESTIONS CLOB NOT NULL," +
                "REP_DATE DATE NOT NULL," +
                "ROOM_TYPE VARCHAR2(10)," +
                "ADMISSION CHAR(1)," +
                "FOREIGN KEY (PATIENT_ID) REFERENCES PATIENTS(PATIENT_ID)," +
                "FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(DOCTOR_ID)" +
                ")";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Created REPORTS table");

        // Creating Sequence for auto increment
        String createSequenceSQL = "CREATE SEQUENCE REPORTS_SEQ START WITH 1 INCREMENT BY 1";
        stmt.executeUpdate(createSequenceSQL);

        // Creating Trigger for auto increment
        String createTriggerSQL = "CREATE OR REPLACE TRIGGER REPORTS_TRG " +
                "BEFORE INSERT ON REPORTS FOR EACH ROW " +
                "BEGIN " +
                "  SELECT REPORTS_SEQ.NEXTVAL INTO :new.REPORT_ID FROM dual; " +
                "END;";
        stmt.executeUpdate(createTriggerSQL);
    }

    // Create BEDS Table
    private static void createBedsTable(Statement stmt) throws SQLException {
        String createTableSQL = "CREATE TABLE BEDS (" +
                "BED_ID NUMBER PRIMARY KEY," +
                "ROOM_NO NUMBER NOT NULL," +
                "ROOM_TYPE VARCHAR2(10) NOT NULL," +
                "STATUS CHAR(2)" +
                ")";
        stmt.executeUpdate(createTableSQL);
        System.out.println("Created BEDS table");

        // Creating Sequence for auto increment
        String createSequenceSQL = "CREATE SEQUENCE BEDS_SEQ START WITH 1 INCREMENT BY 1";
        stmt.executeUpdate(createSequenceSQL);

        // Creating Trigger for auto increment
        String createTriggerSQL = "CREATE OR REPLACE TRIGGER BEDS_TRG " +
                "BEFORE INSERT ON BEDS FOR EACH ROW " +
                "BEGIN " +
                "  SELECT BEDS_SEQ.NEXTVAL INTO :new.BED_ID FROM dual; " +
                "END;";
        stmt.executeUpdate(createTriggerSQL);
    }
}
