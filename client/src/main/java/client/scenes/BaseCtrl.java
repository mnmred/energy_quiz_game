package client.scenes;


import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.NotificationMessage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A base class for controllers which have various common controls, such as sound toggles.
 */
public abstract class BaseCtrl implements Initializable {

    protected final MainCtrl mainCtrl;
    protected final ApplicationUtils utils;
    protected final ServerUtils server;
    protected final GameUtils gameUtils;

    @FXML
    protected ImageView music;
    @FXML
    protected VBox notificationBox;

    @Inject
    public BaseCtrl(MainCtrl mainCtrl, ApplicationUtils utils, ServerUtils server, GameUtils gameUtils) {
        this.mainCtrl = mainCtrl;
        this.utils = utils;
        this.server = server;
        this.gameUtils = gameUtils;
    }

    @FXML
    public void showHome() {
        utils.playButtonSound();
        if (server.isConnected()) {
            if(gameUtils.getPlayer() != null)
                server.send("/app/notification/" + gameUtils.getGameID(),
                        new NotificationMessage(gameUtils.getPlayer().getPlayerName() + " left"));

            server.disconnect();
        }
        gameUtils.resetGame();
        mainCtrl.showHome();
    }

    /**
     * Toggles music in the application.
     */
    @FXML
    protected void toggleMusic() {
        utils.playButtonSound();
        utils.toggleMusic();
    }


    /**
     * Initialize is required to register ImageViews as toggles.
     * This is because of injection that happens after class is initialized.
     *
     * @param location Not relevant.
     * @param resources Not relevant.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utils.registerMusicToggle(music);
        utils.registerNotificationBox(notificationBox);
    }
}
