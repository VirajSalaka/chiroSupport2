package com.mycompany.chiroSupport.patientProfile;

import com.mycompany.chiroSupport.patientCase.MedicalHx;
import com.mycompany.chiroSupport.patientCase.PatientCase;
import com.mycompany.chiroSupport.patientCase.VitalsReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="patient",
        uniqueConstraints={@UniqueConstraint(columnNames={"refNo","nicNo","bhtNo"})})
public class Patient implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="refNo", nullable=false, unique=true, length=11)
    private int refNo;

    @Column(name="name", length=150, nullable=false)
    private String name;

    @Column(name="nicNo", length=9)
    private int nicNo;

    @Column(name="bhtNo", length=8)
    private int bhtNo;

    @Column(name="dob")
    private String dob;

    @Column(name="gender", length=1, nullable=false)
    private int gender;  //male =1 and Female =2

    @Column(name="address", length=255)
    private String address;

    @Column(name="contactNo", length=9)
    private int contactNo;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<VitalsReport> vitalsReports;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private MedicalHx medicalHx;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientCase> patientCaseList;


    public Patient( String name, int nicNo, int bhtNo,String dob, int gender,String address,int contactNo){
        this.name = name;
        this.nicNo = nicNo;
        this.bhtNo = bhtNo;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.contactNo = contactNo;
    }


    //reference number will be
    public void setRefNo(int refNo){
        this.refNo = refNo;
    }


    public int getRefNo() {
        return refNo;
    }

    public String getName() {
        return name;
    }

    public int getNicNo() {
        return nicNo;
    }

    public int getBhtNo() {
        return bhtNo;
    }

    public String getDob() {
        return dob;
    }

    public int getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void addPatientCase(PatientCase p){

    }

    /*
   display available patients to the physiotherapist
    */
    public void addToQueue(){

    }


    public List<VitalsReport> getVitalsReports() {
        return vitalsReports;
    }

    public void setVitalsReports(List<VitalsReport> vitalsReports) {
        this.vitalsReports = vitalsReports;
    }

    public MedicalHx getMedicalHx() {
        return medicalHx;
    }

    public void setMedicalHx(MedicalHx medicalHx) {
        this.medicalHx = medicalHx;
    }

    public List<PatientCase> getPatientCaseList() {
        return patientCaseList;
    }

    public void setPatientCaseList(List<PatientCase> patientCaseList) {
        this.patientCaseList = patientCaseList;
    }
}
