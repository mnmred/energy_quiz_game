package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MCQuestionCtrl extends BaseQuestionCtrl {

    @FXML
    Label ActivityDescription;
    @FXML
    ImageView questionImage;
    @FXML
    StackPane chatAndEmoteHolder;
    private Activity activity;

    @Inject
    public MCQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(server, mainCtrl, utils, gameUtils);
    }

    @Override
    public void generateActivity() {
        activity = server.getSingleActivity(gameUtils.getCurrentQuestion(), gameUtils.getGameID());
        long answer = activity.getEnergyConsumption();
        long option1;
        long option2;
        int random = (int)(Math.random()*3);
        switch (random){
            case 0:
                option1= (long) (answer * 1.5);
                option2= (long) (answer * 0.5);
                break;
            case 1:
                option1= (long) (answer * 1.5);
                option2= (long) (answer * 2.0);
                break;
            default:
                option1= (long) (answer * 0.75);
                option2= (long) (answer * 0.5);
                break;
        }
        Activity a = new Activity(activity.getDescription(), option1, activity.getPicturePath());
        Activity b = new Activity(activity.getDescription(), option2, activity.getPicturePath());
        List<Activity> activities = Arrays.asList(activity, a, b);
        Collections.shuffle(activities);
        answerButtonId = activities.indexOf(activity) + 1;
        displayActivity(activities);
        setHasPlayerAnswered(false);
    }

    private void displayActivity(List<Activity> activities) {
        ActivityDescription.setText(activity.getDescription());
        questionImage.setImage(new Image(ServerUtils.SERVER + activity.getPicturePath()));
        firstButton.setText(String.valueOf(activities.get(0).getEnergyConsumption()));
        secondButton.setText(String.valueOf(activities.get(1).getEnergyConsumption()));
        thirdButton.setText(String.valueOf(activities.get(2).getEnergyConsumption()));
        mainCtrl.setAnswersForAnswerReveal(activities, answerButtonId);
    }



}
