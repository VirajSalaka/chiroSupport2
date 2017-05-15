package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Salaka on 5/15/2017.
 */
public class physiotherapistHomeController implements Initializable{

    public void appointmentBtnClicked(MouseEvent mouseEvent) {
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
