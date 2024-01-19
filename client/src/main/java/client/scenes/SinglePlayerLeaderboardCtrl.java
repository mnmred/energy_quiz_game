package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class SinglePlayerLeaderboardCtrl extends BaseCtrl implements Initializable {

    private ObservableList<Player> data;
    List<Player> players;

    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> rank;
    @FXML
    private TableColumn<Player, String> player;
    @FXML
    private TableColumn<Player, String> score;
    @FXML
    private TableColumn<Player, String> scoredTime;
    @FXML
    private Button playAgain;
    @FXML
    private Label rankInfo;


    @Inject
    public SinglePlayerLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils,
                                       GameUtils gameUtils) {
        super(mainCtrl, utils, server, gameUtils);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        rank.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRank())));
        player.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPlayerName()));
        score.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getScore())));
        scoredTime.setCellValueFactory(p -> new SimpleStringProperty(convertDate(p.getValue().getTime())));
    }

    /**
     * Adds a player to the repo
     *
     * @param p - the player we want to add to the leaderboard
     */
    public void addPlayer(Player p) {
        server.addPlayerToSPLeaderboard(p);
        refresh();
    }

    @FXML
    private void refreshButton() {
        utils.playButtonSound();
        refresh();
    }

    /**
     * Update the leaderboard
     */
    public void refresh() {
        players = server.getPlayersInSPL();
        //A sort should be done to display the Players in the correct order
        Collections.sort(players,Player.Comparators.SCORE);
        Collections.reverse(players);
        for(Player p : players){
            p.setRank(players.indexOf(p)+1);
        }
        data = FXCollections.observableList(players);
        table.setItems(data);
    }

    public void indicatePlayerRanking(){
        Player currentPlayer = gameUtils.getPlayer();
        int ranking = players.indexOf(currentPlayer)+1;
        rankInfo.setText("you are number "+ranking+"!");
        rankInfo.setVisible(true);
    }

    public void hideRankingInfo(){
        rankInfo.setVisible(false);
    }
    @FXML
    public void playAgain() {
        utils.playButtonSound();
        mainCtrl.showNamePromtScene();
    }

    /**
     * The button "Play a singleplayer again" is made visible
     */
    public void showPLayAgain() {
        playAgain.setVisible(true);
    }

    /**
     * The button "Play a singleplayer again" is hidden
     * Used when leaderboard is accessed from the homescreen
     */
    public void hidePlayAgain() {
        playAgain.setVisible(false);
    }

    /**
     * Converts a timestamp to a string representing how long ago happened.
     * @param timestamp The timestamp to convert.
     * @return A human-readable string of when it took place.
     */
    private String convertDate(Timestamp timestamp) {
        Timestamp now = Timestamp.from(Instant.now());
        long timeDiff = now.getTime() - timestamp.getTime();
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        long week = day * 7;
        if (timeDiff >= week) return timeDiff / week + " week(s) ago";
        if (timeDiff >= day) return timeDiff / day + " day(s) ago";
        if (timeDiff >= hour) return timeDiff / hour + " hour(s) ago";
        if (timeDiff >= minute) return timeDiff / minute + " minute(s) ago";
        return "Just now";
    }

}
