package server.api;

import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.PlayerScoreRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/playerscore")
public class PlayerScoreController {

    private final PlayerScoreRepository repo;

    @Autowired
    public PlayerScoreController(PlayerScoreRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns all player scores stored in the database.
     *
     * @return list of all scores.
     */
    @GetMapping(path = "")
    public ResponseEntity<List<Player>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    /**
     * Get a player score from the database by id.
     *
     * @param id id of the requested player score.
     * @return the requested player.
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<Player> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getById(id));
    }

    /**
     * Get the top amount of player results with the highest scores.
     *
     * @param amount the requested number of best results, cannot be negative.
     * @return the list of results, list may be shorter if not enough results.
     */
    @GetMapping(path = "top/{amount}")
    public ResponseEntity<List<Player>> getTop(@PathVariable("amount") int amount) {
        if (amount < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Player> topScores = repo.findAll(
                Sort.by(Sort.Direction.DESC, "score"));
        topScores = topScores.stream()
                .limit(amount)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topScores);
    }

    /**
     * Add a player score to the database. The time of achieved score is assigned by the server.
     *
     * @param player result to be added to the database.
     * @return the score that was created.
     */
    @PostMapping(path = "")
    public ResponseEntity<Player> add(@RequestBody Player player) {
        if (player.getPlayerName() == null) {
            return ResponseEntity.badRequest().build();
        }
        player.setTime(Timestamp.from(Instant.now()));
        Player p = repo.save(player);
        return ResponseEntity.ok(p);
    }


    @PostMapping(path = "generatePlayer")
    public ResponseEntity<Player> generatePlayer(@RequestBody String name) {
        Player p = new Player(name);
        return ResponseEntity.ok(p);
    }

}
