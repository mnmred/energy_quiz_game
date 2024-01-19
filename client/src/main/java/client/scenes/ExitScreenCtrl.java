package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ExitScreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ApplicationUtils utils;

    @FXML
    Button closeButton;
    @FXML
    Button quitButton;

    @Inject
    public ExitScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.utils = utils;
    }

    public void quit() {
        utils.playButtonSound();
        mainCtrl.beforeExit();
        Platform.exit();
    }

    public void closeButtonMouseClicked(){
        utils.playButtonSound();
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
