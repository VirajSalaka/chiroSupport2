package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.patientCase.*;
import com.mycompany.chiroSupport.patientCase.objective.*;
import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.patientProfile.PatientQueueItem;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private List<Examination> caseRelatedExamList;
    private File diagnosticStudySourceFile;
    private int diagnosticStudyCount = 0;
    private File neurologicalStudySourceFile;
    private int neurologicalStudyCount = 0;

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

    //Muscle - Power
    @FXML
    private TextField musclePowerRegionFld;
    @FXML
    private TextField musclePowerMuscleFld;
    @FXML
    private ChoiceBox<String> musclePowerPowerChoiceBox;
    @FXML
    private TextField musclePowerCommentsFld;

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
        }finally {
            session.close();
        }


    }

    public void SubjectiveSave(MouseEvent mouseEvent) {


        String complaint = subjectiveComplaintFld.getText();
        String region = subjectiveRegionComboBox.getValue();
        String location = subjectiveLocationFld.getText();
        String frequency = subjectiveFrequencyComboBox.getValue();
        String severity = subjectiveSeverityComboBox.getValue();
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

        if (frequency.length()>0){

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

        session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();

        try {
            Transaction t = session.beginTransaction();

            Query query = session.createQuery("from com.mycompany.chiroSupport.patientCase.PatientCase");
            List<PatientCase> list = query.list();
            patientCase = list.get(0);

            if(examination == null){
                examination = new Examination(patientCase);
                examination.setDate("2020-11-11");
                session.save(examination);
            }
            t.commit();
        }finally {
            session.close();
        }

        patient = patientCase.getPatient();
        caseRelatedExamList = patientCase.getExamList();

        subjectiveRegionComboBox.getItems().addAll("head","neck","chest","Shoulder L", "shoulder R", "upper abdomen",
                "lower abdomen", "back", "arm L", "arm R", "palm L", "palm R","hip joint","Leg L", "leg R", "ankle L",
                "ankle R", "foot L", "foot R");

        subjectiveFrequencyComboBox.getItems().addAll("always","once an hour","every six hours","daily", "more than once a week", "other");
        subjectiveSeverityComboBox.getItems().addAll("high","high-medium", "medium", "low-medium","low");

        addPreviousObservation();
        addPreviousPalpation();
        specialTestIntialize();

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

        musclePowerPowerChoiceBox.getItems().addAll("not selected","99","90","80","70","60","50","40","30","20","10","0");
        musclePowerPowerChoiceBox.setValue("not selected");

        diagnosticStudiesFileNameLabel.setText("no file is selected");
        diagnosticStudiesTypeOfStudyChoiceBox.getItems().addAll("not selected","x ray","CR scan", "ECG", "Scanning report");
        diagnosticStudiesTypeOfStudyChoiceBox.setValue("not selected");

        analysisPatientConditionChoiceBox.getItems().addAll("not selected","resolved","marked improvement","moderate improvement",
                "slight improvement","no change","somewhat worse", "worse");
        analysisPatientProgressingChoiceBox.getItems().addAll("not selected","expected rate", "faster rate", "slower rate", "negative rate");
        analysisPrognosisChoiceBox.getItems().addAll("undetermined","poor","fair","good","excellent");
        analysisPatientConditionChoiceBox.setValue("not selected");
        analysisPatientProgressingChoiceBox.setValue("not selected");
        analysisPrognosisChoiceBox.setValue("undetermined");
        analysisTreatmentEffectiveUnableRadioBtn.setSelected(true);

        treatmentFrequencyChoiceBox.getItems().addAll("none","as needed","daily","three times weekly","five times weekly", "once weekly" ,"twice weekly","twice monthly","monthly");
        treatmentDurationChoiceBox.getItems().addAll("none","indefinitely","one week","two weeks", "three weeks","one month","six weeks","two months", "three months","one year");
        treatmentFrequencyChoiceBox.setValue("none");
        treatmentDurationChoiceBox.setValue("none");
        //start transaction

    }

    public void saveObjectInDatabase(Object obj){
        session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
            neurologicalStudyCount++;
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
        List localObservationList = generalSelectQuery("patientCase.objective.Observation");

        if(localObservationList.size()!=0){
            Observation lastObservation = (Observation)localObservationList.get(localObservationList.size()-1);

            if (lastObservation.getExamination().getPatientCase().equals(patientCase)){
                recentObservationArea.setText(lastObservation.getDescription());
                recentObservationDateFld.setText(lastObservation.getExamination().getDate());
            }

            List<Observation> list = new ArrayList<Observation>();
            for(int i= localObservationList.size()-1; i>=0; i--){
                Observation o = (Observation)localObservationList.get(i);
                list.add(o);
//            if(o.getExamination().getPatientCase().equals(patientCase)){
//                list.add(o);
//            }else{
//                break;
//            }
            }

            ObservableList<Observation> oList = FXCollections.observableList(list);
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
        List localPalpationList = generalSelectQuery("patientCase.objective.Palpation");

        if(localPalpationList.size() != 0){
            Palpation lastPalpation = (Palpation) localPalpationList.get(localPalpationList.size()-1);

            if (lastPalpation.getExamination().getPatientCase().equals(patientCase)){
                recentPalpationArea.setText(lastPalpation.getDescription());
                recentPalpationDateFld.setText(lastPalpation.getExamination().getDate());
            }

            List<Palpation> list = new ArrayList<Palpation>();
            for(int i= localPalpationList.size()-1; i>=0; i--){
                Palpation p = (Palpation)localPalpationList.get(i);
                list.add(p);
//            if(p.getExamination().getPatientCase().equals(patientCase)){
//                list.add(p);
//            }else{
//                break;
//            }
            }

            ObservableList<Palpation> oList = FXCollections.observableList(list);
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
        if(caseRelatedExamList.size()!=0){
            for(int i=caseRelatedExamList.size()-1;i>=0;i--){
                List<SpecialTest> tempList = caseRelatedExamList.get(i).getSpecialTestList();
                ObservableList<SpecialTest> oList = FXCollections.observableList(tempList);

                for(int j=tempList.size()-1;j>=0;j--){
                    SpecialTest st = tempList.get(j);
                    System.out.println(st.getRegion());
                    specialTestPreviousTestsDateColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getExamination().getDate()));
                    specialTestPreviousTestsRegionColumn.setCellValueFactory(new PropertyValueFactory<SpecialTest,String>("region"));
                    specialTestPreviousTestsTestColumn.setCellValueFactory(new PropertyValueFactory<SpecialTest,String>("test"));
                    specialTestPreviousTestsResultColumn.setCellValueFactory(new PropertyValueFactory<SpecialTest,String>("result"));
                    specialTestPreviousTestsCommentsColumn.setCellValueFactory(new PropertyValueFactory<SpecialTest,String>("Comments"));
                    specialTestPreviousTestsTableView.setItems(oList);
                }

            }
        }


    }
}
