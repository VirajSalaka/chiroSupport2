package com.mycompany.chiroSupport.patientCase.objective;

import com.mycompany.chiroSupport.patientCase.Examination;

import javax.persistence.*;

/**
 * Created by Salaka on 4/14/2017.
 */

@Entity
@Table(name="specialtest",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class SpecialTest {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true)
    private long id;

    @ManyToOne
    @JoinColumn(name="exam_id", nullable=false)
    private Examination examination;

    @Column(name="region", nullable=false)
    private String region;

    @Column(name="location")
    private String location;

    @Column(name="test_result", nullable=false)
    private String result;

    @Column(name="comments")
    private String Comments;

    public SpecialTest(Examination examination){
        this.setExamination(examination);
    }
    public SpecialTest(){}

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public long getId() {
        return id;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }
}
