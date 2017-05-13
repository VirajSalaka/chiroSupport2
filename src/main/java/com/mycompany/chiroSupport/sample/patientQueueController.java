package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.patientProfile.PatientQueueItem;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Salaka on 4/19/2017.
 */
public class patientQueueController implements Initializable{

    @FXML
    public ListView<Patient> newPatientListView;

    @FXML
    public ListView<String> completedPatientListView;

    @FXML
    public ListView<String> currentPatientListView;

    public ObservableList<Patient> newPatientObservableList;

    public List<Patient> newPatientList = new ArrayList<Patient>();

    public void addPeople(Patient p){

        newPatientList.add(p);
    }



    public void initialize(URL location, ResourceBundle resources) {

//        addPeople(new Patient("sarath",123232,234343,"03-03-2014",1,"adfsdaf",1223232));
//        addPeople(new Patient("sunil",234343,234343,"03-03-2014",1,"adfsdaf",1223232));
//        addPeople(new Patient("pasindu",343232,234343,"03-03-2014",1,"adfsdaf",1223232));
//        addPeople(new Patient("sarah",156232,234343,"03-03-2014",1,"adfsdaf",1223232));
//        addPeople(new Patient("upul",983232,234343,"03-03-2014",1,"adfsdaf",1223232));

        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();

        session.beginTransaction();

        Query query = session.createQuery("from com.mycompany.chiroSupport.patientProfile.PatientQueueItem");



        List<PatientQueueItem> list = query.list();

        for (PatientQueueItem pqi:list){
            PatientQueueItem item = (PatientQueueItem)pqi;
            Patient p = item.getPatient();
            System.out.println(p.getName());
            addPeople(p);
        }

                session.getTransaction().commit();



        newPatientObservableList = FXCollections.observableList(newPatientList);
        newPatientListView.setItems(newPatientObservableList);

        newPatientListView.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>(){


            public ListCell<Patient> call(ListView<Patient> p) {

                ListCell<Patient> cell = new ListCell<Patient>(){

                    @Override
                    protected void updateItem(Patient patient, boolean bln) {
                        super.updateItem(patient, bln);
                        if (patient != null) {
                            setText(patient.getName() + " : " + patient.getNicNo());


                        }
                    }

                };


                return cell;
            }
        });





///////////////because found better way using mouseEvent

        /*newPatientListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {


            public void changed(ObservableValue<? extends Patient> observable, Patient oldValue, Patient newValue) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog with Custom Actions");
                alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
                alert.setContentText("Choose your option.");

                ButtonType buttonTypeOne = new ButtonType("One");
                ButtonType buttonTypeTwo = new ButtonType("Two");
                ButtonType buttonTypeThree = new ButtonType("Three");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne){
                    System.out.println("one");



                } else if (result.get() == buttonTypeTwo) {

                    System.out.println("two");
                } else if (result.get() == buttonTypeThree) {

                    System.out.println("three");
                } else {

                }
                System.out.println("Selected item: " );
            }
        });*/
    }

    public void newPatientListViewClicked(MouseEvent mouseEvent) {
        System.out.println(newPatientListView.getSelectionModel().getSelectedItem().getName());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog with Custom Actions");
        alert.setHeaderText("Look, a Confirmation Dialog with Custom Actions");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("One");
        ButtonType buttonTypeTwo = new ButtonType("Two");
        ButtonType buttonTypeThree = new ButtonType("Three");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            System.out.println("one");
            Node node=(Node) mouseEvent.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            Parent root = null;/* Exception */
            try {
                root = FXMLLoader.load(getClass().getResource("/receptionistDataEntry.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        } else if (result.get() == buttonTypeTwo) {

            System.out.println("two");
        } else if (result.get() == buttonTypeThree) {

            System.out.println("three");
        } else {

        }
        System.out.println("Selected item: " );
    }

}
