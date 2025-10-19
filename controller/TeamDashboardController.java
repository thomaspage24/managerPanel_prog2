package controller;

import javafx.fxml.FXML;
import au.edu.uts.ap.javafx.*;
import model.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import javafx.beans.property.*;
import model.exception.UnauthorisedAccessException;
import model.exception.FillException;
import model.exception.InvalidSigningException;
public class TeamDashboardController extends Controller<Manager> {
    /*
     * Banner
     * Seperator + teamname
     * label + text box + sign button only gets highlighted after typing n amount of letters
     * table view 2 columns, name + position
     * right side of tV is grid
     */
    @FXML private Label teamNameLbl;
    @FXML private TextField enterPlayerTf;
    @FXML private Button signBtn;
    @FXML private TableView<Player> playersTv;
    @FXML private TableColumn<Player, String> playerColumn;
    @FXML private TableColumn<Player, String> positionColumn;
    @FXML private GridPane teamJerseyGrid;
    @FXML private Button closeBtn;


    @FXML
    private void initialize() {
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        if (model != null) {
            Team team = model.getTeam();
            teamNameLbl.setText(team.getTeamFullName());
            playersTv.setItems(team.getAllPlayers().getPlayers());

        }
    }

    @FXML private void handleSign() {
        /* if player not in league/alr on team/diff team ivalidsigning 
         * 
        */
    }
}
