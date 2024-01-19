package server.api;

import commons.Activity;
import commons.PostActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.ActivityService;
import server.MockActivityRepository;
import server.database.ActivityRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ActivityControllerTest {

    private ActivityRepository repo;
    private ActivityService actServ;
    private ActivityController sut;
    private Activity act1;
    private Activity act2;
    private Activity act3;
    private PostActivity postAct;

    @BeforeEach
    void setup() {
        repo = new MockActivityRepository();
        actServ = new ActivityService(repo);
        sut = new ActivityController(actServ);
        act1 = new Activity("description", 12L, "path/to/file1");
        act2 = new Activity("a different description", 42L, "path/to/file2");
        /*File picture;
        try{
            picture = ResourceUtils.getFile("classpath:static\\00\\fridge.png");
            act3 = new Activity("even more different description", 50L,
                    picture.getAbsolutePath());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        postAct = new PostActivity(act3, "src\\test\\resources\\static\\newActivities\\");*/
    }

    @Test
    void getActivities() {
        actServ.addActivity(act1);
        assertEquals(ResponseEntity.of(Optional.of(List.of(act1))), sut.getActivities(null));
        actServ.addActivity(act2);
        assertEquals(2, sut.getActivities(null).getBody().size());
        assertEquals(ResponseEntity.of(Optional.of(List.of(act2))), sut.getActivities("different"));
        assertEquals(2, sut.getActivities("description").getBody().size());
        assertEquals(0, sut.getActivities("other").getBody().size());
    }

    @Test
    void getActivityById() {
        var act = actServ.addActivity(act1);
        assertEquals(act1, sut.getActivityById(act.getId()).getBody());
        assertEquals(HttpStatus.NOT_FOUND, sut.getActivityById(act.getId() + 1).getStatusCode());
    }

    @Test
    void deleteActivityById() {
        actServ.addActivity(act1);
        actServ.addActivity(act2);
        assertEquals(ResponseEntity.of(Optional.of(act1)), sut.deleteActivityById(act1.getId()));
        assertEquals(HttpStatus.NOT_FOUND, sut.deleteActivityById(act1.getId()).getStatusCode());
        assertEquals(1, repo.count());
    }

    @Test
    void addActivity() {
        sut.addActivity(act2);
        assertTrue(repo.findAll().contains(act2));
        var res = sut.addActivity(act2);
        assertEquals(ResponseEntity.of(Optional.of(act2)), res);
        assertEquals(2, repo.count());
    }

    @Test
    void getActivity() {
        sut.addActivity(act2);
        //size 1, so not random
        assertEquals(act2, sut.getActivity().getBody());
    }

    @Test
    void addPostActivity() {
        byte[] b = new byte[5];
        PostActivity post = new PostActivity(act1,b);
        assertEquals(act1, sut.addPostActivity(post).getBody());
        assertTrue(actServ.getAllActivities().contains(act1));
    }

    @Test
    void updatePostActivity() {
        byte[] b = new byte[5];
        Activity a1 = act1;
        a1.setEnergyConsumption(5050L);
        a1.setId(act1.getId());
        PostActivity post = new PostActivity(a1,b);
        sut.addActivity(act1);
        assertEquals(a1, sut.updatePostActivity(post).getBody());
        assertTrue(actServ.getAllActivities().contains(a1));
    }

    @Test
    void deletePostActivity() {
        sut.addActivity(act1);
        assertEquals(true, sut.deletePostActivity(act1.getId()).getBody());
        assertEquals(0, sut.getActivities(null).getBody().size());
    }
}