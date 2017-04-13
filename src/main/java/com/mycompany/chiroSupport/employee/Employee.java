package com.mycompany.chiroSupport.employee;



/**
 * Created by Salaka on 3/26/2017.
 */
public abstract class Employee {
    private String employeeName;
    private String contactNo;
    private String employeeID;

    //to set the attributes to employee instance
    public Employee(String employeeID, String employeeName, String contactNo){
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.contactNo = contactNo;
    }

  /*  public Patient searchPatientByNic(String nicNo){

        //return the value form select query
    }

    public Patient searchPatientByName(String name){
        //
    }*/


}
