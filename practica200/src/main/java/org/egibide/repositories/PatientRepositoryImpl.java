package org.egibide.repositories;

import org.egibide.dao.DoctorDao;
import org.egibide.dao.DoctorDaoImpl;
import org.egibide.dao.PatientDao;
import org.egibide.dao.PatientDaoImpl;
import org.egibide.irepositories.PatientRepository;
import org.egibide.models.Doctor;
import org.egibide.models.Patient;

public class PatientRepositoryImpl implements PatientRepository {

    private PatientDao patientDao = new PatientDaoImpl();
    private DoctorDao doctorDao = new DoctorDaoImpl();

    @Override
    public Patient getPatient(int id) {
        Patient patient = patientDao.getPatient(id);
        Doctor doctor = doctorDao.getDoctorByPatientId(patient.getId());
        patient.setDoctor(doctor);
        return patient;
    }

    @Override
    public boolean isPatientAttendedByDoctor(int patient_id, int doctor_id) {
        Patient patient = getPatient(patient_id);

        if (patient != null && patient.getDoctor() != null) {
            return patient.getDoctor().getId() == doctor_id;
        }

        return false;
    }
}