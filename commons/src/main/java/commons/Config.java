package commons;


public class Config {
    public static String styleSheet = "style.css";
    public static String title = "The Energy Quiz";
    public static String quit = "Sure you want to quit?";
    public static String titleWaitingRoom = "The Waiting Room";
    public static long timePerQuestion = 12000; //milliseconds
    public static long timeForIntermediate = 5000; //milliseconds, time the intermediate leaderboard shows
    public static long timeAnswerReveal = 5000; //milliseconds, time the revealed answers show\
    public static int totalQuestions = 20; //total amount of questions in the quiz, set to 2 for testing purposes
    public static String edit = "Edit activities";
    public static int maxChatMessages = 5;//maximum amount of chatmessages that can be in the chat at once
    public static int maxCharsUsername = 20; //maximum amount of characters a name can contain
    public static String port = "8080"; //port for server
    public static String server = "http://localhost:" + port + "/";
    public static int maxPointsPerQuestion = 200;
    public static int maxAmountOfNotifications = 3;
}
