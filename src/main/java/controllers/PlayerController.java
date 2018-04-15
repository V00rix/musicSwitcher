package controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController extends ControllerBase{
    // Properties

    @RequestMapping(value = "/play")
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    void play() {
        this.statusProvider.setStatus("Toggled play");
        this.playerProvider.play();
    }
//
//    @RequestMapping(value = "/play/next")
//    @CrossOrigin(origins = "http://localhost:8080")
//    public @ResponseBody
//    void next() {
//        PlayList.PlayNext();
//    }
//
//    @RequestMapping(value = "/play/previous")
//    @CrossOrigin(origins = "http://localhost:8080")
//    public @ResponseBody
//    void previous() {
//        PlayList.PlayPrevious();
//    }
}
