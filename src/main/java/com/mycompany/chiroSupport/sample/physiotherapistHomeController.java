package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.employee.Employee;
import com.mycompany.chiroSupport.patientProfile.Appointment;
import com.mycompany.chiroSupport.util.HibernateUtil;
import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Salaka on 5/15/2017.
 */
public class physiotherapistHomeController implements Initializable{

    public void appointmentBtnClicked(MouseEvent mouseEvent) {

        try {
            Node node=(Node) mouseEvent.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 800, 600);

            DatePickerSkin datePickerSkin;
            datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
            Node popupContent = datePickerSkin.getPopupContent();
            DatePickerContent pop = (DatePickerContent)datePickerSkin.getPopupContent();
            ListView<String> appointmentListView = new ListView<String>();
            Label notVisible = new Label();
            notVisible.setVisible(false);

            List<DateCell> dateCells = getAllDateCells(pop);

            for (DateCell cell : dateCells)
            {
                cell.addEventHandler(
                        MouseEvent.MOUSE_PRESSED,(e)->
                        {
                            addItemsToListView(cell.getItem().toString(),appointmentListView);
                            notVisible.setText(String.valueOf(cell.getItem()));
                        }
                );
            }

            AnchorPane belowPart  = new AnchorPane();
            Label appointmentLabel = new Label("Appointments");
            Button addNewAppointmentBtn = new Button("add New");

            addNewAppointmentBtn.setOnAction(e -> System.out.println("clickdddd"));

            /*addNewAppointmentBtn.setOnAction((event) -> {

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Appointment");
                dialog.setHeaderText("Add new appointment");
                dialog.setContentText("write a note containing patient info and other details");


                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    if(result.get().length()>0) {
                        Appointment app = new Appointment();
                        app.setDate(notVisible.getText());
                        app.setDescription(result.get());
                        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
                        try {
                            session.beginTransaction();
                            session.save(app);
                            session.getTransaction().commit();
                        } finally {
                            session.close();
                        }
                    }
                }

            });*/

            Button homeBtn = new Button("Home");
            appointmentLabel.setTranslateX(-20);
            addNewAppointmentBtn.setTranslateX(-20);
            addNewAppointmentBtn.setTranslateY(50);
            homeBtn.setTranslateX(-20);
            homeBtn.setTranslateY(100);

            appointmentListView.setPrefSize(600,400);

            belowPart.getChildren().addAll(appointmentLabel,addNewAppointmentBtn,homeBtn);

            AnchorPane leftPart = new AnchorPane();
            leftPart.getChildren().addAll(appointmentListView);

            root.setTop(popupContent);
            root.setRight(belowPart);
            root.setBottom(leftPart);

            root.getBottom().prefWidth(600);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<DateCell> getAllDateCells(DatePickerContent content)
    {
        List<DateCell> result = new ArrayList<>();

        for (Node n : content.getChildren())
        {
            System.out.println("node " + n + n.getClass());
            if (n instanceof GridPane)
            {
                GridPane grid = (GridPane) n;
                for (Node gChild : grid.getChildren())
                {
                    System.out.println("grid node: " + gChild + gChild.getClass());
                    if (gChild instanceof DateCell)
                    {
                        result.add((DateCell) gChild);
                    }
                }
            }
        }

        return result;
    }

    public void addItemsToListView(String date,ListView<String> listView){
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        Query query = null;
        boolean flag = false;
        List<String> appointmentList=new ArrayList<>();
        List list;
        try {
            session.beginTransaction();
            query = session.createQuery("from com.mycompany.chiroSupport.patientProfile.Appointment where date = :date");
            query.setParameter("date",date);
            if((list = query.list())!=null){
                if(list.size()!=0){
                    flag = true;
                }
            }
            session.getTransaction().commit();
        }finally {
            session.close();
        }
        if(flag){
            for(Object o:list){
                appointmentList.add(((Appointment)o).getDescription());
            }
            listView.setItems(FXCollections.observableList(appointmentList));
        }
    }


    public void patientQueueClicked(MouseEvent mouseEvent) {
        Node node=(Node) mouseEvent.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;/* Exception */
        try {
            root = FXMLLoader.load(getClass().getResource("/patientQueue.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void patientRegistrationClicked(MouseEvent mouseEvent) {
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        session.close();
    }
}
