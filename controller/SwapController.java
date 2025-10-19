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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.beans.property.*;
import model.exception.UnauthorisedAccessException;
import java.text.*;
import javafx.collections.*;
import javafx.beans.*;
import javafx.beans.property.*;
import javafx.beans.binding.*;

/*
banner
Seperator
swap team
seperator
tableview of teams
    iterate through teams list to find teams with no manager to display them
    can just return getManageableTeams
swap button disabled untill activated by highlight on click of team
close

 */
public class SwapController extends Controller<Manager>{
    @FXML private Button closeBtn;
    @FXML private Button swapBtn;
    @FXML private ListView<Team> teamLv;


    //private ObserverableList<Team> teams;

    @FXML
    private void initialize() {
        if (model != null) {
            League league = League.getInstance();
            teamLv.setItems(league.getManageableTeams().getTeams());
            swapBtn.disableProperty().bind(teamLv.getSelectionModel().selectedItemProperty().isNull());
        }
    }

    @FXML
    private void handleClose() {
        stage.close();
    }
    @FXML
    private void handleSwap() {
        Team selected =  teamLv.getSelectionModel().getSelectedItem();
        if (selected != null) {
            League league = League.getInstance();
            league.setManagerForTeam(model, selected);
            System.out.println(selected.getManager() + " " + league.getLoggedInManager().getTeam().getTeamName());
        }
    }
}
