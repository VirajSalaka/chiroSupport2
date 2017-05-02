package com.mycompany.chiroSupport.patientCase;


import com.mycompany.chiroSupport.patientProfile.Patient;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="patientcase",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class PatientCase {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @Column(name="case_name", nullable=false)
    private String caseName;

    @Column(name="createdDate", nullable=false)
    private String createdDate;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<Examination> examList;

    public PatientCase(String name, String createdDate){
        this.setCaseName(name);
        this.setCreatedDate(createdDate);
    }

    public PatientCase(){};

    public long getCaseId() {
        return getId();
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

    public List<Examination> getExamList() {
        return examList;
    }

    public void setExamList(List<Examination> examList) {
        this.examList = examList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
