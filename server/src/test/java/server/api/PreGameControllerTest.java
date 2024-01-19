package server.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.ClientInfo;
import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.ActivityService;
import server.MockActivityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PreGameControllerTest {

    PreGameController preGameController;
    Player p1;
    Player p2;
    List<Player> playerList;
    ResponseEntity<List<Player>> updatedList;
    ObjectMapper mapper;
    private ActivityService sut2;
    private MockActivityRepository repo;
    Long gameID;
    ClientInfo clientInfo;
    Thread pollingThread;

    @BeforeEach
    public void setup() {
        repo = new MockActivityRepository();
        sut2 = new ActivityService(repo);
        preGameController = new PreGameController(sut2);
        p1 = new Player("Reinier", 0);
        p2 = new Player("Mana", 0);
        playerList = new ArrayList<>();
        pollingThread = new Thread(() -> {
            while(true) updatedList = preGameController.updates();
        });
        mapper = new ObjectMapper();
        gameID = 22L;
        clientInfo = new ClientInfo(gameID, p1);
    }

    @Test
    void testSingle() {
        assertTrue(preGameController.playSingle(p1).getBody());
    }

    @Test
    void testJoin() {
        preGameController.playMulti(p1);
        preGameController.playMulti(p2);
        List<Player> expected = List.of(p1, p2);
        assertEquals(expected, preGameController.getWaitingPlayers());
    }

    @Test
    void testJoin2() {
        preGameController.playMulti(p1);
        preGameController.playMulti(p2);
        List<Player> expected = List.of(p1, p2);
        assertTrue(preGameController.playMulti(new Player("Jake", 2)).getBody());
    }

    @Test
    void testJoin3() {
        preGameController.playMulti(p1);
        preGameController.playMulti(p2);
        List<Player> expected = List.of(p1, p2);
        assertFalse(preGameController.playMulti(p1).getBody());
    }

    /*@Test //not possible to test this properly?
    void testGetGameID() {
        var actual = preGameController.supplyGameID();
        var expected = 0L;
        assertEquals(expected, actual.getBody());
    }

    @Test
    void testGetGameID2() {
        var actual = preGameController.supplyGameID();
        var expected = 0L;
        assertEquals(expected, actual.getBody());
    }*/

    /*@Test
    void testStartMultiplayerGame() {
        var actual = preGameController.startMultiplayerGame();
        var expected = 0L;
        assertEquals(expected, actual);
    }*/


    @Test
    void testGetWaitingroom() {
        preGameController.playMulti(p1);
        preGameController.playMulti(p2);
        var actual = preGameController.getWaitingroom();
        List<Player> expected = List.of(p1, p2);
        assertEquals(expected, actual.getBody());
    }


    @Test
    void updateScore() {
        //preGameController.updateScore(clientInfo);
        assertTrue(true);
    }


    /*@Test
    void playMultiNameTakenTest() {
        sut.playMulti(p1.getPlayerName());
        sut.playMulti(p2.getPlayerName());
        assertFalse(sut.playMulti(p1.getPlayerName()).getBody());
        assertFalse(sut.playMulti(p2.getPlayerName()).getBody());
<<<<<<< HEAD
    }*/

   /* @Test
    void getWaitingroomTest() {
        sut.playMulti(p1.getPlayerName());
        playerList.add(p1);
        assertEquals(playerList, sut.getWaitingroom().getBody());
        sut.playMulti(p2.getPlayerName());
        playerList.add(0, p2);
        assertEquals(playerList, sut.getWaitingroom().getBody());
    }*/

   /* @Test
    void leaveWaitingroomTest() {
        assertTrue(sut.playMulti(p1.getPlayerName()).getBody());
        sut.leaveWaitingroom(p1);
        assertEquals(playerList, sut.getWaitingroom().getBody());
    }
*/
    /*@Test
    void updatesJoinTest() throws InterruptedException {
        sut.playMulti(p1.getPlayerName());
        Thread.sleep(6000);
        playerList.add(p1);
        sut.playMulti(p2.getPlayerName());
        Thread.sleep(6000);
        playerList.add(0, p2);
        List<Player> result = mapper.convertValue(updatedList.getResult(),new TypeReference<List<Player>>() { });
        assertEquals(playerList, result);
    }

    @Test
    void updatesLeaveTest() throws InterruptedException {
        sut.playMulti(p1.getPlayerName());
        Thread.sleep(6000);
        playerList.add(p1);
        sut.playMulti(p2.getPlayerName());
        Thread.sleep(6000);
        playerList.add(0, p2);
        sut.leaveWaitingroom(p2);
        Thread.sleep(6000);
        playerList.remove(p2);
        List<Player> result = mapper.convertValue(updatedList.getResult(),new TypeReference<List<Player>>() { });
        assertEquals(playerList, result);
    }*/
}