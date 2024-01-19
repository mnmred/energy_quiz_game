package client.utils;

import static commons.Config.*;

import client.scenes.MainCtrl;
import com.google.inject.Inject;
import commons.Player;
import javafx.scene.control.Label;

public class GameUtils {

    private Player player;
    private int currentQuestion;
    private GameType gameType;
    private Long currentTimeMillis;
    private Long gameID;
    protected final ServerUtils server;
    protected final MainCtrl mainCtrl;
    protected final ApplicationUtils utils;

    @Inject
    public GameUtils(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.utils = utils;
    }

    public void requestGameID() {
        this.gameID = server.requestGameID();
    }

    public void resetGame() {
        player = null;
        currentQuestion = 0;
        currentTimeMillis = null;
        gameID = null;
        utils.clearNotificationBox();
        utils.cancelProgressBar();
    }

    public void startTimer() {
        currentTimeMillis = System.currentTimeMillis();
    }

    public long stopTimer() {
        return System.currentTimeMillis() - currentTimeMillis;
    }

    /**
     * Updates the passed in labels to show the current question and score out of the total.
     *
     * @param question Label which contains the current question in format "Question X/Y".
     * @param score    Label which contains the current score in format "Score X/Y".
     * @param update   Indicates if question counter should be incremented, otherwise, if false it will just update
     *                 the given label acoording to currentQuestion variable.
     */
    public void updateTracker(Label question, Label score, boolean update) {
        if (update) {
            currentQuestion++;
        }
        question.setText("Question " + currentQuestion + "/" + totalQuestions);
        score.setText("Score " + player.getScore() + "/" + currentQuestion * 200);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public enum GameType {
        SinglePlayer,
        MultiPlayer
    }
}
