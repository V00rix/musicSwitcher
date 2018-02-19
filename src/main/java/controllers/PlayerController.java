package controllers;

import business.player.PlayList;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {
    // Properties

    @RequestMapping(value = "/play")
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    void play() {
        PlayList.TogglePlay();
    }

    @RequestMapping(value = "/play/next")
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    void next() {
        PlayList.PlayNext();
    }

    @RequestMapping(value = "/play/previous")
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    void previous() {
        PlayList.PlayPrevious();
    }
}
