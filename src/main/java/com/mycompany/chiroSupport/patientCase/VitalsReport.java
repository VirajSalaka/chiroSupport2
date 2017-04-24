package com.mycompany.chiroSupport.patientCase;

import com.mycompany.chiroSupport.patientProfile.Patient;

import javax.persistence.*;

/**
 * Created by Salaka on 4/11/2017.
 */

@Entity
@Table(name="vitalsreport",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class VitalsReport {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @Column(name="date" , nullable = false)
    private String date;

    @Column(name="height")
    private int height;

    @Column(name = "weight")
    private double weight;

    @Column(name = "pulse")
    private int pulse;

    @Column(name="respiration")
    private int respiration;

    @Column(name= "temperature")
    private double temperature;

    @Column(name = "bloodPressureLB")
    private int bloodPressureLB;

    @Column(name="bloodPressureUB")
    private int bloodPressureUB;

    public VitalsReport(Patient patient, String date){
        this.setPatient(patient);
        this.setDate(date);
    }

    public VitalsReport(){
    }

    public long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }


    public String getDate() {
        return date;
    }



    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getRespiration() {
        return respiration;
    }

    public void setRespiration(int respiration) {
        this.respiration = respiration;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getBloodPressureLB() {
        return bloodPressureLB;
    }

    public void setBloodPressureLB(int bloodPressureLB) {
        this.bloodPressureLB = bloodPressureLB;
    }

    public int getBloodPressureUB() {
        return bloodPressureUB;
    }

    public void setBloodPressureUB(int bloodPressureUB) {
        this.bloodPressureUB = bloodPressureUB;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
