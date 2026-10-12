package org.egibide.dao;

import org.egibide.models.Patient;

import java.util.List;

public interface PatientDao {

    int add(Patient patient);

    void delete(int id);

    Patient getPatient(int id);

    List<Patient> getPatients();

    boolean update(Patient patient);
}