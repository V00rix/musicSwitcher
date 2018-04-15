package controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Music player controller
 */
@RestController
public class PlayerController extends ControllerBase {

    /**
     * Toggle play/pause
     */
    @RequestMapping(value = "/play")
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    void play() {
        this.playListProvider.togglePlay();
    }

    /**
     * Play next track
     */
    @RequestMapping(value = "/play/next")
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    void next() {
        this.playListProvider.playNext();
    }

    /**
     * Play previous track
     */
    @RequestMapping(value = "/play/previous")
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    void previous() {
        this.playListProvider.playPrevious();
    }
}
