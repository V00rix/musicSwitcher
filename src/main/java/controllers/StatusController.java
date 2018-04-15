package controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class StatusController extends ControllerBase {

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    String status() {
        return this.statusProvider.getStatus();
    }
}
