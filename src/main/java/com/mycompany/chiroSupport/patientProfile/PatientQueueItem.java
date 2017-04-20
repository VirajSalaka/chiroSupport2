package com.mycompany.chiroSupport.patientProfile;

import javax.persistence.*;

/**
 * Created by Salaka on 4/20/2017.
 */

@Entity
@Table(name="queue",
        uniqueConstraints={@UniqueConstraint(columnNames={"patient_id","given_Number"})})
public class PatientQueueItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="given_number", nullable=false, unique=true)
    private int givenNumber;

    @OneToOne
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @Column(name="status")
    private int status; //0 for not checked, 1 for currently checking , 2 for completed checking


    public PatientQueueItem(Patient patient){
        this.setPatient(patient);
    }

    public PatientQueueItem(){}

    public int getGivenNumber() {
        return givenNumber;
    }

    public Patient getPatient() {
        return patient;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setGivenNumber(int givenNumber) {
        this.givenNumber = givenNumber;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
