package server;

import commons.Activity;
import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g;
    Player p1;
    Player p2;
    HashMap<Integer, Integer> questionTypes;
    HashMap<Integer, List<Activity>> activities;

    @BeforeEach
    public void setup() {
        g = new Game();
        p1 = new Player("Vian",50);
        p2 = new Player("Kuba", 70);
        questionTypes = new HashMap<>();
        activities = new HashMap<>();
    }

    @Test
    void addAPlayer() {
        g.addAPlayer(p1);
        assertTrue(g.getSize()==1);

    }

    @Test
    void addPointsToAPlayer(){
        g.addAPlayer(p1);
        Player ptest= p1;
        p1.addPoints(50);
        assertEquals(ptest,g.addPointsToAPlayer("Vian",50));
    }

    @Test
    void updateScore(){
        g.addAPlayer(p1);
        p1.setScore(200);
        g.updateScore(p1);
        var expected = p1.getScore();
        Long playerID = p1.getId();
        var actual = g.getHashMapOfPlayers().get(playerID).getScore();
        assertEquals(expected, actual);
    }

    @Test
    void removeAPlayerWithName(){
        g.addAPlayer(p1);
        var before = g.getHashMapOfPlayers().keySet().size();
        g.removeAPlayerWithName(p1.getPlayerName());
        var expected = before - 1 >= 0 ? before - 1 : 0;
        var actual = g.getHashMapOfPlayers().keySet().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeAPlayerWithName2(){
        g.addAPlayer(p1);
        g.addAPlayer(p2);
        var before = g.getHashMapOfPlayers().keySet().size();
        g.removeAPlayerWithName(p2.getPlayerName());
        var expected = before - 1 >= 0 ? before - 1 : 0;
        var actual = g.getHashMapOfPlayers().keySet().size();
        assertEquals(expected, actual);
    }

    @Test
    void removeAll(){
        g.addAPlayer(p1);
        g.addAPlayer(p2);
        g.removeAll();
        var expected = 0;
        var actual = g.getHashMapOfPlayers().keySet().size();
        assertEquals(expected, actual);
    }

    @Test
    void getSize(){
        g.addAPlayer(p1);
        g.addAPlayer(p2);
        var expected = 2;
        var actual = g.getSize();
        assertEquals(expected, actual);
    }

    @Test
    void getPlayers(){
        g.addAPlayer(p1);
        g.addAPlayer(p2);
        var expected = List.of(p1, p2);
        var actual = g.getPlayers();
        assertEquals(expected, actual);
    }

    @Test
    void setPlayers(){
        var list = List.of(p1 ,p2);
        g.setPlayers(list);
        var expected = List.of(p1, p2);
        var actual = g.getPlayers();
        assertEquals(expected, actual);
    }

    @Test
    void getGameId(){
        var expected = 8L;
        var actual = g.getGameId();
        assertEquals(expected, actual);
    }


    @Test
    void getByName() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        Player ptest = g.getByName(p1.getPlayerName());
        assertEquals(p1, ptest);
    }


    @Test
    void testEquals() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        Game g1 = new Game();
        g1.addAPlayer(p2);
        g1.addAPlayer(p1);
        assertTrue(g.equals(g1));
    }

    @Test
    void testNotEquals() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        Game g1 = new Game();
        g1.addAPlayer(p2);
        assertFalse(g.equals(g1));
    }

    @Test
    void testHashCode() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        Game g1 = new Game();
        g1.addAPlayer(p2);
        g1.addAPlayer(p1);
        assertEquals(g.hashCode(),g1.hashCode());
        Game g2 = new Game();
        g1.addAPlayer(p2);
        g1.addAPlayer(p1);
        assertNotEquals(g.hashCode(), g2.hashCode());
    }

    @Test
    void getQuestionTypes() {
        assertEquals(questionTypes, g.getQuestionTypes());
    }

    @Test
    void getActivities() {
        assertEquals(activities, g.getActivities());
    }

    @Test
    void getHashMapOfPlayers() {
        HashMap<Long, Player> playerList = new HashMap<>();
        playerList.put(p1.getId(), p1);
        g.addAPlayer(p1);
        assertEquals(playerList, g.getHashMapOfPlayers());
        playerList.put(p2.getId(), p2);
        g.addAPlayer(p2);
        assertEquals(playerList, g.getHashMapOfPlayers());
    }


    @Test
    void testToString() {
        HashMap<Long, Player> playerList = new HashMap<>();
        playerList.put(p1.getId(), p1);
        var id = g.getGameId();
        g.addAPlayer(p1);
        String result = "Game " + id + "{" +
                "players=" + playerList
                + '}';
        assertEquals(result, g.toString());
    }
}