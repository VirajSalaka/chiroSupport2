package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.patientCase.PatientCase;
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


import javax.swing.*;
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
    private ListView<Patient> newPatientListView;

    @FXML
    private ListView<Patient> completedPatientListView;

    @FXML
    private ListView<Patient> currentPatientListView;

    private ObservableList<Patient> newPatientObservableList;
    private List<Patient> newPatientList = new ArrayList<Patient>();

    private ObservableList<Patient> currentPatientObservableList;
    private List<Patient> currentPatientList = new ArrayList<Patient>();

    private ObservableList<Patient> completedPatientObservableList;
    private List<Patient> completedPatientList = new ArrayList<Patient>();



    public void initialize(URL location, ResourceBundle resources) {


        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from com.mycompany.chiroSupport.patientProfile.PatientQueueItem");
            List<PatientQueueItem> list = query.list();

            for (PatientQueueItem pqi : list) {
                PatientQueueItem item = (PatientQueueItem) pqi;
                Patient patient = item.getPatient();
                if(item.getStatus()==0){
                    newPatientList.add(patient);
                }else if(item.getStatus()==1){
                    currentPatientList.add(patient);
                }else{
                    completedPatientList.add(patient);
                }
            }
            session.getTransaction().commit();
        }finally{
            session.close();
        }

        setListView(newPatientObservableList,newPatientList,newPatientListView);
        setListView(currentPatientObservableList,currentPatientList,currentPatientListView);
        setListView(completedPatientObservableList,completedPatientList,completedPatientListView);







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

        ButtonType buttonTypeOne = new ButtonType("new patient case");
        ButtonType buttonTypeTwo = new ButtonType("existing patient case");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            System.out.println("one");

        } else if (result.get() == buttonTypeTwo) {
            System.out.println("two");
            Node node=(Node) mouseEvent.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            Parent root = null;/* Exception */
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/patientDashboard.fxml"));
                root = loader.load();
                PatientDashboardController controller = loader.getController();
                Patient patient =newPatientListView.getSelectionModel().getSelectedItem();
                List<PatientCase> tempList= patient.getPatientCaseList();
                if(tempList.size()!=0){
                    PatientCase patientCase = tempList.get(tempList.size()-1);
                    controller.setPatient(patient);
                    controller.setPatientCase(patientCase);

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    JOptionPane.showMessageDialog(null, "No patient cases exist", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }  else {

        }
        System.out.println("Selected item: " );
    }

    public void setListView(ObservableList<Patient> oList,List<Patient> list,ListView<Patient> listView){
        oList = FXCollections.observableList(list);
        listView.setItems(oList);

        listView.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>(){


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
    }

}
