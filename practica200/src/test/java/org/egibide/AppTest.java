package org.egibide;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.egibide.dao.DoctorDao;
import org.egibide.dao.DoctorDaoImpl;
import org.egibide.models.Doctor;
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
}
