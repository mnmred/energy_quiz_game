package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.Config;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class HomeScreenCtrl extends BaseCtrl {

    @FXML
    TextField serverField;
    @FXML
    Label connectionLabel;

    private final NamePromptCtrl namePromptCtrl;

    @Inject
    public HomeScreenCtrl(MainCtrl mainCtrl, ApplicationUtils utils,
                          ServerUtils server, GameUtils gameUtils, NamePromptCtrl namePromptCtrl) {
        super(mainCtrl, utils, server, gameUtils);
        this.namePromptCtrl = namePromptCtrl;
    }

    @FXML
    private void showSPLeaderboard() {
        utils.playButtonSound();
        mainCtrl.showSPLeaderboardFromHome();
    }

    @FXML
    private void showExitScreen() {
        utils.playButtonSound();
        mainCtrl.showExitScreen();
    }

    /**
     * Based on which button the player clicked, the player will get the nameprompt for single- or multiplayer.
     *
     * @param e The button on which the player has clicked to reach the nameprompt.
     */
    @FXML
    private void showPrompt(Event e) {
        utils.playButtonSound();
        String mode = ((Button) e.getSource()).getText();
        if (mode.equals("Singleplayer")) gameUtils.setGameType(GameUtils.GameType.SinglePlayer);
        else gameUtils.setGameType(GameUtils.GameType.MultiPlayer);
        namePromptCtrl.setUp();
        mainCtrl.showNamePromtScene();
    }

    @FXML
    private void showEditScreen() {
        utils.playButtonSound();
        mainCtrl.showEditScreen();
    }

    @FXML
    private void showInfo() {
        utils.playButtonSound();
        mainCtrl.showInfo();
    }

    @FXML
    private void tryConnect() {
        utils.playButtonSound();
        tryPing();
    }

    public void tryPing() {
        ServerUtils.setSERVER(serverField.getText());
        try {
            server.ping();
            connectionLabel.setText("Success");
            connectionLabel.setStyle(Config.connectionLabelStyle + "green");
            connectionLabel.setVisible(true);
        } catch (Exception e) {
            connectionLabel.setText("Failure");
            connectionLabel.setStyle(Config.connectionLabelStyle + "red");
            connectionLabel.setVisible(true);
        }
    }

    public void loadServerField() {
        serverField.setText(commons.Config.server);
    }
}
