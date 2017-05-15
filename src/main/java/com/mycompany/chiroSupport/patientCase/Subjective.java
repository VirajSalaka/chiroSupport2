package com.mycompany.chiroSupport.patientCase;

import javax.persistence.*;

/**
 * Created by Salaka on 4/14/2017.
 */

@Entity
@Table(name="subjective",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class Subjective {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @OneToOne
    @JoinColumn(name="exam_id", nullable=false)
    private Examination examination;

    @Column(name="complaint")
    private String complaint;

    @Column(name="region")
    private String region;

    @Column(name="location")
    private String location;

    @Column(name="frequency")
    private String frequency; //in scale of 1-7

    @Column(name="severity")
    private String severity; // in scale of 1-5

    @Column(name="pain")
    private boolean pain;

    @Column(name="stiffness")
    private boolean stiffness;

    @Column(name="weakness")
    private boolean weakness;

    @Column(name="numbness")
    private boolean numbness;

    @Column(name="symptoms")
    private String symptoms;

    @Column(name="other")
    private String other;

    @Column(name="aggrevated_factors")
    private String aggrevatedFactors;

    @Column(name="relieving_factors")
    private String relievingFactors;

    public Subjective(Examination examination){
        this.setExamination(examination);
    }

    public Subjective(){}

    public long getId() {
        return id;
    }

    public Examination getExamination() {
        return examination;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public boolean isPain() {
        return pain;
    }

    public void setPain(boolean pain) {
        this.pain = pain;
    }

    public boolean isStiffness() {
        return stiffness;
    }

    public void setStiffness(boolean stiffness) {
        this.stiffness = stiffness;
    }

    public boolean isWeakness() {
        return weakness;
    }

    public void setWeakness(boolean weakness) {
        this.weakness = weakness;
    }

    public boolean isNumbness() {
        return numbness;
    }

    public void setNumbness(boolean numbness) {
        this.numbness = numbness;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getAggrevatedFactors() {
        return aggrevatedFactors;
    }

    public void setAggrevatedFactors(String aggrevatedFactors) {
        this.aggrevatedFactors = aggrevatedFactors;
    }

    public String getRelievingFactors() {
        return relievingFactors;
    }

    public void setRelievingFactors(String relievingFactors) {
        this.relievingFactors = relievingFactors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }
}
