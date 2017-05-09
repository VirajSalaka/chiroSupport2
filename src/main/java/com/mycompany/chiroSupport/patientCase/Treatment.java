package com.mycompany.chiroSupport.patientCase;

import javax.persistence.*;

/**
 * Created by Salaka on 5/9/2017.
 */

@Entity
@Table(name="treatment",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class Treatment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @ManyToOne
    @JoinColumn(name="exam_id", nullable=false)
    private Examination examination;

    @Column(name="name")
    private String treatmentName;

    @Column(name="frequency")
    private String frequency;

    @Column(name="duration")
    private String duration;

    @Column(name="adjustments")
    private String adjustments;

    public Treatment(){}

    public Treatment(Examination examination){
        this.setExamination(examination);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(String adjustments) {
        this.adjustments = adjustments;
    }
}
