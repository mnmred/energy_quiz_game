package server.api;

import commons.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import server.MockPlayerScoreScoreRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerScoreControllerTest {

    private MockPlayerScoreScoreRepository repo;
    private PlayerScoreController sut;

    private Player ps1;
    private Player ps2;

    @BeforeEach
    public void setup() {
        repo = new MockPlayerScoreScoreRepository();
        sut = new PlayerScoreController(repo);
        ps1 = new Player("ps1", 1);
        ps2 = new Player("ps2", 2);
    }

    @Test
    void testRepoUsed() {
        sut.add(ps1);
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    void testAdd() {
        sut.add(ps1);
        repo.exists(Example.of(ps1));
    }

    @Test
    void testGetAll() {
        sut.add(ps1);
        sut.add(ps2);
        var scores = sut.getAll().getBody();
        assertTrue(scores.contains(ps1));
        assertTrue(scores.contains(ps2));
    }

    @Test
    void testGetById() {
        sut.add(ps1);
        sut.add(ps2);
        assertEquals(ps1, sut.getById(ps1.getId()).getBody());
        assertEquals(ps2, sut.getById(ps2.getId()).getBody());
    }

    @Test
    void testGetTop() {
        Player ps3 = new Player("ps3", 3);
        sut.add(ps1);
        sut.add(ps3);
        sut.add(ps2);
        assertEquals(List.of(), sut.getTop(0).getBody());
        assertEquals(List.of(ps3), sut.getTop(1).getBody());
        assertEquals(List.of(ps3, ps2), sut.getTop(2).getBody());
        assertEquals(List.of(ps3, ps2, ps1), sut.getTop(3).getBody());
        assertEquals(List.of(ps3, ps2, ps1), sut.getTop(4).getBody());
    }
}