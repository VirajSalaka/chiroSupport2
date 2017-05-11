package com.mycompany.chiroSupport.sample;


import com.mycompany.chiroSupport.patientCase.VitalsReport;
import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.patientProfile.PatientQueueItem;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

public class ReceptionistDataEntryController implements Initializable {

    private int gender;
    private ObservableList<Patient> entries = FXCollections.observableArrayList();
    private List<Patient> patientList = new ArrayList<Patient>();
    private int selection = 0;

    //registration
    @FXML
    public AnchorPane registrationTab;
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

    //Search option
    @FXML
    private TextField searchNameFld;
    @FXML
    private ChoiceBox<String> searchFieldSelectionChoiceBox;
    @FXML
    private ListView<Patient> searchPatientSuggestionsList;



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
        PatientQueueItem patientQueueItem = new PatientQueueItem(patient);
        Session session = null;
        try {
            // to save patient object in the database
            session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();

            //start transaction
            session.beginTransaction();

            //Save the Model object
            session.save(patient);
            session.save(patientQueueItem);

            //Commit transaction
            session.getTransaction().commit();

            //terminate session factory, otherwise program won't end


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!"+e.toString());
            alert.showAndWait();
        }finally{
            if(session != null){
                session.close();
            }
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
        searchFieldSelectionChoiceBox.getItems().addAll("name","nic No");
        searchFieldSelectionChoiceBox.setValue("name");
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();

        try {
            session.beginTransaction();
            Query query = session.createQuery("from com.mycompany.chiroSupport.patientProfile.Patient");

            List<Patient> list = query.list();
            for (Patient p : list) {
                Patient item = (Patient) p;
                entries.add(item);
            }
            session.getTransaction().commit();
        }finally {
            session.close();
        }
        setObjectToListView(searchPatientSuggestionsList,entries);

        searchFieldSelectionChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        selection = newValue.intValue();
                    }
                }
        );

        searchNameFld.textProperty().addListener(
                new ChangeListener() {
                    public void changed(ObservableValue observable,
                                        Object oldVal, Object newVal) {
                        if(selection==0){
                            handleSearchByName((String)oldVal, (String)newVal);
                        }else{
                            handleSearchByNicNo((String)oldVal, (String)newVal);
                        }

                    }
                });

    }

    public void handleSearchByName(String oldVal, String newVal) {
        // If the number of characters in the text box is less than last time
        // it must be because the user pressed delete
        if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
            // Restore the lists original set of entries
            // and start from the beginning
            setObjectToListView(searchPatientSuggestionsList,entries);
        }

        // Break out all of the parts of the search text
        // by splitting on white space
        String[] parts = newVal.toUpperCase().split(" ");

        // Filter out the entries that don't contain the entered text
        ObservableList<Patient> subentries = FXCollections.observableArrayList();
        for ( Patient entry: searchPatientSuggestionsList.getItems() ) {
            boolean match = true;
            String entryText = entry.getName();
            for ( String part: parts ) {
                // The entry needs to contain all portions of the
                // search string *but* in any order
                if ( ! entryText.toUpperCase().contains(part) ) {
                    match = false;
                    break;
                }
            }
            if ( match ) {
                subentries.add(entry);
            }
        }
        setObjectToListView(searchPatientSuggestionsList,subentries);
    }

    public void handleSearchByNicNo(String oldVal, String newVal) {
        // If the number of characters in the text box is less than last time
        // it must be because the user pressed delete
        if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
            // Restore the lists original set of entries
            // and start from the beginning
            setObjectToListView(searchPatientSuggestionsList,entries);
        }

        // Break out all of the parts of the search text
        // by splitting on white space
        String[] parts = newVal.toUpperCase().split(" ");

        // Filter out the entries that don't contain the entered text
        ObservableList<Patient> subentries = FXCollections.observableArrayList();
        for ( Patient entry: searchPatientSuggestionsList.getItems() ) {
            boolean match = true;
            String entryText = String.valueOf(entry.getNicNo());
            for ( String part: parts ) {
                // The entry needs to contain all portions of the
                // search string *but* in any order
                if ( ! entryText.toUpperCase().contains(part) ) {
                    match = false;
                    break;
                }
            }
            if ( match ) {
                subentries.add(entry);
            }
        }
        setObjectToListView(searchPatientSuggestionsList,subentries);
    }

    public void setObjectToListView(ListView<Patient> l, ObservableList<Patient> observableList){
        l.setItems(observableList);
        l.setCellFactory(new Callback<ListView<Patient>, ListCell<Patient>>(){
            public ListCell<Patient> call(ListView<Patient> p) {
                ListCell<Patient> cell = new ListCell<Patient>(){

                    @Override
                    protected void updateItem(Patient t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + ":" + t.getNicNo());
                            StringBuilder sb = new StringBuilder();
                            sb.append("name :");
                            sb.append(t.getName());
                            sb.append("\nNic No : ");
                            sb.append(t.getNicNo());
                            sb.append("\ndate of birth :");
                            sb.append(t.getDob());
                            sb.append("\naddress : ");
                            sb.append(t.getAddress());
                            this.setTooltip(new Tooltip(sb.toString()));
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void searchPatientListViewClicked(MouseEvent mouseEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add patient to Queue");
        alert.setHeaderText("");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeAddToQueue = new ButtonType("Add to Queue");
        ButtonType buttonTypeTwo = new ButtonType("View profile");

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeAddToQueue, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeAddToQueue){

            Patient patient = searchPatientSuggestionsList.getSelectionModel().getSelectedItem();
            PatientQueueItem patientQueueItem = new PatientQueueItem(patient);
            Session session = null;
            try {
                // to save patient object in the database
                session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();

                //start transaction
                session.beginTransaction();

                //Save the Model object
                session.save(patientQueueItem);

                //Commit transaction
                session.getTransaction().commit();

                //terminate session factory, otherwise program won't end


            } catch (Exception e) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("Look, an Error Dialog");
                alert2.setContentText("Ooops, there was an error!"+e.toString());
                alert2.showAndWait();
            }finally{
                if(session != null){
                    session.close();
                }
            }

            Node node=(Node) mouseEvent.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            Parent root = null;
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
        } else {

        }
        System.out.println("Selected item: " );
    }

}
