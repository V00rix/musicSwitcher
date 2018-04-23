package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.AudioFile;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Library controller
 */
@RestController
public class LibraryController extends ControllerBase {
    /**
     * Return library data
     *
     * @return TODO
     */
    @RequestMapping(value = "/library", method = RequestMethod.GET)
    public @ResponseBody
    String library() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.libraryProvider.getFiles().sort((a1, a2) -> {
                return (a1.title == null) ? ((a2.title == null) ? 1 : -1) : ((a2.title == null) ? 1 : a1.title.compareTo(a2.title));
            });
            return objectMapper.writeValueAsString(this.libraryProvider.getFiles());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
