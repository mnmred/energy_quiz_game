package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.stream.Collectors;


public class ComparisonQuestionCtrl extends BaseQuestionCtrl {
    @FXML
    Label ActivityDescription1;
    @FXML
    Label ActivityDescription2;
    @FXML
    Label ActivityDescription3;

    @FXML
    ImageView questionImage1;
    @FXML
    ImageView questionImage2;
    @FXML
    ImageView questionImage3;

    private List<Activity> activities;

    @Inject
    public ComparisonQuestionCtrl(ServerUtils server, MainCtrl mainCtrl,
                                  ApplicationUtils utils, GameUtils gameUtils) {
        super(server, mainCtrl, utils, gameUtils);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * gets 3 activities from the server, calculates the correct answer and displays the activities
     */
    @Override
    public void generateActivity() {
        activities = server.get4Activities(gameUtils.getCurrentQuestion(), gameUtils.getGameID())
                .getListOfActivities();
        Activity activity = activities.stream().max(Activity.Comparators.ENERGY).get();
        activities.remove(activity);
        long answer = activities.stream().map(Activity::getEnergyConsumption)
                .sorted().collect(Collectors.toList()).get(2);
        answerButtonId = activities.stream().map(Activity::getEnergyConsumption)
                .collect(Collectors.toList()).indexOf(answer)+1;
        displayActivities();
        setHasPlayerAnswered(false);
    }

    private void displayActivities() {
        Platform.runLater(() -> {
            ActivityDescription1.setText(activities.get(0).getDescription());
            ActivityDescription2.setText(activities.get(1).getDescription());
            ActivityDescription3.setText(activities.get(2).getDescription());
            questionImage1.setImage(new Image(ServerUtils.SERVER + activities.get(0).getPicturePath()));
            questionImage2.setImage(new Image(ServerUtils.SERVER + activities.get(1).getPicturePath()));
            questionImage3.setImage(new Image(ServerUtils.SERVER + activities.get(2).getPicturePath()));
        });
        mainCtrl.setAnswersForAnswerReveal(activities, answerButtonId);
    }

}
