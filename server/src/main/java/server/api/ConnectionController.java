package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.ActivityService;

/**
 * Controller for testing network connections with clients.
 */
@RestController
@RequestMapping("/api/connection")
public class ConnectionController extends BaseController {


    @Autowired
    public ConnectionController(ActivityService activityService) {
        super(activityService);
    }

    @GetMapping(path = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

}