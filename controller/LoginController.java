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

    private void initialize() {

    }

    @FXML
    private void handleLogin() {
        try {
            int managerId = Integer.parseInt(managerTf.getText());
            Manager manager = model.validateManager(managerId);

            ViewLoader.showStage(manager, "/view/ManagerDashboardView.fxml", "Manager Dashboard", new Stage());
            stage.close(); // ?? maybe

        } catch (UnauthorisedAccessException e) {
            showErrorScreen(e.getClass().getSimpleName());
        }
    }
    @FXML
    private void handleClose() {
        stage.close();
    }

    private void showErrorScreen(String s) {
        // implement invalid login and invalid format aswell
        ViewLoader.showStage(s,"/view/ErrorView.fxml", "error", new Stage());

    }


}
