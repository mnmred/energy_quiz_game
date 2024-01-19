package client.scenes;

import client.utils.ActivityBoardUtils;
import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.PostActivity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class EditActivityCtrl extends BaseCtrl {
    private ActivityBoardUtils actUtils;

    @FXML
    ImageView imageView;
    @FXML
    Label errorLabel;
    @FXML
    TextField questionField;
    @FXML
    TextField consumptionField;
    @FXML
    TextField imagePathField;

    String imagePath;
    boolean add;
    byte[] pictureBuffer;
    Activity activity;
    FileChooser fileChooser;


    @Inject
    public EditActivityCtrl(MainCtrl mainCtrl, ApplicationUtils utils, ServerUtils server, GameUtils gameUtils) {
        super(mainCtrl, utils, server, gameUtils);
    }

    /**
     * resets all the fields
     * resets the imageView to the placeholder image
     * makes the error label invisible
     */
    public void setUp(boolean add, Activity activity, ActivityBoardUtils actUtils) {
        this.actUtils = actUtils;
        fileChooser = new FileChooser();
        imagePath = "images/placeholder.png";
        errorLabel.setVisible(false);
        imageView.setImage(new Image(imagePath));
        questionField.setText("");
        consumptionField.setText("");
        imagePathField.setText("");
        this.add = add;
        if(!add){
            this.activity = activity;
            questionField.setText(activity.getDescription());
            consumptionField.setText(activity.getEnergyConsumption().toString());
            pictureBuffer = new byte[0];
            imageView.setImage(new Image(server.SERVER + activity.getPicturePath()));
            imagePath = activity.getPicturePath();
        } else this.activity = new Activity();
    }


    /**
     * Opens the file dialog for the user to select the image to be added
     */
    @FXML
    public void addImage() {
        utils.playButtonSound();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedImage = fileChooser.showOpenDialog(mainCtrl.getSecondaryStage());
        imagePathField.setText(selectedImage.getAbsolutePath());
        imagePath = selectedImage.getAbsolutePath();
        try {
            pictureBuffer = Files.readAllBytes(Path.of(imagePath));
            imageView.setImage(new Image(new ByteArrayInputStream(pictureBuffer)));
        } catch (Exception ex){
            errorLabel.setText("Can't find image");
            errorLabel.setVisible(true);
            ex.printStackTrace();
        }
    }


    /**
     * try to add the activity to the database
     */
    @FXML
    private void tryAdd() {
        utils.playButtonSound();
        if (validActivity()) {
            activity.setDescription(questionField.getText());
            activity.setEnergyConsumption(Long.parseLong(consumptionField.getText()));
            activity.setPicturePath(imagePath);
            PostActivity postActivity = new PostActivity(activity, pictureBuffer);
            if(add){
                Activity newActivity = server.addPostActivity(postActivity);
                if (newActivity != null){
                    actUtils.updateAdd(newActivity);
                    Stage stage = (Stage) imageView.getScene().getWindow();
                    stage.close();
                }  else errorLabel.setText("Server did not allow the activity to be added");
            } else {
                Activity newActivity = server.updatePostActivity(postActivity);
                if (newActivity != null){
                    actUtils.updateEdit(newActivity);
                    Stage stage = (Stage) imageView.getScene().getWindow();
                    stage.close();
                }  else errorLabel.setText("Server did not allow the activity to be added");
            }

            errorLabel.setVisible(true);
        }
    }

    /**
     * @return true if the activity fields pass the tests
     */
    private boolean validActivity() {
        String description = questionField.getText();
        Long consumption = (long) -1;

        if (description.equals("")) {
            errorLabel.setText("Question field can't be empty!");
            errorLabel.setVisible(true);
            return false;
        } else if (consumptionField.getText().equals("")) {
            errorLabel.setText("Energy consumption field can't bet empty!");
            errorLabel.setVisible(true);
            return false;
        } else if (imagePath.equals("images/placeholder.png")) {
            errorLabel.setText("Please provide an image!");
            errorLabel.setVisible(true);
            return false;
        }

        try {
            consumption = Long.parseLong(consumptionField.getText());
            if (consumption <= 0) {
                errorLabel.setText("Only positive numbers allowed!");
                errorLabel.setVisible(true);
                return false;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Only numbers allowed in the consumption field!");
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

}
