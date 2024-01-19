package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostActivityTest {

    Activity activity;
    byte[] pictureBuffer;
    PostActivity postActivity;

    @BeforeEach
    void setUp() {
        activity = new Activity("Description", 42L, "path\\file.png");
        pictureBuffer = new byte[10];
        postActivity = new PostActivity(activity, pictureBuffer);
    }

    @Test
    void getPictureBuffer() {
        assertEquals(pictureBuffer, postActivity.getPictureBuffer());
    }

    @Test
    void getActivity() {
        assertEquals(activity, postActivity.getActivity());
    }

    @Test
    void testEquals() {
        PostActivity postActivity2 = new PostActivity(activity, pictureBuffer);
        assertEquals(postActivity2, postActivity);
    }

    @Test
    void hashTest(){
        PostActivity postActivity1 = new PostActivity(activity, pictureBuffer);
        assertEquals(postActivity.hashCode(), postActivity1.hashCode());
    }
}