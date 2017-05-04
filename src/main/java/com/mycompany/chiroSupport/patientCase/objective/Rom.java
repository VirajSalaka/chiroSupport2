package com.mycompany.chiroSupport.patientCase.objective;

import com.mycompany.chiroSupport.patientCase.Examination;

import javax.persistence.*;

/**
 * Created by Salaka on 4/14/2017.
 */
@Entity
@Table(name="rom",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class Rom {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @ManyToOne
    @JoinColumn(name="exam_id", nullable=false)
    private Examination examination;

    @Column(name="region", nullable =false)
    private String region;

    @Column(name="rom_type", nullable=false)
    private String romType;

    @Column(name="flexion")
    private int flexion;

    @Column(name="flexion_pain")
    private int flexionPain;

    @Column(name="extention")
    private int extention;

    @Column(name="extention_pain")
    private int extentionPain;

    @Column(name="llf")
    private int llf;

    @Column(name="llf_pain")
    private int llfPain;

    @Column(name="rlf")
    private int rlf;

    @Column(name="rlf_pain")
    private int rlfPain;

    @Column(name="lr")
    private int lr;

    @Column(name="lr_pain")
    private int lrPain;

    @Column(name="rr")
    private int rr;

    @Column(name="rr_pain")
    private int rrPain;

    @Column(name="total_loss")
    private int totalLoss;

    @Column(name="comments")
    private String comments;

    public Rom(Examination examination){
        this.setExamination(examination);
    }
    public Rom(){}

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

    public void setId(long id) {
        this.id = id;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
