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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @FXML private ImageView jersey0, jersey1, jersey2, jersey3, jersey4;
    private List<ImageView> jerseys;


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
        unsignBtn.disableProperty().bind(playersTv.getSelectionModel().selectedItemProperty().isNull());
        if (model != null) {
            Team team = model.getTeam();
            teamNameLbl.setText(team.getTeamFullName());
            playersTv.setItems(team.getAllPlayers().getPlayers());
            refreshJerseyGrid();

        }
       

    }
    @FXML private void handleUnsign() {
        /*
         * listen to table view selected content
         * then just unsign them, should update the table view auto
         */
        Player p = playersTv.getSelectionModel().getSelectedItem();
        Team t = model.getTeam();
        League l = League.getInstance();
        if (p == null) {
            return;
        }

        t.getAllPlayers().remove(p);
        p.setTeam(null);
        playersTv.refresh();
        refreshJerseyGrid();

    }

    @FXML private void handleSign() {
        /* if player not in league/alr on team/diff team ivalidsigning 
         * need League, team and players to handle
         * catch exceptions for error 
        */
        String playerToFind = enterPlayerTf.getText();
        League league = League.getInstance();
        Team team = model.getTeam();

        try {
            Player playerToSign = league.getPlayers().player(playerToFind);

            if (playerToFind.isEmpty() || playerToSign == null) {
                throw new InvalidSigningException("Player does not exist in the league");
            }
            if (team.getAllPlayers().getPlayers().contains(playerToSign)) {
                throw new InvalidSigningException(playerToSign.getFullName() + " is already signed to your team.");
            }
            if (playerToSign.getTeam() != null) {
                throw new FillException("Cannot sign " + playerToSign.getFullName() + ", player is already signed to " + playerToSign.getTeam().getTeamFullName());
            }
            
            playerToSign.setTeam(team);
            team.getAllPlayers().add(playerToSign);
            enterPlayerTf.setText("");
        } catch (InvalidSigningException | FillException e) {
            
            ErrorController.exceptionType = e.getClass().getSimpleName();
            ErrorController.exceptionMsg = e.getMessage();
            ViewLoader.showStage(null, "/view/ErrorView.fxml", "error", new Stage());
        }
    }
    @FXML private void showErrorScreen(String s) {
        ViewLoader.showStage(s, "/view/ErrorView.fxml", "error", new Stage());
    }

    @FXML private void refreshJerseyGrid() {
        /*
        fetch list
        get team
        iterate through jersey list
        check if currentTeam arr has at index
            if there populate with team jersey
            else none jersey

         */
        Team team = model.getTeam();
        Player[] players = team.getCurrentTeam();
        List<ImageView>  jersey = Arrays.asList(jersey0, jersey1, jersey2, jersey3, jersey4);

        for (int i = 0; i < jersey.size(); i++) {
            ImageView img = jersey.get(i);
            Player player = players[i];

            if (player != null) {
                img.setImage(new Image(getClass().getResourceAsStream("/view/image/" + team.getJerseyString())));
                Tooltip.install(img, new Tooltip(player.getFullName() + player.getPosition() ));
            } else {
                img.setImage(new Image(getClass().getResourceAsStream("/view/image/none.png")));
            }
        }
    }
}
