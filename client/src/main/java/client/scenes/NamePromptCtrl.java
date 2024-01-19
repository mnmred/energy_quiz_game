package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.Scanner;

import static commons.Config.*;


public class NamePromptCtrl extends BaseCtrl {

    @FXML
    public Button startButton;
    @FXML
    private TextField nameField;
    @FXML
    private Label errorLabel;

    @Inject
    public NamePromptCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils, server, gameUtils);
    }

    /**
     * When the start button is clicked this fires off.
     * It checks whether the provided name abides by certain rules and whether it has permission
     * to start a singleplayer game
     * A new player is created, the name entered will be used for identification later on
     */
    public void startSinglePlayer() {
        utils.playButtonSound();
        String name = nameField.getText();
        Player player = server.generatePlayer(name);
        if (checkName(nameField, errorLabel) && server.startSingle(player)) {
            //Player player = new Player(nameField.getText());
            gameUtils.setPlayer(player);
            //gameUtils.requestGameID();
            client.utils.Config.playerName = nameField.getText();
            Long gameID = server.start(player);
            this.gameUtils.setGameID(gameID);
            mainCtrl.showQuestion();
        }
    }

    /**
     * make the server field prompt display the default server example
     * the player name display the old player name if there was one saved
     * if not then empty field with the prompt text
     * set focus makes it so nameField is not clicked on when loaded for the prompt to display
     */
    public void setUp() {
        nameField.clear();
        errorLabel.setVisible(false);
        nameField.setPromptText("Enter your name...");
        if (client.utils.Config.playerName == null) loadSavedName();
        nameField.setText(client.utils.Config.playerName);
        nameField.setFocusTraversable(false);
    }

    /**
     * Sets the old player name if there is one
     * If not then makes it empty
     */
    private void loadSavedName() {
        try {
            Scanner sc = new Scanner(new File(client.utils.Config.nameFile));
            if (sc.hasNext()) client.utils.Config.playerName = sc.next();
            else client.utils.Config.playerName = "";
            sc.close();
        } catch (Exception ex) {
            System.out.println(ex);
            client.utils.Config.playerName = "";
        }
    }

    /**
     * @param nameField The text field in the scene
     * @return true if the text in nameField adheres to certain rules
     * (rules for now are now whitespaces and that something must be entered)
     * (we can ignore no whitespaces if we convert the text to base64 because then the url request doesn't mess up)
     */
    public boolean checkName(TextField nameField, Label errorLabel) {
        String name = nameField.getText();
        if (name.length() > maxCharsUsername) {
            errorLabel.setText("Name needs to be 20 characters or less!");
            errorLabel.setVisible(true);
            return false;
        } else if (name.contains(" ")) {
            errorLabel.setText("No whitespaces allowed!");
            errorLabel.setVisible(true);
            return false;
        } else if (name.equals("")) {
            errorLabel.setText("You have to enter something!");
            errorLabel.setVisible(true);
            return false;
        }
        return true;
    }

    /**
     * When the start button is clicked this fires off.
     * It checks whether the provided name abides by certain rules and whether it has permission
     * to enter the waiting room. If it doesn't then for now I've just written it off that the
     * name is taken but that may not be the case if the connection doesn't go through and so on.
     */

    public void enterWaitingRoom() {
        if (checkName(nameField, errorLabel)) {
            String name = nameField.getText();
            Player player = server.generatePlayer(name); //new Player(nameField.getText());
            if (server.enterWaitingRoom(player)) {
                gameUtils.setPlayer(player);
                mainCtrl.showWaitingRoom();
                client.utils.Config.playerName = nameField.getText();
            } else {
                errorLabel.setText("Name is taken!");
                errorLabel.setVisible(true);
            }
        }
    }

    /**
     * when the player clicks the button, we have to check if the player is in single- or multiplayer
     * depending on this we call the appropriate function
     */
    @FXML
    private void confirm() {
        utils.playButtonSound();
        gameUtils.resetGame();
        if (gameUtils.getGameType().equals(GameUtils.GameType.SinglePlayer)) {
            mainCtrl.activateSingleplayer();
            startSinglePlayer();
        } else {
            mainCtrl.activateMultiplayer();
            enterWaitingRoom();
        }
        client.utils.Config.playerName = nameField.getText();
    }
}
