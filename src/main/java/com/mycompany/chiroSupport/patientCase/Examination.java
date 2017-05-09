package com.mycompany.chiroSupport.patientCase;


import com.mycompany.chiroSupport.patientCase.objective.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="examination",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class Examination {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @ManyToOne
    @JoinColumn(name="case_id", nullable=false)
    private PatientCase patientCase;

    @Column(name="createdDate", nullable=false)
    private String date;

    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL)
    private List<Subjective> subjectiveList;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL)
    private Observation observation;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL)
    private Palpation palpation;

    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL)
    private List<SpecialTest> specialTestList;

    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL)
    private List<Rom> romTestList;

    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL)
    private List<MusclePower> musclePowerList;

    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL)
    private List<DiagnosticStudy> diagnosticStudyList;

    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL)
    private List<NeurologicalStudy> neurologicalStudyList;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL)
    private Analysis analysis;

    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL)
    private List<Treatment> treatmentList;

    public Examination(PatientCase patientCase){
        this.setPatientCase(patientCase);
    }

    public Examination(){}

    public long getId() {
        return id;
    }

    public PatientCase getPatientCase() {
        return patientCase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Subjective> getSubjectiveList() {
        return subjectiveList;
    }

    public void setSubjectiveList(List<Subjective> subjectiveList) {
        this.subjectiveList = subjectiveList;
    }

    public Palpation getPalpation() {
        return palpation;
    }

    public void setPalpation(Palpation palpation) {
        this.palpation = palpation;
    }

    public List<SpecialTest> getSpecialTestList() {
        return specialTestList;
    }

    public void setSpecialTestList(List<SpecialTest> specialTestList) {
        this.specialTestList = specialTestList;
    }

    public List<Rom> getRomTestList() {
        return romTestList;
    }

    public void setRomTestList(List<Rom> romTestList) {
        this.romTestList = romTestList;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    public List<MusclePower> getMusclePowerList() {
        return musclePowerList;
    }

    public void setMusclePowerList(List<MusclePower> musclePowerList) {
        this.musclePowerList = musclePowerList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPatientCase(PatientCase patientCase) {
        this.patientCase = patientCase;
    }

    public List<DiagnosticStudy> getDiagnosticStudyList() {
        return diagnosticStudyList;
    }

    public void setDiagnosticStudyList(List<DiagnosticStudy> diagnosticStudyList) {
        this.diagnosticStudyList = diagnosticStudyList;
    }

    public List<NeurologicalStudy> getNeurologicalStudyList() {
        return neurologicalStudyList;
    }

    public void setNeurologicalStudyList(List<NeurologicalStudy> neurologicalStudyList) {
        this.neurologicalStudyList = neurologicalStudyList;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }
}
