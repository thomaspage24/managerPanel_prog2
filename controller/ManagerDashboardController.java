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
            refreshTeamData(model.getTeam());

            model.teamProperty().addListener((obs, oldteam, newTeam) -> {
                refreshTeamData(newTeam);
                updateManagerButtons(newTeam);
            });
            
        }
    }

    @FXML
    private void handleClose() { stage.close(); }

    @FXML
    private void handleWithdraw() {
        if (model != null) {
            Manager manager = League.getInstance().getLoggedInManager();
            League.getInstance().withdrawManagerFromTeam(manager);
            model.teamProperty().addListener((obs, oldTeam, newTeam) -> {
                refreshTeamData(newTeam);
                System.out.println("check withdraw old team" + oldTeam);
                System.out.println("Check withdraw new team" + newTeam);
            });
        }
       
    }
    
    @FXML 
    private void handleManage() { ViewLoader.showStage(model, "/view/TeamDashboardView.fxml", "Team Dashboard", new Stage()); }

    @FXML
    private void handleSwap() { ViewLoader.showStage(model, "/view/SwapView.fxml", "Swap", new Stage()); }

    private void refreshTeamData(Team team) {
        if (team == null) {
            teamNameLbl.setText("No team");
            jersey.setImage(new Image(getClass().getResourceAsStream("/view/image/none.png")));
            return;
        }

        teamNameLbl.setText(team.getTeamName());
        String pathToImage = "/view/image/" + team.getJerseyString();
        jersey.setImage(new Image(getClass().getResourceAsStream(pathToImage)));
    }

    private void updateManagerButtons(Team team) {
        boolean hasTeam = team != null;
        withdrawBtn.setDisable(!hasTeam);
        manageBtn.setDisable(!hasTeam);
        
    }
    
}

