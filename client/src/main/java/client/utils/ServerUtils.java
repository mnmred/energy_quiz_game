/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static commons.Config.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.lang.reflect.Type;

import commons.*;
import commons.Activity;
import commons.Player;
import commons.PostActivity;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ServerUtils {

    public static String SERVER;
    public static String wsSERVER;

    public Long requestGameID() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/play/getGameID") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Long>() {});
    }

    public String ping() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/connection/ping") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<String>() {});
    }

    public Long start(Player player) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/play/start/single") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(player, APPLICATION_JSON), Long.class);
    }


    public Integer getQuestionType(int currentQuestion, Long gameID) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/play/getQuestionType") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(new ClientInfo(currentQuestion, gameID), APPLICATION_JSON), Integer.class);
    }

    public ActivityList get4Activities(int currentQuestion, Long gameID) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/play/get4Activities") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(new ClientInfo(currentQuestion, gameID), APPLICATION_JSON), ActivityList.class);
    }

    public Activity getSingleActivity(int currentQuestion, Long gameID) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/play/getSingleActivity") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(new ClientInfo(currentQuestion, gameID), APPLICATION_JSON), Activity.class);
    }

    public Boolean updateScore(Long gameID, Player player) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/play/updateScore") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(new ClientInfo(gameID, player), APPLICATION_JSON), Boolean.class);
    }

    public PlayerList getPlayers(Long gameID) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/play/getPlayers") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(new ClientInfo(gameID), APPLICATION_JSON), PlayerList.class);
    }

    public Player generatePlayer(String name) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/playerscore/generatePlayer") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(name, APPLICATION_JSON), Player.class);
    }

    /*public void startMultiplayer() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/play/startMultiplayer") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {});
    }*/

    /*public Integer getTypeOfQuestion(int round){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/activity/getQuestion") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(round, APPLICATION_JSON), Integer.class);
    }*/

    /**
     * @param player the name with which the player wants to play singleplayer
     * @return true if the server accepts
     */
    public boolean startSingle(Player player) {
        return askConfirmation("api/play/single", player);
    }

    /**
     * @param player the name with which the player wants to join the waiting room
     * @return true if the server accepts
     */
    public boolean enterWaitingRoom(Player player) {
        return askConfirmation("api/play/join", player);
    }

    /**
     * @param path where to send the request
     * @return true if request is ok
     */
    public boolean askConfirmation(String path, Player player){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(path) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(player, APPLICATION_JSON), Boolean.class);
    }

    /**
     * @return the list of waiting players
     */
    public List<Player> getWaitingPlayers() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/play/waitingroom") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Player>>() {});
    }

    /**
     * @param player that is going to leave
     */
    public void leaveWaitingroom(Player player) {
         ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/play/waitingroom/leave") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(player, APPLICATION_JSON), Boolean.class);
    }

    /**
     * @return the updated list of the players when something has changed
     */
    public List<Player> pollWaitingroom() {
        return ClientBuilder.newClient(new ClientConfig()) //F
                .target(SERVER).path("api/play/waitingroom/poll") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Player>>() {});
    }

    /**
     * @param Player added to the leaderboard
     * @return the player added
     */
    public Player addPlayerToSPLeaderboard(Player Player) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/playerscore") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(Player, APPLICATION_JSON), Player.class);

    }

    /**
     * @return the list of waiting players
     */
    public List<Player> getPlayersInSPL() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/playerscore") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Player>>() {});
    }

    public String activateHint() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/joker/hint") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<String>() {});
    }


    /**
     * @param activity is the activity to try to add to the database
     * @return the updated list of the players when something has changed
     */
    public Activity addPostActivity(PostActivity activity) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/activity/add") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(activity, APPLICATION_JSON), Activity.class);
    }

    /**
     * @return all activities
     */
    public List<Activity> getActivities() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activity") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Activity>>() {
                });
    }


    /**
     * @param postActivity is the activity and image to be added to the server
     * @return the newly added activity
     */
    public Activity updatePostActivity(PostActivity postActivity) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/activity/update") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(postActivity, APPLICATION_JSON), Activity.class);
    }

    /**
     * @param id is the id of the activity to be deleted and its image from the server files
     * @return true if successful
     */
    public Boolean deletePostActivity(Long id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/api/activity/delete") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(id, APPLICATION_JSON), Boolean.class);
    }

    private StompSession session;

    public void connect(){
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try{
            session = stomp.connect(wsSERVER, new StompSessionHandlerAdapter() {}).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public <T> StompSession.Subscription registerForMessages(String dest, Class<T> type ,Consumer<T> consumer){
        if(!isConnected())
            connect();
        return session.subscribe(dest, new StompFrameHandler(){
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    public void send(String dest, Object o){
        session.send(dest, o);
    }

    public void unsubscribe(StompSession.Subscription subscription){
        subscription.unsubscribe();
    }

    public void disconnect(){
        session.disconnect();
    }

    public boolean isConnected() {
        if(session == null)
            return false;

        return session.isConnected();
    }

    public static void setSERVER(String url) {
        SERVER = url;
        wsSERVER = "ws" + url.substring(4) + "websocket";
    }
}