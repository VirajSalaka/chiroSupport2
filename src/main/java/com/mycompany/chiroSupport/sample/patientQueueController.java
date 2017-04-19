package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.patientProfile.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

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

    public List<Patient> newpatientList = new ArrayList<Patient>();

    public void addPeople(Patient p){

        newpatientList.add(p);
    }

    public void initialize(URL location, ResourceBundle resources) {

        addPeople(new Patient("sarath",123232,234343,"03-03-2014",1,"adfsdaf",1223232));
        addPeople(new Patient("sunil",234343,234343,"03-03-2014",1,"adfsdaf",1223232));
        addPeople(new Patient("pasindu",343232,234343,"03-03-2014",1,"adfsdaf",1223232));
        addPeople(new Patient("sarah",156232,234343,"03-03-2014",1,"adfsdaf",1223232));
        addPeople(new Patient("upul",983232,234343,"03-03-2014",1,"adfsdaf",1223232));
        newPatientObservableList = FXCollections.observableList(newpatientList);
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
    }
}
