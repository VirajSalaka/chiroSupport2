package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.patientCase.Examination;
import com.mycompany.chiroSupport.patientCase.PatientCase;
import com.mycompany.chiroSupport.patientCase.Subjective;
import com.mycompany.chiroSupport.patientCase.VitalsReport;
import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.patientProfile.PatientQueueItem;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Salaka on 4/24/2017.
 */
public class patientDashboardController implements Initializable{

    private Patient patient;
    private PatientCase patientCase;
    private Session session;
    private Examination examination;

    //vitalsReport
    @FXML
    private DatePicker vitalsDateFld;
    @FXML
    private TextField vitalsHeightFld;
    @FXML
    private TextField vitalsWeightFld;
    @FXML
    private TextField vitalsPulseFld;
    @FXML
    private TextField vitalsRespirationFld;
    @FXML
    private TextField vitalsTemperatureFld;
    @FXML
    private TextField vitalsBloodPressureUBFld;
    @FXML
    private TextField vitalsBloodPressureLBFld;

    //Subjective
    @FXML
    private TextField subjectiveComplaintFld;
    @FXML
    private ChoiceBox<String> subjectiveRegionComboBox;
    @FXML
    private TextField subjectiveLocationFld;
    @FXML
    private ChoiceBox<String> subjectiveFrequencyComboBox;
    @FXML
    private ChoiceBox<String> subjectiveSeverityComboBox;
    @FXML
    private TextField subjectiveSymptomsFld;
    @FXML
    private CheckBox subjectivePainCheckBox;
    @FXML
    private CheckBox subjectiveStiffnessCheckBox;
    @FXML
    private CheckBox subjectiveWeaknessCheckBox;
    @FXML
    private CheckBox subjectiveNumbnessCheckBox;
    @FXML
    private TextField subjectiveOtherFld;
//    @FXML
//    private TextField subjectiveAggrevatedByFld;
//    @FXML
//    private Button subjectiveAggrevatedByBtn;
    @FXML
    private TextArea subjectiveAggrevatedByArea;
//    @FXML
//    private TextField subjectiveRelievedByFld;
//    @FXML
//    private Button subjectiveRelievedByBtn;
    @FXML
    private TextArea subjectiveRelievedByArea;
    @FXML
    private Button subjectiveAddSubjectiveBtn;
    @FXML
    private Button subjectiveDetailedViewBtn;



    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


    public PatientCase getPatientCase() {
        return patientCase;
    }

    public void setPatientCase(PatientCase patientCase) {
        this.patientCase = patientCase;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public void vitalsSave(MouseEvent mouseEvent) {
        String date = String.valueOf(vitalsDateFld.getValue());
        String height = vitalsHeightFld.getText();
        String weight = vitalsWeightFld.getText();
        String pulse = vitalsPulseFld.getText();
        String respiration = vitalsRespirationFld.getText();
        String temperature = vitalsTemperatureFld.getText();
        String bloodPressureUB = vitalsBloodPressureUBFld.getText();
        String bloodPressureLB = vitalsBloodPressureLBFld.getText();

        VitalsReport vitalsReport = new VitalsReport();

        if(date.length()==0){
            //alert
        }else{
            vitalsReport.setDate(date);
        }

        try {
            if (height.length() > 0) {
                vitalsReport.setHeight(Integer.parseInt(height));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            if (weight.length() > 0) {
                vitalsReport.setWeight(Double.parseDouble(weight));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            if (pulse.length() > 0) {
                vitalsReport.setPulse(Integer.parseInt(pulse));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            if (respiration.length() > 0) {
                vitalsReport.setRespiration(Integer.parseInt(respiration));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            if (temperature.length() > 0) {
                vitalsReport.setTemperature(Double.parseDouble(temperature));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            if (bloodPressureUB.length() > 0) {
                vitalsReport.setBloodPressureUB(Integer.parseInt(bloodPressureUB));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            if (bloodPressureLB.length() > 0) {
                vitalsReport.setBloodPressureLB(Integer.parseInt(bloodPressureLB));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        try {
            // to save patient object in the database


            //start transaction
            session.beginTransaction();

            Query query = session.createQuery("from com.mycompany.chiroSupport.patientProfile.Patient");
            List<Patient> list = query.list();
            patient = list.get(0);
            vitalsReport.setPatient(patient);
            vitalsReport.setDate(date);


            //Save the Model object
            session.save(vitalsReport);

            //Commit transaction
            session.getTransaction().commit();

            System.out.println("Employee ID="+vitalsReport.getPatient().getName());

            //terminate session factory, otherwise program won't end

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please Try Again");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!"+e.toString());


            alert.showAndWait();
        }


    }

    public void SubjectiveSave(MouseEvent mouseEvent) {
        if (patientCase == null){
            patientCase = new PatientCase("low back pain","2020/11/11");
            patientCase.setPatient(patient);
        }


        String complaint = subjectiveComplaintFld.getText();
        String region = subjectiveRegionComboBox.getValue();
        String location = subjectiveLocationFld.getText();
        String frequency = "1";                              //subjectiveFrequencyComboBox.getValue();
        String severity =  "1";                               //subjectiveSeverityComboBox.getValue();
        String symptoms = subjectiveSymptomsFld.getText();
        String other = subjectiveOtherFld.getText();
        String aggrevatedByFactors = subjectiveAggrevatedByArea.getText();
        String relievedByFactors = subjectiveRelievedByArea.getText();

        if(examination == null){
            examination = new Examination(patientCase);
            examination.setDate("2020-11-11");
        }
        Subjective subjective = new Subjective(examination);

        if (complaint.length() !=0){

            subjective.setComplaint(complaint);
        }else{
            //Alert
            System.out.println("No complaint error");
        }

        if(region.length()!=0){
            subjective.setRegion(region);
        }

        if(location.length()>0){
            subjective.setLocation(location);
        }

        if (frequency.length()>0){
            // setvalues accordingly
        }

        if (severity.length()>0){
            // setvalues accordingly
        }

        if (symptoms.length()>0){
            subjective.setSymptoms(symptoms);
        }

        if(subjectivePainCheckBox.isSelected()){
            subjective.setPain(true);
        }else{
            subjective.setPain(false);
        }

        if(subjectiveStiffnessCheckBox.isSelected()){
            subjective.setStiffness(true);
        }else{
            subjective.setStiffness(false);
        }

        if(subjectiveWeaknessCheckBox.isSelected()){
            subjective.setWeakness(true);
        }else{
            subjective.setWeakness(false);
        }

        if(subjectiveNumbnessCheckBox.isSelected()){
            subjective.setNumbness(true);
        }else{
            subjective.setNumbness(false);
        }

        if(other.length()>0){
            subjective.setOther(other);
        }

        if(aggrevatedByFactors.length()>0){
            subjective.setAggrevatedFactors(aggrevatedByFactors);
        }

        if (relievedByFactors.length()>0){
            subjective.setRelievingFactors(relievedByFactors);
        }

        session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        session.beginTransaction();
        session.save(patientCase);
        session.save(examination);
        session.save(subjective);

        session.getTransaction().commit();

    }



    public void initialize(URL location, ResourceBundle resources) {
        subjectiveRegionComboBox.getItems().addAll("aaa","bbb","ccc");
        //start transaction
        try {
            session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
            Transaction t = session.beginTransaction();

            Query query = session.createQuery("from com.mycompany.chiroSupport.patientProfile.Patient");
            List<Patient> list = query.list();
            patient = list.get(0);

            t.commit();
        }finally {
            session.close();
        }
        //session.getTransaction().commit();
    }



}
