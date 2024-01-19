package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.Emote;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

import static commons.Config.*;

public class AnswerRevealCtrl extends BaseCtrl {


    @FXML
    ProgressBar pgBarReveal;
    @FXML
    Label questionTracker;
    @FXML
    Label scoreLabel;
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;

    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;

    @FXML
    Label estimateAnswer;
    @FXML
    Label pointsGrantedEstimate;
    @FXML
    Label pointsGrantedMC;

    @FXML
    ImageView checkmark1;
    @FXML
    ImageView checkmark2;
    @FXML
    ImageView checkmark3;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    protected final SinglePlayerLeaderboardCtrl splCtrl;

    @Inject

    public AnswerRevealCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils,
                            SinglePlayerLeaderboardCtrl splCtrl) {
        super(mainCtrl, utils, server, gameUtils);
        this.splCtrl = splCtrl;
    }

    /**
     * starts the countdown of the progressbar for the answer reveal
     */
    public void activateProgressBar() {
        utils.runProgressBar(pgBarReveal, timeAnswerReveal, () -> {
            restorePointsLabel();
            if (gameUtils.getCurrentQuestion() < totalQuestions) {
                mainCtrl.restoreQuestions();
                if (gameUtils.getGameType().equals(GameUtils.GameType.SinglePlayer)) {
                    mainCtrl.showQuestion();
                } else mainCtrl.showIntermediateLeaderboard();
            } else {
                mainCtrl.restore();
                if (gameUtils.getGameType().equals(GameUtils.GameType.SinglePlayer)) {
                    gameUtils.setPlayer(server.addPlayerToSPLeaderboard(gameUtils.getPlayer()));
                    splCtrl.refresh();
                    mainCtrl.showSPLeaderboard();
                } else
                    mainCtrl.showMPFinalLeaderboard();
            }
        }, null);
    }

    private void restorePointsLabel() {
        pointsGrantedMC.setText("You got 0 points!");
        pointsGrantedEstimate.setText("You got 0 points!");
    }

    /**
     * updates the label of this page which displays the current question.
     * it's called with false since the question doesn't update when answers are revealed, only when the next question
     * shows.
     */

    public void updateTracker() {
        gameUtils.updateTracker(questionTracker, scoreLabel, false);
    }


    public void emote(Event e) {
        utils.playButtonSound();
        String url = ((ImageView) e.getSource()).getImage().getUrl();
        String path = url.substring(url.lastIndexOf('/'));
        Emote emote = new Emote(path, gameUtils.getPlayer().getPlayerName());
        server.send("/app/emote/" + gameUtils.getGameID(), emote);
    }

    /**
     * takes in a list of 3 activities and sets the values next to the answer
     *
     * @param activities     - list of 3 activities passed from the QuestionCtrl
     * @param answerButtonId - numeric id of the correct answer
     */
    public void setAnswers(List<Activity> activities, int answerButtonId) {
        setEstimateQuestionFormat(false);
        checkmark1.setImage(null);
        checkmark2.setImage(null);
        checkmark3.setImage(null);
        Image checkmark = new Image("/images/checkmark.png");
        switch (answerButtonId) {
            case 1:
                checkmark1.setImage(checkmark);
                break;
            case 2:
                checkmark2.setImage(checkmark);
                break;
            case 3:
                checkmark3.setImage(checkmark);
                break;
        }
        label1.setText(activities.get(0).getEnergyConsumption().toString() + "WH");
        label2.setText(activities.get(1).getEnergyConsumption().toString() + "WH");
        label3.setText(activities.get(2).getEnergyConsumption().toString() + "WH");
    }

    /**
     * Takes in a boolean, if true, sets the estimate question format, if false, sets the MC question format
     *
     * @param bool
     */
    private void setEstimateQuestionFormat(Boolean bool) {
        estimateAnswer.setVisible(bool);
        pointsGrantedEstimate.setVisible(bool);
        pointsGrantedMC.setVisible(!bool);
        checkmark1.setVisible(!bool);
        checkmark2.setVisible(!bool);
        checkmark3.setVisible(!bool);
        label1.setVisible(!bool);
        label2.setVisible(!bool);
        label3.setVisible(!bool);
        firstButton.setVisible(!bool);
        secondButton.setVisible(!bool);
        thirdButton.setVisible(!bool);
    }

    /**
     * Sets the answer after having an estimate question
     *
     * @param activity
     */
    public void setAnswer(Activity activity) {
        setEstimateQuestionFormat(true);
        estimateAnswer.setText(activity.getEnergyConsumption().toString() + "WH");
    }

    /**
     * Sets the obtained points
     *
     * @param points - obtained points
     * @param bool   - indicates which label to set visible
     *               -> true for estimate related label
     *               -> false for MC with 3 activities related label
     */
    public void setAnswer(int points, boolean bool) {
        if (bool)
            pointsGrantedEstimate.setText("You got " + points + " points!");
        else
            pointsGrantedMC.setText("You got " + points + " points!");
    }
}
