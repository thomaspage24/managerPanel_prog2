package controller;

import au.edu.uts.ap.javafx.*;
import javafx.fxml.FXML;
import model.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.beans.property.*;
import model.exception.UnauthorisedAccessException;

import java.io.*;

public class ManagerDashboardController extends Controller<Manager>{
    /*
     * team name center aligned
     * png of team jersey 
     * withdraw + manage button centered
     * bottom bar spanning whole hbox swap + close
     */
    
    @FXML private Label teamNameLbl;
    @FXML private ImageView jersey;
    @FXML private Button withdrawBtn;
    @FXML private Button manageBtn;
    @FXML private Button swapBtn; // open swap controller + tableview of teams
    @FXML private Button closeBtn;


    @FXML
    private void initialize() {
        if (model != null) {
            Team team = model.getTeam();
            teamNameLbl.setText(team.getTeamName());

            String pathToImage = "/view/image/" + team.getJerseyString();
            System.out.println(pathToImage);
            InputStream stream = getClass().getResourceAsStream(pathToImage);
            
            if (stream != null) {
                jersey.setImage(new Image (stream));
            } else {
                jersey.setImage(new Image("/view/image/none.png"));
            } 
             
            
        }
    }

    @FXML
    private void handleClose() { stage.close(); }

    @FXML
    private void handleWithdraw() {

    }
    
    @FXML 
    private void handleManage() {

    }

    @FXML
    private void handleSwap() { ViewLoader.showStage(model, "/view/SwapView.fxml", "Swap", new Stage()); }

    
}

