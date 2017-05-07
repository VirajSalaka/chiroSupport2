package com.mycompany.chiroSupport.patientCase.objective;

import com.mycompany.chiroSupport.patientCase.Examination;

import javax.persistence.*;

/**
 * Created by Salaka on 4/14/2017.
 */

@Entity
@Table(name="diagnosticstudy",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class DiagnosticStudy {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @ManyToOne
    @JoinColumn(name="exam_id", nullable=false)
    private Examination examination;

    @Column(name="date_of_study")
    private String dateOfStudy;

    @Column(name="study_type", nullable=false)
    private String studyType;

    @Column(name="region")
    private String region;

    @Column(name="filename")
    private String fileName;

    @Column(name="impression")
    private String impression;

    public DiagnosticStudy(Examination examination){
        this.setExamination(examination);
    }

    public DiagnosticStudy(){}

    public long getId() {
        return id;
    }

    public Examination getExamination() {
        return examination;
    }

    public String getDateOfStudy() {
        return dateOfStudy;
    }

    public void setDateOfStudy(String date) {
        this.dateOfStudy = date;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }
}
