package com.mycompany.chiroSupport.patientCase.objective;

import com.mycompany.chiroSupport.patientCase.Examination;

import javax.persistence.*;

/**
 * Created by Salaka on 4/14/2017.
 */

@Entity
@Table(name="musclepower",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class MusclePower {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @ManyToOne
    @JoinColumn(name="exam_id", nullable=false)
    private Examination examination;

    @Column(name="region")
    private String region;

    @Column(name="muscle")
    private String muscle;

    @Column(name="power_level")
    private int powerLevel;

    @Column(name="comments")
    private String comments;

    public MusclePower(Examination examination){
        this.setExamination(examination);
    }

    public MusclePower(){}

    public long getId() {
        return id;
    }


    public Examination getExamination() {
        return examination;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int power) {
        this.powerLevel = power;
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
