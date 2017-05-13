package com.mycompany.chiroSupport.sample;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

}
