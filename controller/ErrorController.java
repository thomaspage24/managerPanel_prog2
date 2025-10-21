package controller;

import javafx.fxml.FXML;
import au.edu.uts.ap.javafx.*;
import javafx.fxml.FXML;
import model.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.beans.property.*;
import model.exception.UnauthorisedAccessException;
import javafx.stage.Stage;


public class ErrorController extends Controller <String>{
    @FXML private Button closeButton;
    @FXML private Label exceptionText;
    @FXML private Label detailMsg;
    public static String exceptionType;
    public static String exceptionMsg;

    @FXML
    private void initialize() {
      
        exceptionText.setText(exceptionType);
        detailMsg.setText(exceptionMsg);
       
    }

    @FXML
    private void handleClose(ActionEvent event) {
        stage.close();
    }
}
