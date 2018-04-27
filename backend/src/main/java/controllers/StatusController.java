package controllers;

import domain.HttpResponse;
import domain.enumeration.ErrorCodes;
import domain.enumeration.Severities;
import domain.exeptions.BaseException;
import org.springframework.web.bind.annotation.*;

/**
 * Status controller
 */
@RestController
public class StatusController extends ControllerBase {

    /**
     * Get current status
     * @return Status as string
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public HttpResponse status() {
        return this.ok(this.statusProvider.getStatus());
    }
}
