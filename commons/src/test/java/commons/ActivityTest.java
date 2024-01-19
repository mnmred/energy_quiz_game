package commons;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private Activity act1;
    private Activity act2;

    @BeforeEach
    void setUp() {
        act1 = new Activity("desc1", 86L, "path/to/pic");
        act2 = new Activity("desc2", 42L, "path/to/different/pic");
    }

    @Test
    void testEquals() {
        assertNotEquals(act1, act2);
        assertEquals(act1, act1);
        Activity act = new Activity("desc1", 86L, "path/to/pic");
        assertEquals(act, act1);
    }

    @Test
    void testHashCode() {
        assertNotEquals(act1.hashCode(), act2.hashCode());
        Activity act = new Activity("desc1", 86L, "path/to/pic");
        assertEquals(act.hashCode(), act1.hashCode());
    }

    @Test
    void testToString() {
        String s = act1.toString();
        assertTrue(s.contains("desc1"));
        assertTrue(s.contains("86"));
        assertTrue(s.contains("path/to/pic"));
    }

    @Test
    void getId() {
        assertEquals(null, act1.getId());
    }

    @Test
    void setId() {
        act1.setId(12L);
        assertEquals(12L, act1.getId());
    }

    @Test
    void getDescription() {
        assertEquals("desc1", act1.getDescription());
    }

    @Test
    void setDescription() {
        act1.setDescription("newDesc");
        assertEquals("newDesc", act1.getDescription());
    }

    @Test
    void getEnergyConsumption() {
        assertEquals(86L, act1.getEnergyConsumption());
    }

    @Test
    void setEnergyConsumption() {
        act1.setEnergyConsumption(4L);
        assertEquals(4L, act1.getEnergyConsumption());
    }

    @Test
    void getPicturePath() {
        assertEquals("path/to/pic", act1.getPicturePath());
    }

    @Test
    void setPicturePath() {
        act1.setPicturePath("new/path/to/pic");
        assertEquals("new/path/to/pic", act1.getPicturePath());
    }
}