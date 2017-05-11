package com.mycompany.chiroSupport.sample;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Salaka on 5/11/2017.
 */
public class AppointmentController implements Initializable{

    @FXML
    private BorderPane appointmentBorderPane;


    public void initialize(URL location, ResourceBundle resources) {
        DatePickerSkin datePickerSkin;
        datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        AnchorPane popupContent = (AnchorPane) datePickerSkin.getPopupContent();
        DatePickerContent pop = (DatePickerContent)datePickerSkin.getPopupContent();

 //       List<DateCell> dateCells = getAllDateCells(pop);

//        for (DateCell cell : dateCells)
//        {
//            cell.addEventHandler(
//                    MouseEvent.MOUSE_PRESSED,(e)->
//                    {
//                        System.out.println("Mouse clicked :" + cell.getItem());
//                    }
//            );
//        }

        appointmentBorderPane.setTop(popupContent);
    }

    private static List<DateCell> getAllDateCells(DatePickerContent content)
    {
        List<DateCell> result = new ArrayList();

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

}
