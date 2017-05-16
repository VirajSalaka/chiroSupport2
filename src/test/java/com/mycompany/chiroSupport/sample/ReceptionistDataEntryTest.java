package com.mycompany.chiroSupport.sample;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static com.google.common.io.Resources.getResource;
import static org.testfx.api.FxAssert.verifyThat;


public class ReceptionistDataEntryTest extends ApplicationTest {

    Parent mainNode;


    public void start(Stage stage) throws Exception {
        mainNode = FXMLLoader.load(Main.class.getResource("/receptionistDataEntry.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        /* Do not forget to put the GUI in front of windows. Otherwise, the robots may interact with another
        window, the one in front of all the windows... */
        stage.toFront();
    }

    @Test
    public void checkNameFld (){
        ((TextField) GuiTest.find("#patientNamefld")).setText("salaka");
        verifyThat("#patientNamefld", NodeMatchers.hasText("salaka"));
    }
    @Test
    public void checkbhtNoFld (){
        ((TextField) GuiTest.find("#bhtNofld")).setText("1234567");
        verifyThat("#bhtNofld", NodeMatchers.hasText("1234567"));
    }
    @Test
    public void checkNicNoFld (){
        ((TextField) GuiTest.find("#nicNofld")).setText("123456789");
        verifyThat("#nicNofld", NodeMatchers.hasText("123456789"));
    }
    @Test
    public void checkContactNoFld (){
        ((TextField) GuiTest.find("#contactNofld")).setText("710618178");
        verifyThat("#contactNofld", NodeMatchers.hasText("710618178"));
    }
    @Test
    public void checkaddressArea (){
        ((TextArea) GuiTest.find("#addressarea")).setText("5 B, main road, Moratuwa");
        verifyThat("#addressarea", NodeMatchers.hasText("5 B, main road, Moratuwa"));
    }



}
