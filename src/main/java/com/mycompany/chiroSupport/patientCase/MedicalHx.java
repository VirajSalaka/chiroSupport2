package com.mycompany.chiroSupport.patientCase;

import com.mycompany.chiroSupport.patientProfile.Patient;

import javax.persistence.*;

/**
 * Created by Salaka on 4/13/2017.
 */

@Entity
@Table(name="vitalsreport",
        uniqueConstraints={@UniqueConstraint(columnNames={"id","patient_id"})})
public class MedicalHx {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @OneToOne
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @Column(name="surgery")
    private String surgeryDetails;

    @Column(name="ayurveda")
    private String ayurvedaDetails;

    @Column(name="exPhysiotherapy")
    private String exPhysiotherapyDetails;

    @Column(name="medication")
    private String medicationDetails;

    @Column(name="familyHx")
    private String familyHxDetails;

    @Column(name="socialHx")
    private String socialHxDetails;

    @Column(name="injury")
    private String injuryDetails;

    @Column(name="other")
    private String otherIncidentDetails;


    public MedicalHx(Patient patient){
        this.patient = patient;
    }

    public int getId() {
        return id;
    }


    public Patient getPatient() {
        return patient;
    }


    public String getSurgeryDetails() {
        return surgeryDetails;
    }

    public void setSurgeryDetails(String surgeryDetails) {
        this.surgeryDetails = surgeryDetails;
    }

    public String getAyurvedaDetails() {
        return ayurvedaDetails;
    }

    public void setAyurvedaDetails(String ayurvedaDetails) {
        this.ayurvedaDetails = ayurvedaDetails;
    }

    public String getExPhysiotherapyDetails() {
        return exPhysiotherapyDetails;
    }

    public void setExPhysiotherapyDetails(String exPhysiotherapyDetails) {
        this.exPhysiotherapyDetails = exPhysiotherapyDetails;
    }

    public String getMedicationDetails() {
        return medicationDetails;
    }

    public void setMedicationDetails(String medicationDetails) {
        this.medicationDetails = medicationDetails;
    }

    public String getFamilyHxDetails() {
        return familyHxDetails;
    }

    public void setFamilyHxDetails(String familyHxDetails) {
        this.familyHxDetails = familyHxDetails;
    }

    public String getSocialHxDetails() {
        return socialHxDetails;
    }

    public void setSocialHxDetails(String socialHxDetails) {
        this.socialHxDetails = socialHxDetails;
    }

    public String getInjuryDetails() {
        return injuryDetails;
    }

    public void setInjuryDetails(String injuryDetails) {
        this.injuryDetails = injuryDetails;
    }

    public String getOtherIncidentDetails() {
        return otherIncidentDetails;
    }

    public void setOtherIncidentDetails(String otherIncidentDetails) {
        this.otherIncidentDetails = otherIncidentDetails;
    }
}
