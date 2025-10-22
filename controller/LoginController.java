package controller;

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

import java.io.*;


public class LoginController extends Controller<League> {
    @FXML private TextField managerTf;
    @FXML private Button loginButton;
    @FXML private Button closeButton;

    @FXML
    private void initialize() {
        loginButton.setDisable(true);
        managerTf.textProperty().addListener((o, oldText, newText) -> {
            loginButton.setDisable(newText.isEmpty());
            managerTf.setOnAction(e -> handleLogin());
        });
       
    }

    @FXML
    private void handleLogin() {
        try {
            int managerId = Integer.parseInt(managerTf.getText());
            Manager manager = model.validateManager(managerId);
            ViewLoader.showStage(manager, "/view/ManagerDashboardView.fxml", "Manager Dashboard", new Stage());
            League.getInstance().setLoggedInManager(manager);

            stage.close(); // ?? maybe

        } catch (UnauthorisedAccessException e) {
            ErrorController.exceptionMsg = e.getMessage();
            ErrorController.exceptionType = e.getClass().getSimpleName();
            ViewLoader.showStage(null,"/view/ErrorView.fxml", "error", new Stage());

        } catch (NumberFormatException e) {
            ErrorController.exceptionType = e.getClass().getSimpleName();
            ErrorController.exceptionMsg= "Incorrect format for manager id";
            ViewLoader.showStage(null, "/view/ErrorView.fxml", "error", new Stage());
        }
    }
    @FXML
    private void handleClose() {
        stage.close();
    }


}
