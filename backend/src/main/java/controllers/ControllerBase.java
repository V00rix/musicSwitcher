package controllers;

import domain.HttpResponse;
import domain.exeptions.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import providers.library.api.ILibraryProvider;
import providers.playList.api.IPlayListProvider;
import providers.player.api.IPlayerProvider;
import providers.status.api.IStatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;

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
     * Status provider
     */
    @Autowired
    protected ITimeTrackProvider timeTrackProvider;

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

    /**
     * Play list provider
     */
    @Autowired
    protected ILibraryProvider libraryProvider;

    // todo: declare services here

    //region Response creators

    /**
     * Ok response
     *
     * @return Ok response
     */
    HttpResponse ok() {
        return new HttpResponse();
    }

    /**
     * Ok response
     *
     * @return Ok response
     */
    <T> HttpResponse ok(T data) {
        return new HttpResponse<>(data);
    }

    /**
     * Error response
     *
     * @return Error response
     */
    <T> HttpResponse err(T data) {
        return new HttpResponse<>(data);
    }

    /**
     * Error response
     *
     * @return Error response
     */
    HttpResponse err(Exception e) {
        return new HttpResponse<>(e);
    }

    /**
     * Error response
     *
     * @return Error response
     */
    HttpResponse err(BaseException e) {
        return new HttpResponse<>(e);
    }

    //endregion
}
