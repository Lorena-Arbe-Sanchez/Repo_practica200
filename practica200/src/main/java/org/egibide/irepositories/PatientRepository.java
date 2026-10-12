package org.egibide.irepositories;

import org.egibide.models.Patient;

public interface PatientRepository {

    Patient getPatient(int id);

//    void add(Patient patient);
//
//    void update(Patient patient);
//
//    void remove(Patient patient);

    public boolean isPatientAttendedByDoctor(int patient_id, int doctor_id);
}