package com.mycompany.chiroSupport.patientCase;

import javax.persistence.*;

/**
 * Created by Salaka on 4/14/2017.
 */

@Entity
@Table(name="neurologicalstudy",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class Analysis {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @OneToOne
    @JoinColumn(name="exam_id", nullable=false)
    private Examination examination;

    @Column(name="patient_condition", nullable=false)
    private int patientCondition;

    @Column(name="patient_progress", nullable=false)
    private int patientProgress;

    @Column(name="effectiveness", nullable=false)
    private int effectiveness;

    @Column(name="prognosis", nullable=false)
    private int prognosis;

    @Column(name="comments")
    private String comments;

    public Analysis(Examination examination){
        this.examination = examination;
    }

    public long getId() {
        return id;
    }

    public Examination getExamination() {
        return examination;
    }


    public int getPatientCondition() {
        return patientCondition;
    }

    public void setPatientCondition(int patientCondition) {
        this.patientCondition = patientCondition;
    }

    public int getPatientProgress() {
        return patientProgress;
    }

    public void setPatientProgress(int patientProgress) {
        this.patientProgress = patientProgress;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(int effectiveness) {
        this.effectiveness = effectiveness;
    }

    public int getPrognosis() {
        return prognosis;
    }

    public void setPrognosis(int prognosis) {
        this.prognosis = prognosis;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
