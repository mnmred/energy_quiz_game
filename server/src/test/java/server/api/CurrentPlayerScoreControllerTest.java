package server.api;

import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Game;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrentPlayerScoreControllerTest {

    CurrentPlayerScoreController cp;
    Player p1;
    Player p2;
    Game g;

    @BeforeEach
    public void setup() {
        g = new Game();
        cp = new CurrentPlayerScoreController(g);
        p1 = new Player("Reinier", 50);
        p2 = new Player("Laimonas", 80);
    }

    @Test
    void getAll() {
        g.addAPlayer(p1);
        List<Player> PlayerList = new ArrayList<>();
        PlayerList.add(p1);
        assertEquals(PlayerList, cp.getAll().getBody());
    }

    @Test
    void getByPlayer() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        assertEquals(p1, cp.getByPlayer(p1.getPlayerName()).getBody());
    }

   /* @Test
    void getTop() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        Player a = new Player("a", 400);
        Player b = new Player("b", 300);
        Player c = new Player("c", 200);
        g.addAPlayer(b);
        g.addAPlayer(new Player("d", 100));
        g.addAPlayer(c);
        g.addAPlayer(a);
        List<Player> PlayerList = new ArrayList<>();
        PlayerList.add(a);
        PlayerList.add(b);
        PlayerList.add(c);
        assertEquals(PlayerList, cp.getTop(3).getBody());
    }*/

    @Test
    void add() {
        assertEquals(p1, cp.add(p1).getBody());
    }

    @Test
    void addPointsToAPlayer() {
        g.addAPlayer(p1);
        Player ptest = p1;
        ptest.addPoints(100);
        assertEquals(ptest, cp.addPointsToAPlayer("Reinier", 100).getBody());

    }

    /*@Test
    void deleteAll() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        List<Player> PlayerList = new ArrayList<>();
        assertEquals(true, cp.deleteAll().getBody());
        assertEquals(PlayerList, cp.getAll().getBody());
    }

    @Test
    void deleteByID() {
        g.addAPlayer(p2);
        g.addAPlayer(p1);
        List<Player> PlayerList = new ArrayList<>();
        PlayerList.add(p2);
        assertEquals(true, cp.deleteByName(p1.getPlayerName()).getBody());
        assertEquals(PlayerList, cp.getAll().getBody());
    }*/
}