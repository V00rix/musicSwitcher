package controllers;

import models.AudioFile;
import business.library.Library;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class LibraryController {
    // Properties

    @RequestMapping(value = "/library", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    ArrayList<AudioFile> library() {
        return Library.files;
    }
}
