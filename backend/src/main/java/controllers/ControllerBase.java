package controllers;

import components.util.IRichConsole;
import domain.HttpResponse;
import domain.exeptions.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import providers.library.api.ILibraryProvider;
import providers.playList.api.IPlayListProvider;
import providers.control.api.IControlProvider;
import providers.status.api.IStatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;

/**
 * Base REST controller class
 */
@RequestMapping(value="/api")
public abstract class ControllerBase implements IRichConsole {
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
     * Audio control provider
     */
    @Autowired
    protected IControlProvider playerProvider;

    /**
     * Play list provider
     */
    @Autowired
    protected ILibraryProvider libraryProvider;

    /**
     * Play list provider
     */
    @Autowired
    protected IPlayListProvider playListProvider;


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
    HttpResponse err(BaseException e) {
        return new HttpResponse<>(e);
    }

    /**
     * Handler for non-base exceptions
     * @param e Exception
     * @return HttpResponse
     */
    @ExceptionHandler(Exception.class)
    public HttpResponse handleException(Exception e) {
        e.printStackTrace();
        return new HttpResponse<>(new BaseException(e));
    }

    //endregion
}
