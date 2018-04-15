package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import providers.playList.api.IPlayListProvider;
import providers.player.api.IPlayerProvider;
import providers.status.api.IStatusProvider;

/**
 * Base REST controller class
 */
@RestController
public abstract class ControllerBase {

    /**
     * Status provider
     */
    @Autowired
    protected IStatusProvider statusProvider;

    /**
     * Audio player provider
     */
    @Autowired
    protected IPlayerProvider playerProvider;

    /**
     * Play list provider
     */
    @Autowired
    protected IPlayListProvider playListProvider;

    // todo: declare services here
}
