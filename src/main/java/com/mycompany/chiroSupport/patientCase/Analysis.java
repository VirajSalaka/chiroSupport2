package com.mycompany.chiroSupport.patientCase;

import javax.persistence.*;

/**
 * Created by Salaka on 4/14/2017.
 */

@Entity
@Table(name="analysis",
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
    private String patientCondition;

    @Column(name="patient_progress", nullable=false)
    private String patientProgress;

    @Column(name="effectiveness", nullable=false)
    private int effectiveness;   //unable:0    no:1    yes:2

    @Column(name="prognosis", nullable=false)
    private String prognosis;

    @Column(name="comments")
    private String comments;

    public Analysis(Examination examination){
        this.setExamination(examination);
    }
    public Analysis(){}

    public long getId() {
        return id;
    }

    public Examination getExamination() {
        return examination;
    }

    public String getPatientCondition() {
        return patientCondition;
    }

    public void setPatientCondition(String patientCondition) {
        this.patientCondition = patientCondition;
    }

    public String getPatientProgress() {
        return patientProgress;
    }

    public void setPatientProgress(String patientProgress) {
        this.patientProgress = patientProgress;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(int effectiveness) {
        this.effectiveness = effectiveness;
    }

    public String getPrognosis() {
        return prognosis;
    }

    public void setPrognosis(String prognosis) {
        this.prognosis = prognosis;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }
}
