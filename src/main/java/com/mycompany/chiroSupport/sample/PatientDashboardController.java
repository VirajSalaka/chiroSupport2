package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.patientCase.*;
import com.mycompany.chiroSupport.patientCase.objective.*;
import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.patientProfile.PatientQueueItem;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 * Created by Salaka on 4/24/2017.
 */
public class PatientDashboardController implements Initializable{

    private Patient patient;
    private PatientCase patientCase;
    private boolean casesAvailable = false;
    private Session session;
    private Examination examination;
    private List<Examination> caseRelatedExamList;
    private List<SpecialTest> specialTestCurrentList;
    private List<Rom> romTestTotalList;
    private List<MusclePower> currentMusclePowerList;
    private List<DiagnosticStudy> diagnosticStudyTotalList;
    private List<NeurologicalStudy> neurologicalStudyTotalList;
    private File diagnosticStudySourceFile;
    private int diagnosticStudyCount = 0;
    private File neurologicalStudySourceFile;
    private int neurologicalStudyCount = 0;

    //patientProfile
    @FXML
    private TextField patientProfileNameFld;
    @FXML
    private TextField patientProfileGenderFld;
    @FXML
    private TextField patientProfileNicNoFld;
    @FXML
    private TextField patientProfileBhtNoFld;
    @FXML
    private TextField patientProfileContactNoFld;
    @FXML
    private TextField patientProfileDobFld;
    @FXML
    private TextField patientProfileCurrentPatientCaseFld;
    @FXML
    private TextArea patientProfileAddressArea;
    @FXML
    private ListView<PatientCase> patientProfilePatientCaseListView;

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
    @FXML
    private TableView<Subjective> subjectiveTableView;
    @FXML
    private TableColumn<Subjective,String> subjectiveDateColumn;
    @FXML
    private TableColumn<Subjective,String> subjectiveRegionColumn;
    @FXML
    private TableColumn<Subjective,String> subjectiveLocationColumn;
    @FXML
    private TableColumn<Subjective,String> subjectiveSymptomsColumn;
    @FXML
    private TableColumn<Subjective,String> subjectiveSeverityColumn;

    //Observation
    @FXML
    private TextArea newObservationArea;
    @FXML
    private TextField recentObservationDateFld;
    @FXML
    private TextArea recentObservationArea;
    @FXML
    private Button observationSaveBtn;
    @FXML
    private ListView<Observation> observationListView;

    //Palpation
    @FXML
    private TextArea newPalpationArea;
    @FXML
    private TextField recentPalpationDateFld;
    @FXML
    private TextArea recentPalpationArea;
    @FXML
    private Button palpationSaveBtn;
    @FXML
    private ListView<Palpation> palpationListView;
    
    //special Tests
    @FXML
    private ChoiceBox<String> specialTestsRegionComboBox;
    @FXML
    private TextField specialTestsTestFld;
    @FXML
    private TextField specialTestsLocationFld;
    @FXML
    private TextField specialTestsResultFld;
    @FXML
    private TextArea specialTestsCommentsArea;
    @FXML
    private Button specialTestsAddResultsBtn;
    @FXML
    private TableView<SpecialTest> specialTestCurrentSessionTableView;
    @FXML
    private TableColumn<SpecialTest,String> specialTestCurrentSessionRegionColumn;
    @FXML
    private TableColumn<SpecialTest,String> specialTestCurrentSessionTestColumn;
    @FXML
    private TableColumn<SpecialTest,String> specialTestCurrentSessionResultColumn;
    @FXML
    private TableColumn<SpecialTest,String> specialTestCurrentSessionCommentsColumn;
    @FXML
    private TableView<SpecialTest> specialTestPreviousTestsTableView;
    @FXML
    private TableColumn<SpecialTest,String> specialTestPreviousTestsDateColumn;
    @FXML
    private TableColumn<SpecialTest,String> specialTestPreviousTestsRegionColumn;
    @FXML
    private TableColumn<SpecialTest,String> specialTestPreviousTestsTestColumn;
    @FXML
    private TableColumn<SpecialTest,String> specialTestPreviousTestsResultColumn;
    @FXML
    private TableColumn<SpecialTest,String> specialTestPreviousTestsCommentsColumn;

    //ROM
    @FXML
    private TextField romRegionFld;
    @FXML
    private TextField romTypeFld;
    @FXML
    private ChoiceBox<String> romFlexionChoiceBox;
    @FXML
    private ChoiceBox<String> romExtentionChoiceBox;
    @FXML
    private ChoiceBox<String> romLLFChoiceBox;
    @FXML
    private ChoiceBox<String> romRLFChoiceBox;
    @FXML
    private ChoiceBox<String> romLRChoiceBox;
    @FXML
    private ChoiceBox<String> romRRChoiceBox;
    @FXML
    private ChoiceBox<String> romFlexionPainChoiceBox;
    @FXML
    private ChoiceBox<String> romExtentionPainChoiceBox;
    @FXML
    private ChoiceBox<String> romLLFPainChoiceBox;
    @FXML
    private ChoiceBox<String> romRLFPainChoiceBox;
    @FXML
    private ChoiceBox<String> romLRPainChoiceBox;
    @FXML
    private ChoiceBox<String> romRRPainChoiceBox;
    @FXML
    private TextField romTotalLossFld;
    @FXML
    private TextArea romCommentsArea;
    @FXML
    private TableView<Rom> romPreviousResultsTableView;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsDateColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsRegionColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsFlexionTotalColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsFlexionPFColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsExtentionTotalColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsExtentionPFColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsLLFTotalColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsLLFPFColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsRLFTotalColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsRLFPFColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsLRTotalColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsLRPFColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsRRTotalColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsRRPFColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsTotalLossColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsTypeColumn;
    @FXML
    private TableColumn<Rom,String> romPreviousResultsCommentsColumn;

    //Muscle - Power
    @FXML
    private TextField musclePowerRegionFld;
    @FXML
    private TextField musclePowerMuscleFld;
    @FXML
    private ChoiceBox<String> musclePowerPowerChoiceBox;
    @FXML
    private TextField musclePowerCommentsFld;
    @FXML
    private TableView<MusclePower> musclePowerPreviousTableView;
    @FXML
    private TableColumn<MusclePower,String> musclePowerPreviousDateColumn;
    @FXML
    private TableColumn<MusclePower,String> musclePowerPreviousMuscleColumn;
    @FXML
    private TableColumn<MusclePower,String> musclePowerPreviousRegionColumn;
    @FXML
    private TableColumn<MusclePower,String> musclePowerPreviousPowerColumn;
    @FXML
    private TableColumn<MusclePower,String> musclePowerPreviousCommentsColumn;
    @FXML
    private TableView<MusclePower> musclePowerCurrentTableView;
    @FXML
    private TableColumn<MusclePower,String> musclePowerCurrentMuscleColumn;
    @FXML
    private TableColumn<MusclePower,String> musclePowerCurrentRegionColumn;
    @FXML
    private TableColumn<MusclePower,String> musclePowerCurrentPowerColumn;
    @FXML
    private TableColumn<MusclePower,String> musclePowerCurrentCommentsColumn;


    //Diagnostic studies
    @FXML
    private DatePicker diagnosticStudiesDateFld;
    @FXML
    private TextField diagnosticStudiesRegionFld;
    @FXML
    private ChoiceBox<String> diagnosticStudiesTypeOfStudyChoiceBox;
    @FXML
    private Button diagnosticStudiesUploadFileBtn;
    @FXML
    private Label diagnosticStudiesFileNameLabel;
    @FXML
    private TextArea diagnosticStudiesImpressionArea;
    @FXML
    private TableView<DiagnosticStudy> diagnosticStudiesTableView;
    @FXML
    private TableColumn<DiagnosticStudy,String> diagnosticStudiesDateColumn;
    @FXML
    private TableColumn<DiagnosticStudy,String> diagnosticStudiesTypeColumn;
    @FXML
    private TableColumn<DiagnosticStudy,String> diagnosticStudiesRegionColumn;
    @FXML
    private TableColumn<DiagnosticStudy,String> diagnosticStudiesImpressionColumn;
    @FXML
    private TableColumn<DiagnosticStudy,String> diagnosticStudiesAttachmentColumn;


    //neurological studies
    @FXML
    private DatePicker neurologicalStudiesDateFld;
    @FXML
    private TextField neurologicalStudiesRegionFld;
    @FXML
    private TextField neurologicalStudiesTypeOfStudyFld;
    @FXML
    private Button neurologicalStudiesUploadFileBtn;
    @FXML
    private Label neurologicalStudiesFileNameLabel;
    @FXML
    private TextArea neurologicalStudiesImpressionArea;
    @FXML
    private TableView<NeurologicalStudy> neurologicalStudiesTableView;
    @FXML
    private TableColumn<NeurologicalStudy,String> neurologicalStudiesDateColumn;
    @FXML
    private TableColumn<NeurologicalStudy,String> neurologicalStudiesTypeColumn;
    @FXML
    private TableColumn<NeurologicalStudy,String> neurologicalStudiesRegionColumn;
    @FXML
    private TableColumn<NeurologicalStudy,String> neurologicalStudiesImpressionColumn;
    @FXML
    private TableColumn<NeurologicalStudy,String> neurologicalStudiesAttachmentColumn;

    //analysis
    @FXML
    private ChoiceBox<String> analysisPatientConditionChoiceBox;
    @FXML
    private ChoiceBox<String> analysisPatientProgressingChoiceBox;
    @FXML
    private RadioButton analysisTreatmentEffectiveYesRadioBtn;
    @FXML
    private RadioButton analysisTreatmentEffectiveNoRadioBtn;
    @FXML
    private RadioButton analysisTreatmentEffectiveUnableRadioBtn;
    @FXML
    private ChoiceBox<String> analysisPrognosisChoiceBox;
    @FXML
    private TextArea analysisCommentsArea;
    @FXML
    private TableView<Analysis> analysisTableView;
    @FXML
    private TableColumn<Analysis,String> analysisDateColumn;
    @FXML
    private TableColumn<Analysis,String> analysisConditionColumn;
    @FXML
    private TableColumn<Analysis,String> analysisProgressColumn;
    @FXML
    private TableColumn<Analysis,String> analysisEffectivenessColumn;
    @FXML
    private TableColumn<Analysis,String> analysisPrognosisColumn;
    @FXML
    private TableColumn<Analysis,String> analysisCommentsColumn;

    //treatment
    @FXML
    private TextField treatmentTreatmentFld;
    @FXML
    private ChoiceBox<String> treatmentFrequencyChoiceBox;
    @FXML
    private ChoiceBox<String> treatmentDurationChoiceBox;
    @FXML
    private TextArea treatmentAdjustmentsArea;


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
        if(examination==null){
            session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
            try {
                Transaction t = session.beginTransaction();

                examination = new Examination(patientCase);
                examination.setDate(String.valueOf(LocalDate.now()));
                session.save(examination);

                t.commit();
            } finally {
                session.close();
            }
        }
        if((caseRelatedExamList = patientCase.getExamList())!= null){
            casesAvailable = true;
        }

        personalDetailsInitialize();
        subjectiveInitialize();
        addPreviousObservation();
        addPreviousPalpation();
        specialTestIntialize();
        romTestInitialize();
        musclePowerInitialize();
        diagnosticStudyInitialize();
        neurologicalStudyInitialize();
        analysisInitialize();
        treatmentPlanInitialize();

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

        session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        try {
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

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please Try Again");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!"+e.toString());


            alert.showAndWait();
        }finally {
            session.close();
        }


    }

    public void SubjectiveSave(MouseEvent mouseEvent) {


        String complaint = subjectiveComplaintFld.getText();
        String region = subjectiveRegionComboBox.getValue();
        String location = subjectiveLocationFld.getText();
        String frequency ;
        String severity ;
        String symptoms = subjectiveSymptomsFld.getText();
        String other = subjectiveOtherFld.getText();
        String aggrevatedByFactors = subjectiveAggrevatedByArea.getText();
        String relievedByFactors = subjectiveRelievedByArea.getText();


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

        if ((frequency = subjectiveFrequencyComboBox.getValue())!=null){
            subjective.setFrequency(frequency);
        }

        if ((severity = subjectiveSeverityComboBox.getValue())!=null){
            subjective.setSeverity(severity);
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
        saveObjectInDatabase(subjective);
    }

    public void observationSave(MouseEvent mouseEvent) {
        String observationText = newObservationArea.getText();
        Observation observation;
        if (observationText.length()>0) {
            observation = new Observation(examination);
            observation.setDescription(observationText);
            saveObjectInDatabase(observation);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No observation to be added");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();
        }
    }


    public void palpationSave(MouseEvent mouseEvent) {
        String palpationText = newPalpationArea.getText();
        Palpation palpation;
        if (palpationText.length()>0) {
            palpation = new Palpation(examination);
            palpation.setDescription(palpationText);
            saveObjectInDatabase(palpation);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Palpation to be added");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();
        }
    }

    public void specialResultsSave(MouseEvent mouseEvent) {
        String region = specialTestsRegionComboBox.getValue();
        String location = specialTestsLocationFld.getText();
        String test = specialTestsTestFld.getText();
        String result = specialTestsResultFld.getText();
        String comments = specialTestsCommentsArea.getText();

        SpecialTest specialTest;

        if(region.length()>0 && test.length()>0 && result.length()>0){
            specialTest = new SpecialTest(examination);
            specialTest.setRegion(region);
            specialTest.setLocation(location);
            specialTest.setTest(test);
            specialTest.setResult(result);
            specialTest.setComments(comments);

            saveObjectInDatabase(specialTest);
            specialTestCurrentList.add(specialTest);

            specialTestCurrentSessionRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
            specialTestCurrentSessionTestColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getTest()));
            specialTestCurrentSessionResultColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getResult()));
            specialTestCurrentSessionCommentsColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getComments()));
            specialTestCurrentSessionTableView.setItems(FXCollections.observableList(specialTestCurrentList));
        }


    }

    public void romSave(MouseEvent mouseEvent) {

        String flexion = null;
        String flexionPain = null;
        String extention = null;
        String llf = null;
        String rlf = null;
        String lr = null;
        String rr = null;
        String extentionPain = null;
        String llfPain = null;
        String rlfPain = null;
        String lrPain = null;
        String rrPain = null;

        String region = romRegionFld.getText();
        String type = romTypeFld.getText();
        flexion = romFlexionChoiceBox.getValue();
        flexionPain = romFlexionPainChoiceBox.getValue();
        extention = romExtentionChoiceBox.getValue();
        llf = romLLFChoiceBox.getValue();
        rlf = romRLFChoiceBox.getValue();
        lr =romLRChoiceBox.getValue();
        rr = romRRChoiceBox.getValue();
        extentionPain = romExtentionPainChoiceBox.getValue();
        llfPain = romLLFPainChoiceBox.getValue();
        rlfPain = romRLFPainChoiceBox.getValue();
        lrPain = romLRPainChoiceBox.getValue();
        rrPain = romRRPainChoiceBox.getValue();
        String totalLoss = romTotalLossFld.getText();
        String comments =  romCommentsArea.getText();

        if (region.length()>0 && type.length()>0){
            Rom rom = new Rom(examination);
            rom.setRegion(region);
            rom.setRomType(type);

            if(!extention.equals("not relevant")){
                rom.setExtention(Integer.parseInt(extention));
            }
            if(!flexion.equals("not relevant")){
                rom.setFlexion(Integer.parseInt(flexion));
            }
            if(!llf.equals("not relevant")){
                rom.setLlf(Integer.parseInt(llf));
            }
            if(!rlf.equals("not relevant")){
                rom.setRlf(Integer.parseInt(rlf));
            }
            if(!lr.equals("not relevant")){
                rom.setLr(Integer.parseInt(lr));
            }
            if(!rr.equals("not relevant")){
                rom.setRr(Integer.parseInt(rr));
            }
            if(!extentionPain.equals("not relevant")){
                rom.setExtentionPain(Integer.parseInt(extentionPain));
            }
            if(!flexionPain.equals("not relevant")){
                rom.setFlexionPain(Integer.parseInt(flexionPain));
            }
            if(!llfPain.equals("not relevant")){
                rom.setLlfPain(Integer.parseInt(llfPain));
            }
            if(!rlfPain.equals("not relevant")){
                rom.setRlfPain(Integer.parseInt(rlfPain));
            }
            if(!lrPain.equals("not relevant")){
                rom.setLrPain(Integer.parseInt(lrPain));
            }
            if(!rrPain.equals("not relevant")){
                rom.setRrPain(Integer.parseInt(rrPain));
            }

            try {
                if (totalLoss.length() > 0) {
                    rom.setTotalLoss(Integer.parseInt(totalLoss));
                }
            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Integer for totalLoss please ");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("Ooops, there was an error!");
                alert.showAndWait();
            }

            if(comments.length() >0){
                rom.setComments(comments);
            }
            saveObjectInDatabase(rom);
            romTestTotalList.add(0,rom);
            romPreviousResultsTableView.setItems(FXCollections.observableList(romTestTotalList));
            romPreviousResultsDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
            romPreviousResultsRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
            romPreviousResultsExtentionTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getExtention())));
            romPreviousResultsExtentionPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getExtentionPain())));
            romPreviousResultsFlexionTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getFlexion())));
            romPreviousResultsFlexionPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getFlexionPain())));
            romPreviousResultsLLFTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getLlf())));
            romPreviousResultsLLFPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getLlfPain())));
            romPreviousResultsRLFTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getRlf())));
            romPreviousResultsRLFPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getRlfPain())));
            romPreviousResultsLRTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getLr())));
            romPreviousResultsLRPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getLrPain())));
            romPreviousResultsRRTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getRr())));
            romPreviousResultsRRPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getRrPain())));
            romPreviousResultsTotalLossColumn.setCellValueFactory(c->new SimpleStringProperty(String.valueOf(c.getValue().getTotalLoss())));
            romPreviousResultsTypeColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRomType()));
            romPreviousResultsCommentsColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getComments()));

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not completed Rom Test");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }

    }


    public void musclePowerSave(MouseEvent mouseEvent) {
        String region = musclePowerRegionFld.getText();
        String muscle = musclePowerMuscleFld.getText();
        String power = musclePowerPowerChoiceBox.getValue();
        String comments = musclePowerCommentsFld.getText();

        if(region.length()>0 || muscle.length()>0 ||power.equals("not relevant")){
            MusclePower musclePower = new MusclePower(examination);
            musclePower.setRegion(region);
            musclePower.setMuscle(muscle);
            musclePower.setPowerLevel(Integer.parseInt(power));

            if(comments.length()>0){
                musclePower.setComments(comments);
            }

            saveObjectInDatabase(musclePower);
            currentMusclePowerList.add(0,musclePower);
            musclePowerCurrentTableView.setItems(FXCollections.observableList(currentMusclePowerList));
            musclePowerCurrentMuscleColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getMuscle()));
            musclePowerCurrentRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
            musclePowerCurrentPowerColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getPowerLevel())));
            musclePowerCurrentCommentsColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getComments()));

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not completed Muscle power fill properly");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }
    }

    public void diagnosticStudiesUploadFile(MouseEvent mouseEvent) {
        Node node=(Node) mouseEvent.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;/* Exception */
        try {
            root = FXMLLoader.load(getClass().getResource("/patientDashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter= new FileChooser.ExtensionFilter("TXT files (*.pdf)","*.pdf");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(stage);
        diagnosticStudySourceFile = file;
        diagnosticStudiesFileNameLabel.setText(file.toPath().toString());

    }

    public void diagnosticStudiesSave(MouseEvent mouseEvent) {
        String date = String.valueOf(diagnosticStudiesDateFld.getValue());
        String type = diagnosticStudiesTypeOfStudyChoiceBox.getValue();
        String region = diagnosticStudiesRegionFld.getText();
        String impression = diagnosticStudiesImpressionArea.getText();

        if(!date.equals(null) && !type.equals("not selected") && !diagnosticStudySourceFile.equals(null)){
            DiagnosticStudy diagnosticStudy = new DiagnosticStudy(examination);
            diagnosticStudy.setDateOfStudy(date);
            diagnosticStudy.setStudyType(type);

            if(region.length()>0){
                diagnosticStudy.setRegion(region);
            }
            if(impression.length()>0){
                diagnosticStudy.setImpression(impression);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("C:\\Users\\Salaka\\Desktop\\Econ\\");
            sb.append(patient.getName());
            //sb.append("\\exam_");
            sb.append(examination.getDate());
            //sb.append("\\diagnosticStudies\\");
            sb.append(type);
         //   sb.append("\\");
            sb.append(String.valueOf(diagnosticStudyCount));
            sb.append(".");
            sb.append(FilenameUtils.getExtension(diagnosticStudySourceFile.toPath().toString()));

            File savingFile = new File(sb.toString());
            diagnosticStudy.setFileName(sb.toString());

            if (diagnosticStudySourceFile != null) {
            try {
                Files.copy(diagnosticStudySourceFile.toPath(),savingFile.toPath());
                saveObjectInDatabase(diagnosticStudy);
                diagnosticStudyCount++;
                diagnosticStudyTotalList.add(0,diagnosticStudy);
                diagnosticStudiesTableView.setItems(FXCollections.observableList(diagnosticStudyTotalList));
                diagnosticStudiesDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
                diagnosticStudiesRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
                diagnosticStudiesTypeColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getStudyType()));
                diagnosticStudiesImpressionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getImpression()));
                diagnosticStudiesAttachmentColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getFileName()));

            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Exception in File Upload");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("Ooops, there was an error!");
                alert.showAndWait();
            }
        }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Complete properly");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }
    }

    public void neurologicalStudyUploadFile(MouseEvent mouseEvent) {
        Node node=(Node) mouseEvent.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;/* Exception */
        try {
            root = FXMLLoader.load(getClass().getResource("/patientDashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter= new FileChooser.ExtensionFilter("TXT files (*.pdf)","*.pdf");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showSaveDialog(stage);
        neurologicalStudySourceFile = file;
        neurologicalStudiesFileNameLabel.setText(file.toPath().toString());

    }


    public void neurologicalStudySave(MouseEvent mouseEvent) {
        String date = String.valueOf(neurologicalStudiesDateFld.getValue());
        String type = neurologicalStudiesTypeOfStudyFld.getText();
        String region = neurologicalStudiesRegionFld.getText();
        String impression = neurologicalStudiesImpressionArea.getText();

        if(!date.equals(null) && type.length()>0 && !neurologicalStudySourceFile.equals(null)){
            NeurologicalStudy neurologicalStudy = new NeurologicalStudy(examination);
            neurologicalStudy.setDateOfStudy(date);
            neurologicalStudy.setStudyType(type);

            if(region.length()>0){
                neurologicalStudy.setRegion(region);
            }
            if(impression.length()>0){
                neurologicalStudy.setImpression(impression);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("C:\\Users\\Salaka\\Desktop\\Econ\\");
            sb.append(patient.getName());
            //sb.append("\\exam_");
            sb.append(examination.getDate());
            //sb.append("\\neurological_studies\\");
            sb.append(type);
            sb.append("_");
            sb.append(String.valueOf(neurologicalStudyCount));
            sb.append(".");
            sb.append(FilenameUtils.getExtension(neurologicalStudySourceFile.toPath().toString()));

            System.out.println(sb.toString());

            File savingFile = new File(sb.toString());
            neurologicalStudy.setFileName(sb.toString());

            if (neurologicalStudySourceFile != null) {
                try {
                    Files.copy(neurologicalStudySourceFile.toPath(),savingFile.toPath());
                    session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
                    saveObjectInDatabase(neurologicalStudy);
                    neurologicalStudyCount++;

                    neurologicalStudyCount++;
                    neurologicalStudyTotalList.add(0,neurologicalStudy);
                    neurologicalStudiesTableView.setItems(FXCollections.observableList(neurologicalStudyTotalList));
                    neurologicalStudiesDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
                    neurologicalStudiesRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
                    neurologicalStudiesTypeColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getStudyType()));
                    neurologicalStudiesImpressionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getImpression()));
                    neurologicalStudiesAttachmentColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getFileName()));

                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Exception in File Upload");
                    alert.setHeaderText("Look, an Error Dialog");
                    alert.setContentText("Ooops, there was an error!"+ex.toString());
                    alert.showAndWait();
                }
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Complete properly");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }
    }


    public void analysisSave(MouseEvent mouseEvent) {
        String patientCondition = analysisPatientConditionChoiceBox.getValue();
        String patientProgressing = analysisPatientProgressingChoiceBox.getValue();
        String patientPrognosis = analysisPrognosisChoiceBox.getValue();
        String comments = analysisCommentsArea.getText();

        if (patientCondition.equals("not selected") && patientProgressing.equals("not selected") && patientPrognosis.equals("undetermined") &&
                comments.length() == 0 && analysisTreatmentEffectiveUnableRadioBtn.isSelected()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing to save :/");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        } else {
            Analysis analysis = new Analysis(examination);
            analysis.setPatientCondition(patientCondition);
            analysis.setPatientProgress(patientProgressing);
            analysis.setPrognosis(patientPrognosis);

            if (analysisTreatmentEffectiveUnableRadioBtn.isSelected()) {
                analysis.setEffectiveness(0);
            }
            if (analysisTreatmentEffectiveNoRadioBtn.isSelected()) {
                analysis.setEffectiveness(1);
            }
            if (analysisTreatmentEffectiveYesRadioBtn.isSelected()) {
                analysis.setEffectiveness(2);
            }

            if (comments.length() > 0) {
                analysis.setComments(comments);
            }

            saveObjectInDatabase(analysis);

        }
    }

    public void treatmentSave(MouseEvent mouseEvent) {
        String treatmentName = treatmentTreatmentFld.getText();
        String frequency = treatmentFrequencyChoiceBox.getValue();
        String duration = treatmentDurationChoiceBox.getValue();
        String adjustments = treatmentAdjustmentsArea.getText();

        if(treatmentName.length()>0 ){
            Treatment treatment = new Treatment(examination);
            treatment.setTreatmentName(treatmentName);
            treatment.setFrequency(frequency);
            treatment.setDuration(duration);
            if(adjustments.length()>0){
                treatment.setAdjustments(adjustments);
            }

            saveObjectInDatabase(treatment);

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fill treatment first");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");
            alert.showAndWait();
        }

    }


    public void initialize(URL location, ResourceBundle resources) {


        if(patientCase != null) {
            session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
            try {
                Transaction t = session.beginTransaction();

                examination = new Examination(patientCase);
                examination.setDate(String.valueOf(LocalDate.now()));
                session.save(examination);

                t.commit();
            } finally {
                session.close();
            }
        }
//        caseRelatedExamList = patientCase.getExamList();
//
//        personalDetailsInitialize();
//        subjectiveInitialize();
//        addPreviousObservation();
//        addPreviousPalpation();
//        specialTestIntialize();
//        romTestInitialize();
//        musclePowerInitialize();
//        diagnosticStudyInitialize();
//        neurologicalStudyInitialize();
//        analysisIntialize();
//        treatmentPlanInitialize();
    }

    public void saveObjectInDatabase(Object obj){
        session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List generalSelectQuery(String className){
        session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        Query query = null;
        List list;
        try {
            session.beginTransaction();
            query = session.createQuery("from com.mycompany.chiroSupport."+className);
            list = query.list();
            session.getTransaction().commit();
        }finally {
            session.close();
        }
        return list;
    }



    public void addPreviousObservation(){
        if(casesAvailable && caseRelatedExamList.size() != 0) {
            List<Observation> observationList = new ArrayList<>();

            for (int i = caseRelatedExamList.size() - 1; i >= 0; i--) {
                Observation obs = null;

                if ((obs = caseRelatedExamList.get(i).getObservation())!=null) {
                    observationList.add(obs);
                }
            }

            if(observationList.size()>0){
                recentPalpationArea.setText(observationList.get(0).getDescription());
                recentPalpationDateFld.setText(observationList.get(0).getExamination().getDate());
            }

            ObservableList<Observation> oList = FXCollections.observableList(observationList);
            observationListView.setItems(oList);

            observationListView.setCellFactory(new Callback<ListView<Observation>, ListCell<Observation>>(){

                public ListCell<Observation> call(ListView<Observation> o) {

                    ListCell<Observation> cell = new ListCell<Observation>(){

                        @Override
                        protected void updateItem(Observation observation, boolean bln) {
                            super.updateItem(observation, bln);
                            if (observation != null) {
                                setText(observation.getExamination().getDate());
                            }
                        }
                    };
                    return cell;
                }
            });

        }

    }

    public void observationListViewClicked(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Observation");
        alert.setHeaderText("description");
        alert.setContentText(observationListView.getSelectionModel().getSelectedItem().getDescription());
        alert.showAndWait();
    }

    public void addPreviousPalpation(){

        if(casesAvailable && caseRelatedExamList.size() != 0) {
            List<Palpation> palpationList = new ArrayList<>();

            for (int i = caseRelatedExamList.size() - 1; i >= 0; i--) {
                Palpation p = null;

                if ((p = caseRelatedExamList.get(i).getPalpation())!=null) {
                    palpationList.add(p);
                }
            }

            if(palpationList.size()>0){
                recentPalpationArea.setText(palpationList.get(0).getDescription());
                recentPalpationDateFld.setText(palpationList.get(0).getExamination().getDate());
            }

            ObservableList<Palpation> oList = FXCollections.observableList(palpationList);
            palpationListView.setItems(oList);

            palpationListView.setCellFactory(new Callback<ListView<Palpation>, ListCell<Palpation>>(){

                public ListCell<Palpation> call(ListView<Palpation> p) {

                    ListCell<Palpation> cell = new ListCell<Palpation>(){

                        @Override
                        protected void updateItem(Palpation palpation, boolean bln) {
                            super.updateItem(palpation, bln);
                            if (palpation != null) {
                                setText(palpation.getExamination().getDate());
                            }
                        }
                    };
                    return cell;
                }
            });

        }

    }

    public void palpationListViewClicked(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Palpation");
        alert.setHeaderText("description");
        alert.setContentText(palpationListView.getSelectionModel().getSelectedItem().getDescription());
        alert.showAndWait();
    }

    public void specialTestIntialize(){
        specialTestsRegionComboBox.getItems().addAll("head","neck","chest","Shoulder L", "shoulder R", "upper abdomen",
                "lower abdomen", "back", "arm L", "arm R", "palm L", "palm R","hip joint","Leg L", "leg R", "ankle L",
                "ankle R", "foot L", "foot R");
        specialTestCurrentList = new ArrayList<>();
        List<SpecialTest> specialTestTotalList = new ArrayList<>();
        if(casesAvailable && caseRelatedExamList.size()!=0){
            for(int i=caseRelatedExamList.size()-1;i>=0;i--){
                List<SpecialTest> tempList;
                if((tempList= caseRelatedExamList.get(i).getSpecialTestList())!=null){
                    for(int j=tempList.size()-1;j>=0;j--){
                        specialTestTotalList.add(tempList.get(j));
                        specialTestPreviousTestsTableView.setItems(FXCollections.observableList(specialTestTotalList));
                        specialTestPreviousTestsDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
                        specialTestPreviousTestsRegionColumn.setCellValueFactory(new PropertyValueFactory<SpecialTest,String>("region"));
                        specialTestPreviousTestsTestColumn.setCellValueFactory(new PropertyValueFactory<SpecialTest,String>("test"));
                        specialTestPreviousTestsResultColumn.setCellValueFactory(new PropertyValueFactory<SpecialTest,String>("result"));
                        specialTestPreviousTestsCommentsColumn.setCellValueFactory(new PropertyValueFactory<SpecialTest,String>("Comments"));
                    }
                }
            }
        }
    }

    public void romTestInitialize(){
        romExtentionChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romExtentionPainChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romFlexionChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romFlexionPainChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romLLFChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romLLFPainChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romRLFChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romRLFPainChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romLRChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romLRPainChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romRRChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");
        romRRPainChoiceBox.getItems().addAll("not relevant","99","90","80","70","60","50","40","30","20","10","0");

        romExtentionChoiceBox.setValue("not relevant");
        romExtentionPainChoiceBox.setValue("not relevant");
        romFlexionChoiceBox.setValue("not relevant");
        romFlexionPainChoiceBox.setValue("not relevant");
        romLLFChoiceBox.setValue("not relevant");
        romLLFPainChoiceBox.setValue("not relevant");
        romRLFChoiceBox.setValue("not relevant");
        romRLFPainChoiceBox.setValue("not relevant");
        romLRChoiceBox.setValue("not relevant");
        romLRPainChoiceBox.setValue("not relevant");
        romRRChoiceBox.setValue("not relevant");
        romRRPainChoiceBox.setValue("not relevant");

        romTestTotalList = new ArrayList<>();
        if(casesAvailable && caseRelatedExamList.size()!=0){
            for(int i=caseRelatedExamList.size()-1;i>=0;i--){
                List<Rom> tempList ;
                if((tempList= caseRelatedExamList.get(i).getRomTestList())!=null){

                    for(int j=tempList.size()-1;j>=0;j--){
                        romTestTotalList.add(tempList.get(j));
                        romPreviousResultsTableView.setItems(FXCollections.observableList(romTestTotalList));
                        romPreviousResultsDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
                        romPreviousResultsRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
                        romPreviousResultsExtentionTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getExtention())));
                        romPreviousResultsExtentionPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getExtentionPain())));
                        romPreviousResultsFlexionTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getFlexion())));
                        romPreviousResultsFlexionPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getFlexionPain())));
                        romPreviousResultsLLFTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getLlf())));
                        romPreviousResultsLLFPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getLlfPain())));
                        romPreviousResultsRLFTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getRlf())));
                        romPreviousResultsRLFPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getRlfPain())));
                        romPreviousResultsLRTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getLr())));
                        romPreviousResultsLRPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getLrPain())));
                        romPreviousResultsRRTotalColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getRr())));
                        romPreviousResultsRRPFColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getRrPain())));
                        romPreviousResultsTotalLossColumn.setCellValueFactory(c->new SimpleStringProperty(String.valueOf(c.getValue().getTotalLoss())));
                        romPreviousResultsTypeColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRomType()));
                        romPreviousResultsCommentsColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getComments()));
                    }
                }
            }
        }
    }

    public void musclePowerInitialize(){
        musclePowerPowerChoiceBox.getItems().addAll("not selected","99","90","80","70","60","50","40","30","20","10","0");
        musclePowerPowerChoiceBox.setValue("not selected");

        currentMusclePowerList = new ArrayList<>();

        if(casesAvailable && caseRelatedExamList.size()!=0) {
            List<MusclePower> previousMusclePowerList = new ArrayList<>();
            for (int i = caseRelatedExamList.size() - 1; i >= 0; i--) {
                List<MusclePower> tempList;
                if ((tempList = caseRelatedExamList.get(i).getMusclePowerList()) != null) {
                    for(int j=tempList.size()-1;j>=0;j--){
                        previousMusclePowerList.add(tempList.get(j));

                        musclePowerPreviousTableView.setItems(FXCollections.observableList(previousMusclePowerList));
                        musclePowerPreviousDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
                        musclePowerPreviousMuscleColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getMuscle()));
                        musclePowerPreviousRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
                        musclePowerPreviousPowerColumn.setCellValueFactory(c-> new SimpleStringProperty(String.valueOf(c.getValue().getPowerLevel())));
                        musclePowerPreviousCommentsColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getComments()));
                    }
                }
            }
        }
    }

    public void diagnosticStudyInitialize(){
        diagnosticStudiesFileNameLabel.setText("no file is selected");
        diagnosticStudiesTypeOfStudyChoiceBox.getItems().addAll("not selected","x ray","CR scan", "ECG", "Scanning report");
        diagnosticStudiesTypeOfStudyChoiceBox.setValue("not selected");

        diagnosticStudyTotalList = new ArrayList<>();

        if(casesAvailable && caseRelatedExamList.size()!=0) {
            for (int i = caseRelatedExamList.size() - 1; i >= 0; i--) {
                List<DiagnosticStudy> tempList;
                if ((tempList = caseRelatedExamList.get(i).getDiagnosticStudyList()) != null) {
                    for (int j = tempList.size() - 1; j >= 0; j--) {
                        diagnosticStudyTotalList.add(tempList.get(j));
                        diagnosticStudiesTableView.setItems(FXCollections.observableList(diagnosticStudyTotalList));
                        diagnosticStudiesDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getDateOfStudy()));
                        diagnosticStudiesRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
                        diagnosticStudiesTypeColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getStudyType()));
                        diagnosticStudiesImpressionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getImpression()));
                        diagnosticStudiesAttachmentColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getFileName()));
                    }
                }
            }
            diagnosticStudiesTableView.setRowFactory( tv -> {
                TableRow<DiagnosticStudy> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        DiagnosticStudy rowData = row.getItem();
                        Desktop dt = Desktop.getDesktop();
                        try {
                            dt.open(new File(rowData.getFileName()));
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null,"internal error no file in saved location");
                        }
                    }
                });
                return row ;
            });
        }

    }

    public void neurologicalStudyInitialize(){
        neurologicalStudyTotalList = new ArrayList<>();

        if(casesAvailable && caseRelatedExamList.size()!=0) {
            for (int i = caseRelatedExamList.size() - 1; i >= 0; i--) {
                List<NeurologicalStudy> tempList;
                if ((tempList = caseRelatedExamList.get(i).getNeurologicalStudyList()) != null) {
                    for (int j = tempList.size() - 1; j >= 0; j--) {
                        neurologicalStudyTotalList.add(tempList.get(j));
                        neurologicalStudiesTableView.setItems(FXCollections.observableList(neurologicalStudyTotalList));
                        neurologicalStudiesDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getDateOfStudy()));
                        neurologicalStudiesRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
                        neurologicalStudiesTypeColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getStudyType()));
                        neurologicalStudiesImpressionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getImpression()));
                        neurologicalStudiesAttachmentColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getFileName()));
                    }
                }
            }

            neurologicalStudiesTableView.setRowFactory( tv -> {
                TableRow<NeurologicalStudy> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                        NeurologicalStudy rowData = row.getItem();
                        Desktop dt = Desktop.getDesktop();
                        try {
                            dt.open(new File(rowData.getFileName()));
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null,"internal error no file in saved location");
                        }
                    }
                });
                return row ;
            });
        }
    }

    public void analysisInitialize(){
        analysisPatientConditionChoiceBox.getItems().addAll("not selected","resolved","marked improvement","moderate improvement",
                "slight improvement","no change","somewhat worse", "worse");
        analysisPatientProgressingChoiceBox.getItems().addAll("not selected","expected rate", "faster rate", "slower rate", "negative rate");
        analysisPrognosisChoiceBox.getItems().addAll("undetermined","poor","fair","good","excellent");
        analysisPatientConditionChoiceBox.setValue("not selected");
        analysisPatientProgressingChoiceBox.setValue("not selected");
        analysisPrognosisChoiceBox.setValue("undetermined");
        analysisTreatmentEffectiveUnableRadioBtn.setSelected(true);

        //setCellValueFactory(c-> new SimpleStringProperty(c.getValue().));
        List<Analysis> analysisTotalList = new ArrayList<>();
        if(casesAvailable && caseRelatedExamList.size()!=0) {
            for (int i = caseRelatedExamList.size() - 1; i >= 0; i--) {
                Analysis analysis;
                if((analysis= caseRelatedExamList.get(i).getAnalysis())!=null){
                    analysisTotalList.add(analysis);
                    analysisTableView.setItems(FXCollections.observableList(analysisTotalList));
                    analysisDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
                    analysisConditionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getPatientCondition()));
                    analysisProgressColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getPatientProgress()));

                    if(analysis.getEffectiveness()==1){
                        analysisEffectivenessColumn.setCellValueFactory(c-> new SimpleStringProperty("no"));
                    }else if(analysis.getEffectiveness()==2){
                        analysisEffectivenessColumn.setCellValueFactory(c-> new SimpleStringProperty("yes"));
                    }else{
                        analysisEffectivenessColumn.setCellValueFactory(c-> new SimpleStringProperty("unable"));
                    }
                    analysisPrognosisColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getPrognosis()));
                    analysisCommentsColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getComments()));
                }
            }
        }
    }

    public void treatmentPlanInitialize(){
        treatmentFrequencyChoiceBox.getItems().addAll("none","as needed","daily","three times weekly","five times weekly", "once weekly" ,"twice weekly","twice monthly","monthly");
        treatmentDurationChoiceBox.getItems().addAll("none","indefinitely","one week","two weeks", "three weeks","one month","six weeks","two months", "three months","one year");
        treatmentFrequencyChoiceBox.setValue("none");
        treatmentDurationChoiceBox.setValue("none");
    }

    public void subjectiveInitialize(){
        subjectiveRegionComboBox.getItems().addAll("head","neck","chest","Shoulder L", "shoulder R", "upper abdomen",
                "lower abdomen", "back", "arm L", "arm R", "palm L", "palm R","hip joint","Leg L", "leg R", "ankle L",
                "ankle R", "foot L", "foot R");

        subjectiveFrequencyComboBox.getItems().addAll("always","once an hour","every six hours","daily", "more than once a week", "other");
        subjectiveSeverityComboBox.getItems().addAll("high","high-medium", "medium", "low-medium","low");

        List<Subjective> subjectiveTotalList = new ArrayList<>();
        if(casesAvailable && caseRelatedExamList.size()!=0) {
            for (int i = caseRelatedExamList.size() - 1; i >= 0; i--) {
                Subjective subjective;
                if((subjective= caseRelatedExamList.get(i).getSubjective())!=null){
                    subjectiveTotalList.add(subjective);
                    subjectiveTableView.setItems(FXCollections.observableList(subjectiveTotalList));
                    subjectiveDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
                    subjectiveRegionColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getRegion()));
                    subjectiveLocationColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getLocation()));
                    subjectiveSymptomsColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getSymptoms()));
                    subjectiveSeverityColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getSeverity()));
                }
            }
        }
    }

    public void personalDetailsInitialize(){
        patientProfileNameFld.setText(patient.getName());

        int gender = patient.getGender();
        if(gender == 1){
            patientProfileGenderFld.setText("Male");
        }else{
            patientProfileGenderFld.setText("Female");
        }

        patientProfileNicNoFld.setText(String.valueOf(patient.getNicNo()));
        patientProfileBhtNoFld.setText(String.valueOf(patient.getBhtNo()));
        patientProfileContactNoFld.setText(String.valueOf(patient.getContactNo()));
        patientProfileDobFld.setText(patient.getDob());
        patientProfileCurrentPatientCaseFld.setText(patientCase.getCaseName()+ "  created date: "+patientCase.getCreatedDate());
        patientProfileAddressArea.setText(patient.getAddress());

        patientProfileNameFld.setEditable(false);
        patientProfileGenderFld.setEditable(false);
        patientProfileNicNoFld.setEditable(false);
        patientProfileBhtNoFld.setEditable(false);
        patientProfileContactNoFld.setEditable(false);
        patientProfileDobFld.setEditable(false);
        patientProfileCurrentPatientCaseFld.setEditable(false);
        patientProfileAddressArea.setEditable(false);

        List<PatientCase> tempCaseList = patient.getPatientCaseList();
        Collections.reverse(tempCaseList);
        ObservableList<PatientCase> oList = FXCollections.observableList(tempCaseList);
        patientProfilePatientCaseListView.setItems(oList);

        patientProfilePatientCaseListView.setCellFactory(new Callback<ListView<PatientCase>, ListCell<PatientCase>>(){

            public ListCell<PatientCase> call(ListView<PatientCase> o) {

                ListCell<PatientCase> cell = new ListCell<PatientCase>(){

                    @Override
                    protected void updateItem(PatientCase pcase, boolean bln) {
                        super.updateItem(pcase, bln);
                        if (pcase != null) {
                            setText(pcase.getCaseName()+ "  created date: "+pcase.getCreatedDate());
                        }
                    }
                };
                return cell;
            }
        });

    }
}
