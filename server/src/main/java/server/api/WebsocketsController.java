package server.api;

import commons.Emote;
import commons.NotificationMessage;
import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketsController {

    @Autowired
    PreGameController preGameController;

    public WebsocketsController(PreGameController preGameController) {
        this.preGameController = preGameController;
    }

    @MessageMapping("/waitingroom/start") // /app/waitingroom/start
    @SendTo("/topic/waitingroom/start")
    public Long startGame(Boolean b){
        //make game
        return preGameController.startMultiplayerGame();
    }


    @MessageMapping("/emote/{id}") // /app/emote/id
    @SendTo("/topic/emote/{id}")
    public Emote emote(Emote e){
        return e;
    }

    @MessageMapping("/notification/{id}") // /app/notification/id
    @SendTo("/topic/notification/{id}")
    public NotificationMessage leaveGame(NotificationMessage e){
        return e;
    }

    @MessageMapping("/useTimeJoker/{id}")
    @SendTo("/topic/timeJokerUsed/{id}")
    public Player useTimeJoker(Player player) {
        return player;
    }
}
