package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import server.ActivityService;

abstract class BaseController {


    @Autowired
    protected ActivityService activityService;


    public BaseController(ActivityService activityService) {
        this.activityService = activityService;
    }
}
