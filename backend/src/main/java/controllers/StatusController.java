package controllers;

import domain.HttpResponse;
import domain.StatusPackage;
import org.springframework.web.bind.annotation.*;

/**
 * Status controller
 */
@RestController
@RequestMapping(value = "/status")
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
    @RequestMapping(value = "", method = RequestMethod.GET)
    public StatusPackage status() {
        return this.statusProvider.statusSmall();
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
    @RequestMapping(value = "/player", method = RequestMethod.GET)
    public HttpResponse player() {
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
