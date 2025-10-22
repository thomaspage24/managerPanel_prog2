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

import javax.swing.*;
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
        jerseys = Arrays.asList(jersey0, jersey1, jersey2, jersey3, jersey4);
        initialiseJerseyListener();
        Team team = model.getTeam();
        if (model != null) {
            teamNameLbl.setText(team.getTeamFullName());
            playersTv.setItems(team.getAllPlayers().getPlayers());
            refreshJerseyGrid();

        }

    }
    @FXML private void handleClose() {
        stage.close();
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
        System.out.println(p);
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
            System.out.println(playerToSign);
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

    @FXML private void initialiseJerseyListener() {
        /*
        get clicked jersey and send to method that handles assigning the pos
         */
        for (int i = 0; i < jerseys.size(); i++) {
            int pos = i;
            ImageView img = jerseys.get(pos);
            img.setOnMouseClicked((event) -> {handleClickOnJersey(pos);});

        }

    }

    @FXML private void handleClickOnJersey(int n) {
        /*
        now we have a jersey to change

         */
        Team t = model.getTeam();
        Player[] currentTeam = t.getCurrentTeam();
        Player clickedJersey = currentTeam[n];
        Player selectedOnTable = playersTv.getSelectionModel().getSelectedItem();

//        try {
//             /*
//            cases to cover
//            jersey slot is empty -> assign player
//            */
//            if (clickedJersey == null && selectedOnTable != null) {
//                for (Player p :  currentTeam) {
//                    if (p.equals(selectedOnTable)) {
//                        throw new InvalidSigningException("Playe: " + p.getFullName() + " is already on active team");
//                    }
//                }
//                currentTeam[n] = selectedOnTable;
//                refreshJerseyGrid();
//                playersTv.refresh();
//                return;
//            }
//            // if already assigned to click position
//            if (clickedJersey == selectedOnTable) {
//                throw new InvalidSigningException("Player " + selectedOnTable.getFullName() + " is already signed to this position on the active team.");
//            }
//            // remove a player from active team if nothing is selected on table
//            if (clickedJersey != null && selectedOnTable == null) {
//                currentTeam[n] = null;
//                playersTv.refresh();
//                refreshJerseyGrid();
//                return;
//            }
//            // swap a player if he is already on the team,
//        }
        try {
            // remove player
            if (clickedJersey != null && selectedOnTable == null) {
                currentTeam[n] = null;
                refreshJerseyGrid();
                playersTv.refresh();
                return;
            }
            // nothing case
            if (clickedJersey == null && selectedOnTable == null) {
                return;
            }
            // alr signed
            if (clickedJersey == selectedOnTable) {
                throw new InvalidSigningException(selectedOnTable.getFullName() + " is already in this spot on team.");
            }
            // IF ALREADY ON ACTIVE PLAYERS LIST
            int pos = -1;
            for (int i = 0; i < currentTeam.length; i++) {
                if (currentTeam[i] != null && currentTeam[i].equals(selectedOnTable)) {
                    pos=i;
                   // throw new InvalidSigningException(selectedOnTable.getFullName() + " is in the active lineup in a diff spot.");
                    break;
                }
            }
            // empty jersey slot assign player to
            if (clickedJersey == null && selectedOnTable != null) {
                if (pos != -1) {
                    throw new FillException(selectedOnTable.getFullName() + " is in already in the active team");
                }
                currentTeam[n] = selectedOnTable;
                refreshJerseyGrid();
                playersTv.refresh();
                return;
            }

            // swap players in jersey grid if already on team
            // if in pos swap them
            // if not on team put on team
            if (clickedJersey != null && selectedOnTable != null) {
                if (pos != -1) {
                    currentTeam[pos] = clickedJersey;
                    currentTeam[n] = selectedOnTable;
                } else {
                    currentTeam[n] = selectedOnTable;
                }
                refreshJerseyGrid();
                playersTv.refresh();
                return;

            }
        }
        catch (Exception e) {
            ErrorController.exceptionMsg = e.getMessage();
            ErrorController.exceptionType = e.getClass().getSimpleName();
            ViewLoader.showStage(null,"/view/ErrorView.fxml", "error", new Stage());
        }

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


        for (int i = 0; i < jerseys.size(); i++) {
            ImageView img = jerseys.get(i);
            Player player = players[i];

            if (player != null) {
                img.setImage(new Image(getClass().getResourceAsStream("/view/image/" + team.getJerseyString())));
                Tooltip.install(img, new Tooltip(player.getFullName() + " " + player.getPosition() ));
            } else {
                img.setImage(new Image(getClass().getResourceAsStream("/view/image/none.png")));
                Tooltip.uninstall(img,null);
            }
        }
        printCurrentTeam();
    }
    private void printCurrentTeam() {
        Team team = model.getTeam();
        Player[] currentTeam = team.getCurrentTeam();
        System.out.println("Current team players:");
        for (int i = 0; i < currentTeam.length; i++) {
            Player p = currentTeam[i];
            if (p != null) {
                System.out.println("Slot " + i + ": " + p.getFullName());
            } else {
                System.out.println("Slot " + i + "=null");
            }
        }
        System.out.println("----");
    }
}
