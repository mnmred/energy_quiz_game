package commons;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Objects;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * A class to store scores of players in the database
 * An instance consists of a player name, the score obtained and the time it was achieved
 */
@Table
@Entity
public class Player implements Comparable<Player> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String playerName;
    private Integer score;
    private Timestamp time;

    public static Long playerID = 0L;

    @Transient
    private int rank;

    @SuppressWarnings("unused")
    Player() {
        // for object mapper
        //generateId();
        this.id = new Long(playerID);
        playerID++;
    }

    /**
     * Create a Player instance.
     *
     * @param playerName name of the player.
     * @param score      the score they scored in the game.
     */
    public Player(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.id = new Long(playerID);
        playerID++;
    }

    public Player(String playerName) {
        this.playerName = playerName;
        this.score = 0;
        this.id = new Long(playerID);
        playerID++;
    }

    /*public void generateId() {
        this.id = new Long(playerID);
        playerID++;
    }*/

    /**
     * Updating the score by adding points to it.
     *
     * @param points to be added to the player's score.
     */
    public void addPoints(int points) {
        score += points;
    }

    /**
     * Getter for the Player's id.
     *
     * @return the id of the Player instance.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the Player's id.
     *
     * @param id new id to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for player's name.
     *
     * @return the name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Getter for player's score.
     *
     * @return the score of the player.
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Setter for the player's score
     *
     * @param score of the player
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Getter for the time the score was achieved.
     *
     * @return the time the game was played.
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * Setter for time the player's score was achieved.
     *
     * @param time The new time to set.
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    /**
     * Compare whether two instances of a Player are equal.
     * All fields have to be equal for equality.
     *
     * @param o to be compared with.
     * @return whether the two objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(playerName, player.playerName)
                && Objects.equals(score, player.score) && Objects.equals(time, player.time);
    }

    /**
     * Get a hash code of the Player instance.
     *
     * @return the hash code of the Player instance.
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Get a string representation of the Player instance.
     * It is given in a multiline format.
     *
     * @return the string representation of the Player instance.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    @Override
    public int compareTo(Player o) {
        return Comparators.SCORE.compare(this,o);
    }

    public static class Comparators {

        public static Comparator<Player> SCORE = new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.getScore().compareTo(p2.getScore());
            }
        };
    }
}
