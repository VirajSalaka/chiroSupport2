package com.mycompany.chiroSupport.patientProfile;

import com.mycompany.chiroSupport.patientCase.PatientCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="patient",
        uniqueConstraints={@UniqueConstraint(columnNames={"refNo","nicNo","bhtNo"})})
public class Patient implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="refNo", nullable=false, unique=true, length=11)
    private int refNo;

    @Column(name="name", length=150, nullable=false)
    private String name;

    @Column(name="nicNo", length=9)
    private int nicNo;

    @Column(name="bhtNo", length=8)
    private int bhtNo;

    @Column(name="dob")
    private String dob;

    @Column(name="gender", length=1, nullable=false)
    private int gender;  //male =1 and Female =2

    @Column(name="address", length=255)
    private String address;

    @Column(name="contactNo", length=9)
    private int contactNo;



    public Patient( String name, int nicNo, int bhtNo,String dob, int gender,String address,int contactNo){
        this.name = name;
        this.nicNo = nicNo;
        this.bhtNo = bhtNo;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.contactNo = contactNo;
    }

    public Patient() {
    }

    //reference number will be
    public void setRefNo(int refNo){
        this.refNo = refNo;
    }


    public int getRefNo() {
        return refNo;
    }

    public String getName() {
        return name;
    }

    public int getNicNo() {
        return nicNo;
    }

    public int getBhtNo() {
        return bhtNo;
    }

    public String getDob() {
        return dob;
    }

    public int getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public int getContactNo() {
        return contactNo;
    }

    public void addPatientCase(PatientCase p){

    }

    /*
   to create Patient's profile and add it to the database
    */
    /*public void saveInDatabase () throws SQLException, ClassNotFoundException {
        Connection conn= null;//DBConnection.getInstance().getConnection();

        String query = "insert into patient (name,nicNo,bhtNo,gender,dob,address,contactNo)  values (?,?,?,?,?,?,?)";
        PreparedStatement preparedStmt = conn.prepareStatement(query);

        preparedStmt.setString (1,name);
        preparedStmt.setInt(2,nicNo);
        preparedStmt.setInt(3,bhtNo);
        preparedStmt.setInt(4,gender);
        preparedStmt.setString(5,dob);
        preparedStmt.setString(6,address);
        preparedStmt.setInt(7,contactNo);

        preparedStmt.execute();

        conn.close();

    }*/

    /*
   display available patients to the physiotherapist
    */
    public void addToQueue(){

    }


}
