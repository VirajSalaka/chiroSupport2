package com.mycompany.chiroSupport.sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static junit.framework.TestCase.assertFalse;
import static org.loadui.testfx.GuiTest.find;
import static org.testfx.api.FxAssert.verifyThat;

/**
 * Created by Salaka on 5/16/2017.
 */
public class LoginTest extends ApplicationTest {
    Parent mainNode;


    public void start(Stage stage) throws Exception {
        mainNode = FXMLLoader.load(Main.class.getResource("/login.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        /* Do not forget to put the GUI in front of windows. Otherwise, the robots may interact with another
        window, the one in front of all the windows... */
        stage.toFront();
    }

    @Test
    public void setBothnamesAndCheckEnabledSearchButton() {

        ((TextField) find("#usernameFld")).setText("140173T");
        verifyThat("#usernameFld", NodeMatchers.hasText("140173T"));

        ((TextField) find("#passwordFld")).setText("user");
        verifyThat("#passwordFld", NodeMatchers.hasText("user"));
    }
}
