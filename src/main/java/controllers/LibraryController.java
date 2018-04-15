package controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class LibraryController extends ControllerBase {
    // Properties
//
    @RequestMapping(value = "/library", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    String library() {
        return this.statusProvider.setStatus("status2");
    }
}
