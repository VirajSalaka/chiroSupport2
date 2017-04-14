package com.mycompany.chiroSupport.patientCase;


import com.mycompany.chiroSupport.patientCase.objective.Observation;
import com.mycompany.chiroSupport.patientCase.objective.Palpation;
import com.mycompany.chiroSupport.patientCase.objective.SpecialTest;

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

    @OneToMany(mappedBy = "patientCase", cascade = CascadeType.ALL)
    private List<Subjective> subjectiveList;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL)
    private Observation observation;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL)
    private Palpation palpation;

    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL)
    private List<SpecialTest> specialTestList;

    public Examination(PatientCase patientCase){
        this.patientCase = patientCase;
    }

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
}
