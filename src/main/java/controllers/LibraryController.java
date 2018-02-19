package controllers;

import business.library.Library;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {
    // Properties

    @RequestMapping(value = "/library", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8080")
    public @ResponseBody
    List<String> library() {
        return Library.GetNames(Library.files);
    }
}
