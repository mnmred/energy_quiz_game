package commons;

import java.util.List;
import java.util.Objects;

public class PlayerList {

    public List<Player> listOfPlayers;

    public PlayerList(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public PlayerList(){}

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerList)) return false;
        PlayerList that = (PlayerList) o;
        return Objects.equals(listOfPlayers, that.listOfPlayers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfPlayers);
    }
}
