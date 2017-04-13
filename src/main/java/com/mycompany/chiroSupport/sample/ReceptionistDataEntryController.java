package com.mycompany.chiroSupport.sample;


import com.mycompany.chiroSupport.patientCase.VitalsReport;
import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;


import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistDataEntryController implements Initializable {

    @FXML
    public DatePicker dobfld;

    @FXML
    public TextField patientNamefld;

    @FXML
    public TextField bhtNofld;

    @FXML
    public TextField nicNofld;

    @FXML
    public TextField contactNofld;

    @FXML
    public TextArea addressarea;

    @FXML
    public RadioButton maleRadiobtn;

    @FXML
    public RadioButton femaleRadiobtn;

    @FXML
    private int gender;


    /*
    when register button in receptionistDataEntry triggers, all the values in the input fields are passed to the model
     */
    public void registerbtnClicked(MouseEvent mouseEvent) {


        String name = patientNamefld.getText();
        int bhtNo = Integer.valueOf(bhtNofld.getText());
        int nicNo = Integer.valueOf(nicNofld.getText());
        int contactNo = Integer.valueOf(contactNofld.getText());
        String address = addressarea.getText();
        String dob = String.valueOf(dobfld.getValue());

        Patient patient  = new Patient(name,nicNo,bhtNo,dob,gender,address,contactNo);
        try {
            // to save patient object in the database
            Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();

            //start transaction
            session.beginTransaction();

            //Save the Model object
            session.save(patient);

            //Commit transaction
            session.getTransaction().commit();

            System.out.println("Employee ID="+patient.getRefNo());





            //terminate session factory, otherwise program won't end

            HibernateUtil.getSessionAnnotationFactory().close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!"+e.toString());


            alert.showAndWait();
        }


    }

    /*
    gender changes according to selected radio button
     */
    public void maleRadiobtnClicked(MouseEvent mouseEvent) {
        gender = 1;
    }

    /*
    gender changes according to selected radio button
     */
    public void femaleRadiobtnClicked(MouseEvent mouseEvent) {
        gender = 2;
    }


    public void initialize(URL location, ResourceBundle resources) {

    }
}
