package com.mycompany.chiroSupport.patientProfile;

import javax.persistence.*;

/**
 * Created by Salaka on 5/16/2017.
 */
@Entity
@Table(name="appointment",
        uniqueConstraints={@UniqueConstraint(columnNames={"user_id"})})
public class Appointment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @Column(name="createdDate", nullable=false)
    private String date;

    @Column(name="description", nullable=false)
    private String Description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
