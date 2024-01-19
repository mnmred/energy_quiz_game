package commons;

import java.util.List;

public class ActivityList {
    private List<Activity> listOfActivities;

    public ActivityList() {
    }

    public ActivityList(List<Activity> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }

    public List<Activity> getListOfActivities() {
        return listOfActivities;
    }

    public void setListOfActivities(List<Activity> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }
}
