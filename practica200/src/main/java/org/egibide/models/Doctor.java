package org.egibide.models;

import java.util.List;

public class Doctor {

    private int id;
    private String name;
    private String lastname;
    private String dni;
    private double salary;
    private String speciality;
    private List<Patient> attendedPatients = null;

    // Constructor vacío
    public Doctor() {
    }

    // Constructor con todos los atributos (menos la lista de pacientes)
    public Doctor(int id, String name, String lastname, String dni, double salary, String speciality) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.salary = salary;
        this.speciality = speciality;
    }

    // Constructor con todos los atributos
    public Doctor(int id, String name, String lastname, String dni, double salary, String speciality, List<Patient> attendedPatients) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.dni = dni;
        this.salary = salary;
        this.speciality = speciality;
        this.attendedPatients = attendedPatients;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public List<Patient> getAttendedPatients() {
        return attendedPatients;
    }

    public void setAttendedPatients(List<Patient> attendedPatients) {
        this.attendedPatients = attendedPatients;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dni='" + dni + '\'' +
                ", salary=" + salary +
                ", speciality='" + speciality + '\'' +
                ", attendedPatients=" + attendedPatients +
                '}';
    }
}
