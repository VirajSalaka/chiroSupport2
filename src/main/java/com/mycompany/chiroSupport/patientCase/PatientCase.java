package com.mycompany.chiroSupport.patientCase;


import com.mycompany.chiroSupport.patientProfile.Patient;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name="patientcase",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class PatientCase {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @Column(name="caseName", nullable=false)
    private String caseName;

    @Column(name="createdDate", nullable=false)
    private String createdDate;

    @OneToOne
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private ArrayList<Examination> examList;

    public PatientCase(String name, String createdDate){
        this.caseName = name;
        this.createdDate = createdDate;
    }



    public long getCaseId() {
        return id;
    }

    public String getCaseName() {
        return caseName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void addExams(Examination examination){
        getExamList().add(examination);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ArrayList<Examination> getExamList() {
        return examList;
    }

    public void setExamList(ArrayList<Examination> examList) {
        this.examList = examList;
    }
}
