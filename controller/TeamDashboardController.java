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
    @FXML private Button unsignBtn;


    @FXML
    private void initialize() {
//        playersTv.getColumns().clear();
//        TableColumn<Player, String> playerColumn = new TableColumn<>("Player");
//        TableColumn<Player, String> positionColumn = new TableColumn<>("Position");
//        playerColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
//        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
//        playersTv.getColumns().addAll(playerColumn, positionColumn);

        enterPlayerTf.textProperty().addListener((obs, oldText, newText) -> {
            signBtn.setDisable(false);
        });

        if (model != null) {
            Team team = model.getTeam();
            playersTv.setItems(team.getAllPlayers().getPlayers());


        }
        System.out.println("Columns loaded: " + playersTv.getColumns().size());
        for (TableColumn<?, ?> col : playersTv.getColumns())
            System.out.println(" -> " + col.getText());


    }

    @FXML private void handleSign() {
        /* if player not in league/alr on team/diff team ivalidsigning 
         * 
        */
        Team team = model.getTeam();
        if (team != null) {
            return;
        }
    }
}
