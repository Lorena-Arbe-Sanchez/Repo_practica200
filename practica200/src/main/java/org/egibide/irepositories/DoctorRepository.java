package org.egibide.irepositories;

import org.egibide.models.Doctor;

public interface DoctorRepository {

    Doctor getDoctor(int doctor_id);
}