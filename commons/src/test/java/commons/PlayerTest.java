package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    public Player ps1;
    public Player ps2;
    public Player ps3;

    @BeforeEach
    void init() {
        ps1 = new Player("John", 2137);
        ps2 = new Player("Joanna", 3214);
        ps3 = new Player("Johnny", 5555);
    }

    @Test
    void testConstructor() {
        var ps = new Player("Josh", 123);
        assertEquals("Josh", ps.getPlayerName());
        assertEquals(123, ps.getScore());
        Player p = new Player();
        assertNotNull(p);
        Player p1 = new Player("Someone");
        assertNotNull(p1);
    }

    @Test
    void testHashCode() {
        assertEquals(ps1.hashCode(), ps1.hashCode());
        assertEquals(ps2.hashCode(), ps2.hashCode());
        assertNotEquals(ps1.hashCode(), ps2.hashCode());
        var ps = new Player("Josh", 123);
        assertNotEquals(ps.hashCode(), ps1.hashCode());
    }

    @Test
    void testToString() {
        var strRepr = ps1.toString();
        System.out.println(strRepr);
        assertTrue(strRepr.contains("John"));
        assertTrue(strRepr.contains("2137"));
        assertTrue(strRepr.contains(Player.class.getSimpleName()));
        assertTrue(strRepr.contains("playerName"));
        assertTrue(strRepr.contains("score"));
    }

    @Test
    void addPoints() {
        ps1.addPoints(3);
        assertEquals(2140, ps1.getScore());
    }

    @Test
    void getId() {
        ps1.setId(22L);
        assertEquals(22L, ps1.getId());
    }

    @Test
    void setId() {
        ps1.setId(222L);
        assertEquals(222L, ps1.getId());
        ps1.setId(141L);
        assertEquals(141L, ps1.getId());
    }

    @Test
    void getPlayerName() {
        assertEquals("John", ps1.getPlayerName());
        assertEquals("Joanna", ps2.getPlayerName());
        assertEquals("Johnny", ps3.getPlayerName());
    }

    @Test
    void getScore() {
        assertEquals(2137, ps1.getScore());
    }

    @Test
    void setScore() {
        ps1.setScore(2000);
        assertEquals(2000, ps1.getScore());
    }

    @Test
    void testEquals() {
        assertNotEquals(ps1, ps2);
        ps1.setId(22L);
        Player p1 = new Player("John", 2137);
        p1.setId(22L);
        assertTrue(ps1.equals(p1));
        assertEquals(ps1,ps1);
    }
}