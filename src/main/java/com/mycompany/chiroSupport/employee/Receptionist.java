package com.mycompany.chiroSupport.employee;




public class Receptionist extends Employee {

    public Receptionist(String employeeID, String employeeName, String contactNo) {
        super(employeeID, employeeName, contactNo);
    }


   /*
   create patient's profile and add details to the database
    */
   public void addPatient(String name, int nicNo, int bhtNo,String dob, int gender,String address,int contactNo){

   }


    /*
    search patient via refNo
     */
    public void searchPatientbyRefNo(int refNO){
        //find the relevant patient from the database
    }


    /*
    search patient via name
     */
    public void searchPatientbyName(String name){
        //find the relevant patient from the database
    }

    /*
    search patient via nicNO
     */
    public void searchPatientbyNicNO(int nicNo){
        //find the relevant patient from the database
    }

}
