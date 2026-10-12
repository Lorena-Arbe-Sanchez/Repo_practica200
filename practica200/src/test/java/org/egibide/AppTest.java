package org.egibide;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.egibide.dao.DoctorDao;
import org.egibide.dao.DoctorDaoImpl;
import org.egibide.irepositories.DoctorRepository;
import org.egibide.irepositories.PatientRepository;
import org.egibide.models.Doctor;
import org.egibide.models.Patient;
import org.egibide.repositories.DoctorRepositoryImpl;
import org.egibide.repositories.PatientRepositoryImpl;
import org.egibide.utils.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }

    // Comprobar la conexion con la base de datos
    public void testConexion() throws Exception {

        Connection conexion = DatabaseConnection
                .getInstance()
                .getConnection();

        assertNotNull(conexion);
        assertFalse(conexion.isClosed());
    }

    // Probar los metodos del DoctorDAO
    public void testDoctorDAO() {

        DoctorDao dao = new DoctorDaoImpl();

        Doctor doctor = new Doctor(
                0,
                "Ane",
                "García",
                "87654321X",
                35000,
                "Cardiología"
        );

        int id = dao.add(doctor);

        // Comprobar que se ha añadido
        assertTrue(id > 0);

        try {
            // Buscar el doctor por su ID
            Doctor doctorEncontrado = dao.getDoctor(id);

            assertNotNull(doctorEncontrado);
            assertEquals("Ane", doctorEncontrado.getName());

            // Comprobar la lista de doctores
            List<Doctor> listaDoctores = dao.getDoctors();

            boolean encontrado = false;

            for (Doctor d : listaDoctores) {
                if (d.getId() == id) {
                    encontrado = true;
                    break;
                }
            }

            assertTrue(encontrado);

            // Modificar el salario
            doctorEncontrado.setSalary(40000);

            assertTrue(dao.update(doctorEncontrado));

            Doctor doctorActualizado = dao.getDoctor(id);

            assertEquals(
                    40000.0,
                    doctorActualizado.getSalary(),
                    0.01
            );

        } finally {
            // Borrar el doctor de prueba
            dao.delete(id);
        }

        // Comprobar que se ha borrado
        assertNull(dao.getDoctor(id));
    }

    public void testGetPatient() {

        PatientRepository repository = new PatientRepositoryImpl();

        Patient patient = repository.getPatient(1);

        assertNotNull(patient);

        System.out.println(patient);
        System.out.println("Médico asignado: " + patient.getDoctor());
    }

    public void testGetDoctor() {

        DoctorRepository repository = new DoctorRepositoryImpl();

        Doctor doctor = repository.getDoctor(1);

        assertNotNull(doctor);

        System.out.println("Médico: " + doctor.getName());

        List<Patient> patients = doctor.getAttendedPatients();

        assertNotNull(patients);

        System.out.println("Pacientes atendidos:");

        for (Patient patient : patients) {
            System.out.println(patient);
        }

        // Este código corresponde a los tres pacientes que existen con "doctor_id = 1" en la base de datos
        assertEquals(3, patients.size());
    }

    public void testIsPatientAttendedByDoctor() {
        PatientRepository repository = new PatientRepositoryImpl();

        assertTrue(repository.isPatientAttendedByDoctor(1, 1));
        assertFalse(repository.isPatientAttendedByDoctor(1, 2));
    }
}

/*
Resultado de los Test:

Patient{id=1, name='Ane', lastname='Garcia Lopez', dni='12345678A', age=25, phone='600123456', disease='Gripe', doctor=Doctor{id=1, name='Marta', lastname='Pérez', dni='12345678Z', salary=35000.0, speciality='Cardiología', attendedPatients=null}}
Médico asignado: Doctor{id=1, name='Marta', lastname='Pérez', dni='12345678Z', salary=35000.0, speciality='Cardiología', attendedPatients=null}

Médico: Marta
Pacientes atendidos:
Patient{id=1, name='Ane', lastname='Garcia Lopez', dni='12345678A', age=25, phone='600123456', disease='Gripe', doctor=null}
Patient{id=2, name='Mikel', lastname='Fernandez Ruiz', dni='23456789B', age=42, phone='611234567', disease='Migraña', doctor=null}
Patient{id=3, name='Nerea', lastname='Martinez Lopez', dni='34567890C', age=30, phone='622345678', disease='Alergia', doctor=null}
 */