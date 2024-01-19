package server;

import commons.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    /**
     * Constructor for ActivityService.
     *
     * @param activityRepository The repository which stores the activities.
     */
    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Method for adding a new Activity to the repository.
     *
     * @param activity The activity to be added.
     * @return The activity added with the assigned id.
     */
    public Activity addActivity(Activity activity) {
        activityRepository.save(activity);
        return activity;
    }

    /**
     * Get an Activity from the repository by its id.
     *
     * @param id the id to search for.
     * @return the activity if present. Otherwise, an empty Optional.
     */
    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    /**
     * Get all activities that match the description.
     *
     * Gets all activities whose descriptions contain the string pattern.
     * @param descriptionPattern The string to be searched with.
     * @return List of all matching activities.
     */
    public List<Activity> getActivitiesByMatchingDescription(String descriptionPattern) {
        return activityRepository.findByDescriptionContaining(descriptionPattern);
    }

    /**
     * Get all activities that are stored in the repository.
     *
     * @return The activities.
     */
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Method for removing activities by its id.
     *
     * @param id Id of the activity to be removed.
     * @return The activity if it was present. Otherwise, an empty Optional.
     */
    public Optional<Activity> removeActivity(Long id) {
        Optional<Activity> optionalActivity = activityRepository.findById(id);
        if (optionalActivity.isPresent()) {
            activityRepository.deleteById(id);
        }
        return optionalActivity;
    }

    /**
     * Method for removing activities.
     *
     * @param activity The activity to be removed from the repository.
     * @return The activity if it was present. Otherwise, an empty Optional.
     */
    public Optional<Activity> removeActivity(Activity activity) {
        Optional<Activity> optionalActivity = activityRepository.findOne(Example.of(activity));
        if (optionalActivity.isPresent()) {
            activityRepository.delete(activity);
        }
        return optionalActivity;
    }

    /**
     * Method for getting 4 activities.
     * The activities are selected in a smart way.
     * @return The list containing 4 activities
     */
    public List<Activity> get4Activities() {
        List<Activity> list = new ArrayList<>(activityRepository.findAll());
        Collections.sort(list, Activity.Comparators.ENERGY); //sorts the list according to the energy consumption
        int value = (int)(Math.random()* (list.size()-42));
        List<Activity> smartList = new ArrayList<>();
        smartList.add(list.get(value));
        smartList.add(list.get(value+20));
        smartList.add(list.get(value+40));
        smartList.add(list.get(value+41));
        Collections.shuffle(smartList); //shuffle the list so that the last button is not always the answer
        return smartList;
    }

    public Activity getActivity() {
        List<Activity> list = new ArrayList<>(activityRepository.findAll());
        int value = (int)(Math.random()* list.size());
        return list.get(value);
    }

    /**
     * @param activity which has the newer fields
     * @return the new activity if update is successful or null otherwise
     */
    public Activity updateActivity(Activity activity) {
        Optional<Activity> databaseActivity = getActivityById(activity.getId());
        if(!databaseActivity.isEmpty()) {
            databaseActivity.get().setDescription(activity.getDescription());
            databaseActivity.get().setEnergyConsumption(activity.getEnergyConsumption());
            databaseActivity.get().setPicturePath(activity.getPicturePath());
            activityRepository.save(databaseActivity.get());
            return databaseActivity.get();
        }
        return null;
    }
}
