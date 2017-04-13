package com.mycompany.chiroSupport.patientCase;


import javax.persistence.*;

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
}
