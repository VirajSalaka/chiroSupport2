package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.employee.Employee;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Salaka on 3/23/2017.
 */
public class loginController implements Initializable{
    @FXML
    private TextField usernameFld;
    @FXML
    private PasswordField passwordFld;
    @FXML
    private Label indicateLabel;

    public void userLogin(MouseEvent mouseEvent) throws Exception {
        Boolean authenticated = false;
        Employee employee = null;
        indicateLabel.setVisible(true);
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        Query query = null;
        List list;
        try {
            session.beginTransaction();
            query = session.createQuery("from com.mycompany.chiroSupport.employee.Employee where user_id = :userid AND password = :pwd");
            query.setParameter("userid", usernameFld.getText());
            query.setParameter("pwd",MD5hash.getMD5Checksum(passwordFld.getText()+"salt"));
            if((list = query.list())!=null){
                if(list.size()!=0){
                    authenticated = true;
                    employee =(Employee) list.get(0);
                }
            }
            session.getTransaction().commit();
        }finally {
            session.close();
        }

        if(authenticated){
            JOptionPane.showMessageDialog (null, "Login Successful", "Login", JOptionPane.INFORMATION_MESSAGE);
            if(employee.getRole()==1){
                Node node=(Node) mouseEvent.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                Parent root = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/receptionistDataEntry.fxml"));
                    root = loader.load();

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }else if(employee.getRole()==2){
                Node node=(Node) mouseEvent.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                Parent root = null;
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/physiotherapistHome.fxml"));
                    root = loader.load();

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }else{
                Node node=(Node) mouseEvent.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                Parent root = null;
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/userRegistration.fxml"));
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        }else{
            JOptionPane.showMessageDialog (null, "Login Failed", "Login", JOptionPane.ERROR_MESSAGE);
        }
        indicateLabel.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indicateLabel.setVisible(false);
        Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        session.close();
    }
}
