package com.mycompany.chiroSupport.patientCase.objective;

import com.mycompany.chiroSupport.patientCase.Examination;

/**
 * Created by Salaka on 4/14/2017.
 */
public class Rom {
    private long id;
    private Examination examination;
    private String romType;
    private int flexion;
    private int flexionPain;
    private int extention;
    private int extentionPain;
    private int llf;
    private int llfPain;
    private int rlf;
    private int rlfPain;
    private int lr;
    private int lrPain;
    private int rr;
    private int rrPain;
    private int totalLoss;
    private String comments;

    public Rom(Examination examination){
        this.examination = examination;
    }

    public long getId() {
        return id;
    }

    public Examination getExamination() {
        return examination;
    }

    public String getRomType() {
        return romType;
    }

    public void setRomType(String romType) {
        this.romType = romType;
    }

    public int getFlexion() {
        return flexion;
    }

    public void setFlexion(int flexion) {
        this.flexion = flexion;
    }

    public int getFlexionPain() {
        return flexionPain;
    }

    public void setFlexionPain(int flexionPain) {
        this.flexionPain = flexionPain;
    }

    public int getExtention() {
        return extention;
    }

    public void setExtention(int extention) {
        this.extention = extention;
    }

    public int getExtentionPain() {
        return extentionPain;
    }

    public void setExtentionPain(int extentionPain) {
        this.extentionPain = extentionPain;
    }

    public int getLlf() {
        return llf;
    }

    public void setLlf(int llf) {
        this.llf = llf;
    }

    public int getLlfPain() {
        return llfPain;
    }

    public void setLlfPain(int llfPain) {
        this.llfPain = llfPain;
    }

    public int getRlf() {
        return rlf;
    }

    public void setRlf(int rlf) {
        this.rlf = rlf;
    }

    public int getRlfPain() {
        return rlfPain;
    }

    public void setRlfPain(int rlfPain) {
        this.rlfPain = rlfPain;
    }

    public int getLr() {
        return lr;
    }

    public void setLr(int lr) {
        this.lr = lr;
    }

    public int getLrPain() {
        return lrPain;
    }

    public void setLrPain(int lrPain) {
        this.lrPain = lrPain;
    }

    public int getRr() {
        return rr;
    }

    public void setRr(int rr) {
        this.rr = rr;
    }

    public int getRrPain() {
        return rrPain;
    }

    public void setRrPain(int rrPain) {
        this.rrPain = rrPain;
    }

    public int getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(int totalLoss) {
        this.totalLoss = totalLoss;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
