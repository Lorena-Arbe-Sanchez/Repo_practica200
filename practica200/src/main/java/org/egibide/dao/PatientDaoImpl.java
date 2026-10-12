package org.egibide.dao;

import org.egibide.models.Patient;
import org.egibide.utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {

    @Override
    public int add(Patient patient) {

        String query = "INSERT INTO patients (name, lastname, dni, age, phone, disease, doctor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .getConnection()
                    .prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, patient.getName());
            ps.setString(2, patient.getLastname());
            ps.setString(3, patient.getDni());
            ps.setInt(4, patient.getAge());
            ps.setString(5, patient.getPhone());
            ps.setString(6, patient.getDisease());

            if (patient.getDoctor() != null) {
                ps.setInt(7, patient.getDoctor().getId());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void delete(int id) {

        String query = "DELETE FROM patients WHERE id=?";

        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .getConnection()
                    .prepareStatement(query);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Patient getPatient(int id) {

        String query = "SELECT * FROM patients WHERE id=?";

        Patient patient = null;

        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .getConnection()
                    .prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getInt("age"),
                        rs.getString("phone"),
                        rs.getString("disease")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patient;
    }

    @Override
    public List<Patient> getPatients() {

        String query = "SELECT * FROM patients";

        ArrayList<Patient> listaPacientes = new ArrayList<>();

        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .getConnection()
                    .prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Patient patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getInt("age"),
                        rs.getString("phone"),
                        rs.getString("disease")
                );

                listaPacientes.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPacientes;
    }

    @Override
    public boolean update(Patient patient) {

        if (patientExists(patient.getId())) {

            String query = "UPDATE patients SET name=?, lastname=?, dni=?, age=?, " +
                    "phone=?, disease=?, doctor_id=? WHERE id=?";

            int rs = 0;

            try {
                PreparedStatement ps = DatabaseConnection.getInstance()
                        .getConnection()
                        .prepareStatement(query);

                ps.setString(1, patient.getName());
                ps.setString(2, patient.getLastname());
                ps.setString(3, patient.getDni());
                ps.setInt(4, patient.getAge());
                ps.setString(5, patient.getPhone());
                ps.setString(6, patient.getDisease());

                if (patient.getDoctor() != null) {
                    ps.setInt(7, patient.getDoctor().getId());
                } else {
                    ps.setNull(7, Types.INTEGER);
                }

                ps.setInt(8, patient.getId());

                rs = ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return rs > 0;
        }

        return false;
    }

    private boolean patientExists(int id) {

        String query = "SELECT * FROM patients WHERE id=?";

        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .getConnection()
                    .prepareStatement(query);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}