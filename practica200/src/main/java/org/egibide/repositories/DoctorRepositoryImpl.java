package org.egibide.repositories;

import org.egibide.dao.DoctorDao;
import org.egibide.dao.DoctorDaoImpl;
import org.egibide.dao.PatientDao;
import org.egibide.dao.PatientDaoImpl;
import org.egibide.irepositories.DoctorRepository;
import org.egibide.models.Doctor;

public class DoctorRepositoryImpl implements DoctorRepository {

    private DoctorDao doctorDao = new DoctorDaoImpl();
    private PatientDao patientDao = new PatientDaoImpl();

    @Override
    public Doctor getDoctor(int doctor_id) {
        Doctor doctor = doctorDao.getDoctor(doctor_id);

        if (doctor != null) {
            doctor.setAttendedPatients(
                    patientDao.getPatientsByDoctorId(doctor_id)
            );
        }

        return doctor;
    }
}