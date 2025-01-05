package ma.enset.mvc.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ma.enset.mvc.dao.PlayerDaoImpl;
import ma.enset.mvc.dao.TeamDaoImpl;
import ma.enset.mvc.dao.entities.Player;
import ma.enset.mvc.dao.entities.Team;
import ma.enset.mvc.service.CPlayerTeamService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class TeamsPlayersController implements Initializable {

    @FXML private TextField playerNameField;
    @FXML private TextField playerPositionField;
    @FXML private TextField playerNumberField;
    @FXML private ComboBox<String> playerTeamComboBox;

    @FXML private TextField teamNameField;
    @FXML private TextField teamCityField;

    @FXML private TableView<Player> playersTable;
    @FXML private TableView<Team> teamsTable;

    @FXML private TableColumn<Team, Long> teamIdColumn;
    @FXML private TableColumn<Team, String> teamNameColumn;
    @FXML private TableColumn<Team, String> teamCityColumn;

    @FXML private TextField teamSearchField;

    @FXML private TableColumn<Player, Long> playerIdColumn;
    @FXML private TableColumn<Player, String> playerNameColumn;
    @FXML private TableColumn<Player, String> playerPositionColumn;
    @FXML private TableColumn<Player, Integer> playerNumberColumn;
    @FXML private TableColumn<Player, String> playerTeamColumn;

    private ObservableList<Team> Teams = FXCollections.observableArrayList();
    private ObservableList<Player> Players = FXCollections.observableArrayList();

    private CPlayerTeamService service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        TeamDaoImpl teamDao = new TeamDaoImpl();
        service = new CPlayerTeamService(playerDao, teamDao);

        Teams.addAll(service.findAllTeams());
        Players.addAll(service.getAllPlayers());

        teamIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        teamNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teamCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playerPositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        playerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        playerTeamColumn.setCellValueFactory(cellData -> {
            Player player = cellData.getValue();
            Team team = player.getTeam();
            String teamName = (team != null) ? team.getName() : "No Team";
            return new SimpleStringProperty(teamName);
        });


        teamIdColumn.getStyleClass().add("text-field"); // Ensure CSS is applied
        teamNameColumn.getStyleClass().add("text-field");
        teamCityColumn.getStyleClass().add("text-field");
        playerIdColumn.getStyleClass().add("text-field");
        playerNameColumn.getStyleClass().add("text-field");
        playerPositionColumn.getStyleClass().add("text-field");
        playerNumberColumn.getStyleClass().add("text-field");
        playerTeamColumn.getStyleClass().add("text-field");



        playersTable.setItems(Players);
        teamsTable.setItems(Teams);

        teamSearchField.textProperty().addListener((observable, oldValue, newValue) -> filterTeams(newValue));

        teamsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showPlayersForTeam(newValue);
            }
        });


        playerTeamComboBox.setItems(FXCollections.observableArrayList(
                Teams.stream().map(Team::getName).collect(Collectors.toList())
        ));

    }

    private void filterTeams(String searchQuery) {
        ObservableList<Team> filteredTeams = FXCollections.observableArrayList();

        for (Team team : Teams) {
            if (team.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    team.getCity().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredTeams.add(team);
            }
        }

        teamsTable.setItems(filteredTeams);
    }

    public void addTeam() {
        Team team = new Team();
        team.setName(teamNameField.getText());
        team.setCity(teamCityField.getText());
        service.saveTeam(team);
        Teams.add(team);
    }

    public void addPlayer() {
        Player player = new Player();
        player.setName(playerNameField.getText());
        player.setPosition(playerPositionField.getText());
        player.setNumber(Integer.parseInt(playerNumberField.getText()));


        String selectedTeamName = playerTeamComboBox.getSelectionModel().getSelectedItem();
        Team selectedTeam = null;


        for (Team team : Teams) {
            if (team.getName().equals(selectedTeamName)) {
                selectedTeam = team;
                break;
            }
        }
        player.setTeam(selectedTeam);
        service.savePlayer(player);
        Players.add(player);
    }

    private void showPlayersForTeam(Team selectedTeam) {
        ObservableList<Player> filteredPlayers = FXCollections.observableArrayList();

        for (Player player : Players) {
            if (player.getTeam() != null && player.getTeam().getId().equals(selectedTeam.getId())) {
                filteredPlayers.add(player);
            }
        }

        playersTable.setItems(filteredPlayers);
    }

    public void deletePlayer() {
        Player player = playersTable.getSelectionModel().getSelectedItem();
        if (player != null) {
            service.deletePlayerById(player.getId());
            Players.remove(player);
        }
    }

    public void deleteTeam() {
        Team team = teamsTable.getSelectionModel().getSelectedItem();
        if (team != null) {
            service.deleteTeamById(team.getId());
            Teams.remove(team);
        }
    }

    public void printInfo() {
        File file = new File("PlayersInfo.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Player player : Players) {
                String playerInfo = "Player Info:\n" +
                        "Name: " + player.getName() + "\n" +
                        "Position: " + player.getPosition() + "\n" +
                        "Number: " + player.getNumber() + "\n" +
                        "Team: " + (player.getTeam() != null ? player.getTeam().getName() : "No Team") + "\n\n";
                bw.write(playerInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}