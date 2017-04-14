package com.mycompany.chiroSupport.patientCase.objective;

import com.mycompany.chiroSupport.patientCase.Examination;

import javax.persistence.*;

/**
 * Created by Salaka on 4/14/2017.
 */
@Entity
@Table(name="palpation",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class Palpation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @OneToOne
    @JoinColumn(name="exam_id", nullable=false)
    private Examination examination;

    @Column(name="description", nullable=false)
    private String description;

    public Palpation(Examination examination){
        this.examination = examination;
    }

    public long getId() {
        return id;
    }

    public Examination getExamination() {
        return examination;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
