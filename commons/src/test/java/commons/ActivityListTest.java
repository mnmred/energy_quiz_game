package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityListTest {

    public List<Activity> listOfActivities;
    public Activity a;
    public Activity a1;

    @BeforeEach
    void init(){
        a = new Activity("car",1L,"/path");
        a1 = new Activity("bus",1L,"/path");
        listOfActivities = new ArrayList<>();
        listOfActivities.add(a);
        listOfActivities.add(a1);
    }

    @Test
    void getListOfActivities() {
        ActivityList al = new ActivityList();
        al.setListOfActivities(listOfActivities);
        List<Activity> list = new ArrayList<>();
        list.add(a);
        list.add(a1);
        assertEquals(list,al.getListOfActivities());
    }

    @Test
    void setListOfActivities() {
        ActivityList al = new ActivityList();
        al.setListOfActivities(listOfActivities);
        List<Activity> list = new ArrayList<>();
        list.add(a);
        list.add(a1);
        al.setListOfActivities(list);
        assertEquals(list,al.getListOfActivities());
    }
}