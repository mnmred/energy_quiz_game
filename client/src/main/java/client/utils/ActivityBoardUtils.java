package client.utils;

import client.scenes.MainCtrl;
import commons.Activity;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class ActivityBoardUtils {

    private GridPane activityGrid;

    private List<Activity> activityList;
    private int start, end, activitiesPerPage = Config.activitiesPerPage, pageCount, currentPage;
    private SpinnerValueFactory.IntegerSpinnerValueFactory spinnerValues;
    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;
    private ApplicationUtils utils;

    public void setUp(MainCtrl mainCtrl, GridPane activityGrid, ServerUtils serverUtils, ApplicationUtils utils) {
        this.mainCtrl = mainCtrl;
        this.activityGrid = activityGrid;
        this.serverUtils = serverUtils;
        this.utils = utils;
        activityList = serverUtils.getActivities();
        start = 0;
        currentPage = 1;
        if(activityList.size() - activitiesPerPage < 0) end = activityList.size();
        else end = start + activitiesPerPage;
        pageCount = activityList.size() / activitiesPerPage;
        if(activityList.size() % activitiesPerPage != 0)
            pageCount++;
        spinnerValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, pageCount, 1);
    }

    public SpinnerValueFactory.IntegerSpinnerValueFactory getSpinnerValues() {
        return spinnerValues;
    }


    /**
     * Loads the activity grid from start index to end index of activityList
     * Platform run later is used to prevent spam clicking
     */
    public void loadGrid(){
        activityGrid.getChildren().clear();
        for(int i=start; i < end; i++) setUpRow(i);
    }

    public void loadPrevious() {
        loadPage(--currentPage);
    }

    public void loadNext() {
        loadPage(++currentPage);
    }

    /**
     * loads the activity into the row and creates other objects
     *
     * @param i is the index of the row
     */
    private void setUpRow(int i) {
        Image editImage = new Image(Config.gearImage);
        Image deleteImage = new Image(Config.deleteImage);
        setUpImage(i);
        setUpEdit(editImage, i);
        setUpDelete(deleteImage, i);
        setUpLabels(activityList.get(i), i);
    }/**
     * Sets up the labels of the row
     *
     * @param activity is the activity from which the labels get data
     * @param index is the index in which to put the row
     */
    private void setUpLabels(Activity activity, int index) {
        Label descriptionLabel = new Label(activity.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);

        activityGrid.add(descriptionLabel, 0, index % activitiesPerPage);
        activityGrid.add(new Label(activity.getEnergyConsumption().toString()), 1, index % activitiesPerPage);
    }

    /**
     * sets up the image of the activity
     *
     * @param index is the index in which to put the row
     */
    public void setUpImage(int index){
        ImageView imageView = new ImageView();
        Image image = new Image(serverUtils.SERVER + activityList.get(index).getPicturePath());
        setProperties(imageView, image, index);
        activityGrid.add(imageView, 2, index % activitiesPerPage);
    }


    /**
     * sets up the edit ImageView
     *
     * @param image is the icon of the gear
     * @param index is the index in which to put the row
     */
    public void setUpEdit(Image image, int index){
        ImageView editActivity = new ImageView();
        editActivity.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                utils.playButtonSound();
                int index = Integer.parseInt(((ImageView) event.getSource()).getId());
                mainCtrl.editActivity(false, activityList.get(index));
                event.consume();
            }
        });
        setProperties(editActivity, image, index);
        activityGrid.add(editActivity, 3, index % activitiesPerPage);
    }

    /**
     * Updates the old activity with the new activity fields
     *
     * @param newActivity is the activity from which to get new data
     */
    public void updateEdit(Activity newActivity) {
        for (int i=0; i<activityList.size(); i++) {
            if (activityList.get(i).getId().equals(newActivity.getId())) {
                activityList.set(i, newActivity);
                break;
            }
        }
        loadGrid();
    }

    /**
     * Adds the new activity to the activity list and sets a new page count
     * spinnerValues.setMax sets the maximum possible value of the spinner to the new page count
     *
     * @param newActivity is the new activity to be added to the list
     */
    public void updateAdd(Activity newActivity) {
        activityList.add(newActivity);
        pageCount = activityList.size() / activitiesPerPage;
        if(activityList.size() % activitiesPerPage != 0)
            pageCount++;
        spinnerValues.setMax(pageCount);
        mainCtrl.refreshLabels();
        loadGrid();
    }

    /**
     * sets up the delete ImageView
     *
     * @param image is the icon of the X
     * @param index is the index in which to put the row
     */
    public void setUpDelete(Image image, int index){
        ImageView deleteActivity = new ImageView();
        setProperties(deleteActivity, image, index);
        deleteActivity.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                utils.playButtonSound();
                int index = Integer.parseInt(((ImageView) event.getSource()).getId());
                if(serverUtils.deletePostActivity(activityList.get(index).getId())){
                    activityList.remove(index);
                    pageCount = activityList.size() / activitiesPerPage;
                    if(activityList.size() % activitiesPerPage != 0)
                        pageCount++;
                    if(currentPage > pageCount)
                        currentPage = pageCount;
                    spinnerValues.setMax(pageCount);
                    mainCtrl.refreshLabels();
                    loadPage(currentPage);
                }

                event.consume();
            }
        });
        activityGrid.add(deleteActivity, 4, index % activitiesPerPage);
    }

    /**
     * Adds basic properties to the imageView
     *
     * @param imageView is the imageView of which the properties will be set
     * @param image is the image to be applied
     * @param index is the index in which to put the row
     */
    public void setProperties(ImageView imageView, Image image, int index) {
        imageView.setImage(image);
        imageView.getStyleClass().add("clickable");
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setId(Integer.toString(index));
    }

    /**
     * Loads the page selected in the pageSpinner
     *
     * Platform run later is used to prevent spam clicking
     */
    public void loadPage(int page) {
        start = (page - 1) * activitiesPerPage;
        end = start + activitiesPerPage;
        if (end > activityList.size()) end = activityList.size();

        loadGrid();
    }

    public int getPageCount() {
        return pageCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPageAndLoad(int currentPage) {
        this.currentPage = currentPage;
        loadPage(currentPage);
    }
}
