package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.patientCase.PatientCase;
import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Salaka on 5/15/2017.
 */
public class AddNewCaseController implements Initializable {

    private Patient patient;
    private PatientCase patientCase;

    @FXML
    private TextField patientCaseNameFld;
    @FXML
    private TextField patientCaseDateFld;

    public void createNewPatientCase(MouseEvent mouseEvent) {
        String casename = patientCaseNameFld.getText();

        if(casename.length()>0) {
            patientCase = new PatientCase();
            patientCase.setPatient(patient);
            patientCase.setCaseName(casename);
            patientCase.setCreatedDate(String.valueOf(LocalDate.now()));

            Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
            try {
                session.beginTransaction();
                session.save(patientCase);
                session.getTransaction().commit();
            } finally {
                session.close();
            }

            Node node=(Node) mouseEvent.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            Parent root = null;
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/patientDashboard.fxml"));
                root = loader.load();
                PatientDashboardController controller = loader.getController();
                controller.setPatient(patient);
                controller.setPatientCase(patientCase);

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch (IOException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        }else{
            JOptionPane.showMessageDialog(null, "Case Name is not entered", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientCaseDateFld.setText(String.valueOf(LocalDate.now()));
        patientCaseDateFld.setEditable(false);
    }
}
