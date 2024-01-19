package client.scenes;

import client.utils.ActivityBoardUtils;
import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;


public class EditScreenCtrl extends BaseCtrl {

    @FXML
    GridPane activityGrid;
    @FXML
    Button previousButton;
    @FXML
    Button nextButton;
    @FXML
    Button showButton;
    @FXML
    Spinner<Integer> pageSpinner;
    @FXML
    Label pageLabel;

    private final ActivityBoardUtils actUtils;


    @Inject
    public EditScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils
            , ActivityBoardUtils actUtils) {
        super(mainCtrl, utils, server, gameUtils);
        this.actUtils = actUtils;
    }

    /**
     * Opens the secondary edit screen from mainCtrl
     */
    public void addActivity() {
        utils.playButtonSound();
        mainCtrl.editActivity(true, null);
    }

    /**
     * Fetches activities
     * Sets up the activity browser
     * Loads the activity browser on page 1
     */
    public void setUp() {
        actUtils.setUp(mainCtrl, activityGrid, server, utils);
        pageSpinner.setEditable(true);
        pageSpinner.setValueFactory(actUtils.getSpinnerValues());
        actUtils.loadGrid();
        enableButtons();
    }

    /**
     * Goes to previous page of activities
     * Platform run later is used to prevent spam clicking
     */
    public void loadPrevious(){
        utils.playButtonSound();
        disableButtons();
        actUtils.loadPrevious();
        enableButtons();
    }

    /**
     * Goes to next page of activities
     * Platform run later is used to prevent spam clicking
     */
    public void loadNext(){
        utils.playButtonSound();
        disableButtons();
        actUtils.loadNext();
        enableButtons();
    }

    /**
     * Loads the page selected in the pageSpinner
     *
     * Platform run later is used to prevent spam clicking
     */
    public void loadPage() {
        utils.playButtonSound();
        disableButtons();
        actUtils.setCurrentPageAndLoad(pageSpinner.getValue());
        enableButtons();
    }

    /**
     * Disables buttons to prevent spamming
     */
    public void disableButtons(){
        Platform.runLater(() -> {
            nextButton.setDisable(true);
            previousButton.setDisable(true);
            showButton.setDisable(true);
        });
    }

    /**
     * Enables buttons back and checks if they should be visible
     */
    public void enableButtons(){
        updateLabels();

        if(actUtils.getCurrentPage() != 1) previousButton.setVisible(true);
        else previousButton.setVisible(false);
        if(actUtils.getCurrentPage() != actUtils.getPageCount()) nextButton.setVisible(true);
        else nextButton.setVisible(false);

        Platform.runLater(() -> {
            nextButton.setDisable(false);
            previousButton.setDisable(false);
            showButton.setDisable(false);
        });
    }

    public void updateLabels(){
        pageLabel.setText("\\ " + actUtils.getPageCount());
        pageSpinner.getValueFactory().setValue(actUtils.getCurrentPage());
    }

    public ActivityBoardUtils getActUtils() {
        return actUtils;
    }
}
