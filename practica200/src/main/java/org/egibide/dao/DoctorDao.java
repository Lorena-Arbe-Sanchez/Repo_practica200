package org.egibide.dao;

import org.egibide.models.Doctor;

import java.util.List;

public interface DoctorDao {

    int add(Doctor doctor);

    void delete(int id);

    Doctor getDoctor(int id);

    List<Doctor> getDoctors();

    boolean update(Doctor doctor);

}
