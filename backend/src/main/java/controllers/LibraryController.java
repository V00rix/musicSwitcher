package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

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
            return objectMapper.writeValueAsString(this.libraryProvider.getFiles());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
