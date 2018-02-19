package controllers;

import business.Status;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatusController {
    // Properties

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    String status() {
        return Status.status;
    }
}
