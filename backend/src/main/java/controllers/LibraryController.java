package controllers;

import domain.HttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import providers.library.api.ILibraryProvider;

/**
 * Library controller
 */
@RestController
public class LibraryController extends ControllerBase {
    /**
     * Return library data
     *
     * @return Array of audio playlist
     */
    @RequestMapping(value = "/library", method = RequestMethod.GET)
    public HttpResponse library() {
        return this.ok(this.libraryProvider.getFiles());
    }
}
