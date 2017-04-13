package com.mycompany.chiroSupport.patientCase;


import java.util.ArrayList;

public class PatientCase {
    private int caseId;
    private String caseName;
    private String createdDate;
    private ArrayList<Examination> examList;

    public PatientCase(String name, String createdDate){
        this.caseName = name;
        this.createdDate = createdDate;
    }

    public void setCaseId(int caseId){
        this.caseId = caseId;
    }

    public int getCaseId() {
        return caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void addExams(Examination examination){
        examList.add(examination);
    }
}
