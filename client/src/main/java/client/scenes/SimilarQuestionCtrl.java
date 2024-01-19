package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.stream.Collectors;


public class SimilarQuestionCtrl extends BaseQuestionCtrl {


    @FXML
    Label ActivityDescription1;
    @FXML
    Label ActivityDescription2;
    @FXML
    Label ActivityDescription3;
    @FXML
    Label ActivityDescription;
    @FXML
    ImageView questionImage;

    private Activity activity;

    private List<Activity> activities;

    @Inject
    public SimilarQuestionCtrl(ServerUtils server, MainCtrl mainCtrl,
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
        activities = server.get4Activities(gameUtils.getCurrentQuestion(), gameUtils.getGameID()).getListOfActivities();
        activity = activities.stream().max(Activity.Comparators.ENERGY).get();
        activities.remove(activity);
        String description = activities.stream().max(Activity.Comparators.ENERGY).get().getDescription();
        answerButtonId = activities.stream().map(Activity::getDescription)
                .collect(Collectors.toList()).indexOf(description) + 1;
        displayActivities();
        setHasPlayerAnswered(false);
    }

    private void displayActivities() {
        questionImage.setImage(new Image(ServerUtils.SERVER + activity.getPicturePath()));
        ActivityDescription.setText(activity.getDescription());
        ActivityDescription1.setText(activities.get(0).getDescription());
        ActivityDescription2.setText(activities.get(1).getDescription());
        ActivityDescription3.setText(activities.get(2).getDescription());
        mainCtrl.setAnswersForAnswerReveal(activities, answerButtonId);
    }

}
