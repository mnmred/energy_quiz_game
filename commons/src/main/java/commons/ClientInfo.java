package commons;


public class ClientInfo {
    private int currentQuestion;
    private Long gameID;
    private Player player;

    public ClientInfo() {
    }

    public ClientInfo(Long gameID) {
        this.gameID = gameID;
    }

    public ClientInfo(Long gameID, Player player) {
        this.gameID = gameID;
        this.player = player;
    }

    public ClientInfo(Player player) {
        this.player = player;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ClientInfo(int currentQuestion, Long gameID) {
        this.currentQuestion = currentQuestion;
        this.gameID = gameID;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setRound(int round) {
        this.currentQuestion = round;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
}
