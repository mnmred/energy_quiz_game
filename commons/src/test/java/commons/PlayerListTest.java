package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerListTest {

    PlayerList pl;
    List<Player> listOfPlayers;
    public Player ps1;
    public Player ps2;

    @BeforeEach
    void init(){
        listOfPlayers = new ArrayList<>();
        ps1 = new Player("Martin", 2100);
        ps2 = new Player("Alex", 3250);
    }

    @Test
    void getListOfPlayers() {
        listOfPlayers.add(ps1);
        pl = new PlayerList(listOfPlayers);
        assertEquals(listOfPlayers, pl.getListOfPlayers());
    }

    @Test
    void setListOfPlayers() {
        pl = new PlayerList(listOfPlayers);
        List<Player> newList = new ArrayList<>();
        newList.add(ps2);
        pl.setListOfPlayers(newList);
        assertEquals(newList, pl.getListOfPlayers());
    }

    @Test
    void testEquals() {
        listOfPlayers.add(ps1);
        listOfPlayers.add(ps2);
        List<Player> newList = new ArrayList<>();
        newList.add(ps1);
        newList.add(ps2);
        pl = new PlayerList(listOfPlayers);
        PlayerList comp = new PlayerList(newList);
        assertEquals(pl, comp);
        newList.add(ps1);
        comp.setListOfPlayers(newList);
        assertNotEquals(pl, comp);
    }

    @Test
    void testHashCode() {
        listOfPlayers.add(ps1);
        listOfPlayers.add(ps2);
        List<Player> newList = new ArrayList<>();
        newList.add(ps1);
        newList.add(ps2);
        pl = new PlayerList(listOfPlayers);
        PlayerList comp = new PlayerList(newList);
        assertEquals(pl.hashCode(), comp.hashCode());
        newList.add(ps2);
        comp.setListOfPlayers(newList);
        assertNotEquals(pl.hashCode(), comp.hashCode());
    }
}