package org.egibide.dao;

import org.egibide.models.Doctor;
import org.egibide.utils.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDaoImpl implements DoctorDao {

    @Override
    public int add(Doctor doctor) {

        String query = "INSERT INTO doctors (name, lastname, dni, salary, speciality) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .getConnection()
                    .prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, doctor.getName());
            ps.setString(2, doctor.getLastname());
            ps.setString(3, doctor.getDni());
            ps.setDouble(4, doctor.getSalary());
            ps.setString(5, doctor.getSpeciality());

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

        String query = "DELETE FROM doctors WHERE id=?";

        PreparedStatement ps;

        try {
            ps = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Doctor getDoctor(int id) {

        String query = "select * from doctors where id=?";

        PreparedStatement ps;
        Doctor doctor = null;

        try {
            ps = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                doctor = new Doctor(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getDouble("salary"),
                        rs.getString("speciality"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctor;
    }

    @Override
    public List<Doctor> getDoctors() {

        String query = "SELECT * FROM doctors";

        PreparedStatement ps;
        Doctor doctor;
        ArrayList<Doctor> listaDoctores = new ArrayList<>();

        try {
            ps = DatabaseConnection.getInstance()
                    .getConnection()
                    .prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("dni"),
                        rs.getDouble("salary"),
                        rs.getString("speciality")
                );

                listaDoctores.add(doctor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaDoctores;
    }

    @Override
    public boolean update(Doctor doctor) {

        if (doctorExists(doctor.getId())) {

            String query = "update doctors set name=?, lastname=?, dni=?, salary=?, speciality=? where id=?";

            PreparedStatement ps;

            int rs = 0;

            try {
                ps = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
                ps.setString(1, doctor.getName());
                ps.setString(2, doctor.getLastname());
                ps.setString(3, doctor.getDni());
                ps.setDouble(4, doctor.getSalary());
                ps.setString(5, doctor.getSpeciality());
                ps.setInt(6, doctor.getId());

                rs = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return (rs > 0);
        }
        return false;
    }

    private boolean doctorExists(int id) {

        String query = "SELECT * FROM doctors WHERE id=?";

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