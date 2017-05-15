package com.mycompany.chiroSupport.sample;

import com.mycompany.chiroSupport.employee.Employee;
import com.mycompany.chiroSupport.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.hibernate.Session;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Salaka on 5/16/2017.
 */
public class UserRegistrationController implements Initializable {

    @FXML
    private TextField user_idFld;
    @FXML
    private TextField nameFld;
    @FXML
    private TextField passwordFld;
    @FXML
    private ChoiceBox<String> roleFld;

    private final String salt = "salt";

    public void registerUser(MouseEvent mouseEvent) throws Exception {
        String user_id =user_idFld.getText();
        String name = nameFld.getText();
        String password = passwordFld.getText();
        String role = roleFld.getValue();

        Pattern p = Pattern.compile("^[ A-Za-z]+$");
        Matcher m = p.matcher(name);
        boolean b = m.matches();

        if(user_id.length()!=7){
            JOptionPane.showMessageDialog(null, "User ID length is not 7 check again", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(name.length()<5 || !b ){
            JOptionPane.showMessageDialog(null, "User name is not valid, check again", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(password.length()==0){
            JOptionPane.showMessageDialog(null, "Password is not added", "Error", JOptionPane.ERROR_MESSAGE);
        }else if(role.equals("not selected")){
            JOptionPane.showMessageDialog(null, "Select the role", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you ok with this?");
            alert.setContentText("User_id : " + user_id+ "\n Name : "+ name + "\n role : " + role);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Employee employee = new Employee();
                employee.setName(name);
                employee.setUser_id(user_id);
                employee.setPassword(MD5hash.getMD5Checksum(password+salt));

                if(role.equals("receptionist")){
                    employee.setRole(1);
                }else if(role.equals("physiotherapist")){
                    employee.setRole(2);
                }else{
                    employee.setRole(3);
                }



                Session session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
                try {
                    session.beginTransaction();
                    session.save(employee);
                    session.getTransaction().commit();
                } finally {
                    session.close();
                }
            } else {

            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleFld.getItems().addAll("not selected", "receptionist","physiotherapist","admin");
        roleFld.setValue("not selected");
    }
}
