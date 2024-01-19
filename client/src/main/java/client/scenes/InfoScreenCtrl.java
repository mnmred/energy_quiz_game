package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import  client.utils.Config;

public class InfoScreenCtrl extends BaseCtrl {

    @FXML
    Label hintExplainer;
    @FXML
    Label timeExplainer;
    @FXML
    Label doublePointsExplainer;


    @Inject
    public InfoScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils, server, gameUtils);
    }

    public void setHintExplainer() {
        hintExplainer.setText("Eliminates an incorrect\nanswer from multiple\nchoice questions");
    }

    public void setTimeExplainer() {
        timeExplainer.setText(
                String.format("Reduces opponents\ntime by %d%%", (int)(Config.timeReductionPercentage * 100)));
    }

    public void setDoublePointsExplainerExplainer() {
        doublePointsExplainer.setText("Doubles your points\nfor the current question");
    }

}
