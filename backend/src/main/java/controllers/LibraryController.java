package controllers;

import org.springframework.web.bind.annotation.*;

/**
 * Library controller
 */
@RestController
public class LibraryController extends ControllerBase {
    /**
     * Return library data
     * @return TODO
     */
    @RequestMapping(value = "/library", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    String library() {
        // TODO
        return this.statusProvider.setStatus("status2");
    }
}
