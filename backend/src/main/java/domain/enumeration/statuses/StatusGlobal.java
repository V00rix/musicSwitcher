package domain.enumeration.statuses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.enumeration.statuses.IStatusCodes.Global;

/**
 * Global application status
 */
public final class StatusGlobal extends StatusBase {

    /**
     * Application initializing
     */
    @JsonIgnore
    public static final Status INITIALIZING = new Status("Application initializing", Global.INITIALIZING);

    /**
     * Application is alive and working
     */
    @JsonIgnore
    public static final Status OK = new Status("Application OK", Global.OK);

    /**
     * Application crashed
     */
    @JsonIgnore
    public static final Status CRASHED = new Status("Application crashed", Global.CRASHED);
}
