package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ActivityService;
import server.MockActivityRepository;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectionControllerTest {


    ConnectionController cc;
    private ActivityService as;
    private MockActivityRepository repo;

    @BeforeEach
    public void setup() {
        repo = new MockActivityRepository();
        as = new ActivityService(repo);
        cc = new ConnectionController(as);
    }

    @Test
    void getAll() {
        assertEquals("<200 OK OK,pong,[]>", cc.ping().toString());
    }

}