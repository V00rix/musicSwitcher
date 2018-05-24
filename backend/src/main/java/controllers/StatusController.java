package controllers;

import domain.HttpResponse;
import javafx.util.Pair;
import org.springframework.web.bind.annotation.*;

/**
 * Status controller
 */
@RestController
@RequestMapping(value = "/api/status")
public class StatusController extends ControllerBase {
    /*
     * 0. Get synchronization state (app availability etc.) FE ? (Http had response...)
     * 1. Get global status (main thread name/title, status)
     * 2. Get player status (seek location update)
     */

    /**
     * Get current status
     * @return Status as string
     */
    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public Pair<Long, Long> status() {
        return this.playListProvider.playerStatusSmall();
    }

    /**
     * Get current status
     * @return Status as string
     */
    @RequestMapping(value = "/player/full", method = RequestMethod.GET)
    public HttpResponse player() {
        return this.ok(this.playListProvider.playerStatusFull());
    }

    /**
     * Get current status
     * @return Status as string
     */
    @RequestMapping(value = "/global", method = RequestMethod.GET)
    public HttpResponse global() {
        return this.ok(this.statusProvider.getStatus());
    }

    /**
     * Get current status
     * @return Status as string
     */
    @RequestMapping(value = "/library", method = RequestMethod.GET)
    public HttpResponse library() {
        return this.ok(this.statusProvider.getStatus());
    }
}
