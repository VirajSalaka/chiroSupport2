package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.patientProfile.PatientQueueItem;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

        addPeople(new Patient("sarath",123232,234343,"03-03-2014",1,"adfsdaf",1223232));
        addPeople(new Patient("sunil",234343,234343,"03-03-2014",1,"adfsdaf",1223232));
        addPeople(new Patient("pasindu",343232,234343,"03-03-2014",1,"adfsdaf",1223232));
        addPeople(new Patient("sarah",156232,234343,"03-03-2014",1,"adfsdaf",1223232));
        addPeople(new Patient("upul",983232,234343,"03-03-2014",1,"adfsdaf",1223232));

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


        newPatientObservableList = FXCollections.observableList(newPatientList);
        newPatientListView.setItems(newPatientObservableList);

        newPatientListView.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>(){


            public ListCell<Patient> call(ListView<Patient> p) {

                ListCell<Patient> cell = new ListCell<Patient>(){

                    @Override
                    protected void updateItem(Patient patient, boolean bln) {
                        super.updateItem(patient, bln);
                        if (patient != null) {
                            setText(patient.getName() + ":" + patient.getNicNo());
                        }
                    }

                };

                return cell;
            }
        });

        newPatientListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {


            public void changed(ObservableValue<? extends Patient> observable, Patient oldValue, Patient newValue) {
                // Your action here
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("Ooops, there was an error!"+newValue.getName());


                alert.showAndWait();
                System.out.println("Selected item: " );
            }
        });
    }
}
