package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Emote;
import commons.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static commons.Config.*;


public class IntermediateLeaderboardCtrl extends BaseCtrl {

    private ObservableList<Player> data;

    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> rank;
    @FXML
    private TableColumn<Player, String> player;
    @FXML
    private TableColumn<Player, String> score;

    @FXML
    ProgressBar pgBarIntermediate;

    @FXML
    Label questionTracker;
    @FXML
    Label scoreLabel;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;
    @FXML
    private Label rankInfo;
    List<Player> players;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        rank.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRank())));
        player.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPlayerName()));
        score.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getScore())));
    }

    @Inject
    public IntermediateLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl,
                                       ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils, server, gameUtils);
    }

    public void activateProgressBar() {
        utils.runProgressBar(pgBarIntermediate, timeForIntermediate, mainCtrl::showQuestion, null);
    }

    public void updateQuestionTracker() {
        gameUtils.updateTracker(questionTracker, scoreLabel, false);
    }

    @FXML
    private void emote(Event e) {
        utils.playButtonSound();
        String url = ((ImageView) e.getSource()).getImage().getUrl();
        String path = url.substring(url.lastIndexOf('/'));
        Emote emote = new Emote(path, gameUtils.getPlayer().getPlayerName());
        server.send("/app/emote/" + gameUtils.getGameID(), emote);
    }

    public void refresh(){
        players = server.getPlayers(gameUtils.getGameID()).getListOfPlayers();
        Collections.sort(players,Player.Comparators.SCORE);
        Collections.reverse(players);
        for(Player p : players){
            p.setRank(players.indexOf(p)+1);
        }
        data = FXCollections.observableList(players);
        table.setItems(data);
        indicatePlayerRanking();
    }

    public void indicatePlayerRanking(){
        Player currentPlayer = gameUtils.getPlayer();
        int ranking = players.indexOf(currentPlayer)+1;
        int difference;
        switch(ranking){
            case 1:
                rankInfo.setText("You are first, keep scoring!");
                break;
            case 2:
                int first = players.get(0).getScore();
                difference = first - currentPlayer.getScore();
                rankInfo.setText("You are second, " + difference + " more points and you are first!");
                break;
            default:
                if(ranking>=2) {
                    String namePrevious = players.get(ranking - 2).getPlayerName();
                    int previousScore = players.get(ranking - 2).getScore();
                    difference = previousScore - currentPlayer.getScore();
                    rankInfo.setText("You are number " + ranking
                            + ", behind " + namePrevious
                            + " by " + difference + " points");
                }
                break;
        }
        rankInfo.setVisible(true);
    }
}
