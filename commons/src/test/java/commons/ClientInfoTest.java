package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientInfoTest {

    public int currentQuestion;
    public Long gameID;
    public Player player;

    @BeforeEach
    void init(){
        currentQuestion=0;
        gameID = 1L;
    }

    @Test
    void constructorTest(){
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        assertNotNull(c);
        ClientInfo c1 = new ClientInfo();
        assertNotNull(c1);
        player =  new Player("John", 2137);
        ClientInfo c2 = new ClientInfo(player);
        assertNotNull(c2);
        ClientInfo c3 = new ClientInfo(gameID);
        assertNotNull(c3);
    }

    @Test
    void getCurrentQuestion() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        assertEquals(currentQuestion,c.getCurrentQuestion());
        player =  new Player("John", 2137);
    }

    @Test
    void getGameID() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        assertEquals(gameID,c.getGameID());
    }

    @Test
    void setRound() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        c.setRound(5);
        assertEquals(5,c.getCurrentQuestion());
    }

    @Test
    void setGameID() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        c.setGameID(4L);
        assertEquals(4L,c.getGameID());
    }

    @Test
    void setCurrentQuestion() {
        ClientInfo c = new ClientInfo(currentQuestion,gameID);
        c.setCurrentQuestion(55);
        assertEquals(55,c.getCurrentQuestion());
    }

    @Test
    void getPlayer() {
        ClientInfo c = new ClientInfo(gameID, player);
        assertEquals(player, c.getPlayer());
    }

    @Test
    void setPlayer() {
        ClientInfo c = new ClientInfo(gameID, player);
        Player p1 =  new Player("Martin", 217);
        c.setPlayer(p1);
        assertEquals(p1, c.getPlayer());
    }
}