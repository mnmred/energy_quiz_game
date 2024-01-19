package server;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ActivityServiceTest {

    private ActivityService sut;
    private MockActivityRepository repo;
    private Activity act1;
    private Activity act2;

    @BeforeEach
    void setup() {
        repo = new MockActivityRepository();
        sut = new ActivityService(repo);
        act1 = new Activity("description", 12L, "path/to/file1");
        act2 = new Activity("a different description", 42L, "path/to/file2");
    }

    @Test
    void addActivity() {
        sut.addActivity(act1);
        assertTrue(repo.activities.contains(act1));
        assertTrue(repo.calledMethods.contains("save"));
        sut.addActivity(act2);
        assertEquals(List.of("save", "save"), repo.calledMethods);
        assertNotEquals(act1.getId(), act2.getId());
    }

    @Test
    void getActivityById() {
        sut.addActivity(act1);
        sut.addActivity(act2);
        assertEquals(Optional.of(act1), sut.getActivityById(act1.getId()));
        assertEquals(Optional.of(act2), sut.getActivityById(act2.getId()));
        assertEquals(Optional.empty(), sut.getActivityById(act1.getId() + act2.getId())); // a non-existent id
    }

    @Test
    void getActivitiesByMatchingDescription() {
        sut.addActivity(act1);
        sut.addActivity(act2);
        var contain1 = sut.getActivitiesByMatchingDescription("desc");
        var contain2 = sut.getActivitiesByMatchingDescription("different");
        var contain3 = sut.getActivitiesByMatchingDescription("thing");
        assertEquals(2, contain1.size());
        assertEquals(1, contain2.size());
        assertEquals(0, contain3.size());
    }

    @Test
    void getAllActivities() {
        assertEquals(0, sut.getAllActivities().size());
        sut.addActivity(act1);
        assertEquals(List.of(act1), sut.getAllActivities());
        sut.addActivity(act2);
        assertEquals(2, sut.getAllActivities().size());
    }

    @Test
    void removeActivity() {
        sut.addActivity(act1);
        sut.addActivity(act2);
        var removed1 = sut.removeActivity(act1.getId());
        assertEquals(Optional.of(act1), removed1);
        assertTrue(repo.calledMethods.contains("deleteById"));
        assertEquals(List.of(act2), sut.getAllActivities());
        act1.setId(null);
        sut.addActivity(act1);
        assertEquals(2, sut.getAllActivities().size());
        var removed2 = sut.removeActivity(act2);
        assertEquals(Optional.of(act2), removed2);
        assertTrue(repo.calledMethods.contains("delete"));
        assertEquals(List.of(act1), sut.getAllActivities());
        assertEquals(Optional.empty(), sut.removeActivity(new Activity()));
    }

    @Test
    void getActivity(){
        sut.addActivity(act1);
        assertEquals(act1, sut.getActivity());
        sut.addActivity(act2);
        assertTrue(List.of(act1,act2).contains(sut.getActivity()));
    }

    @Test
    void updateActivity() {
        sut.addActivity(act1);
        act1.setDescription("new");
        act1.setEnergyConsumption(111L);
        act1.setPicturePath("/path/new");
        var activity1 = sut.updateActivity(act1);
        assertEquals(act1, activity1);
        var activity2 = sut.updateActivity(act2);
        assertEquals(null,activity2);
    }
}