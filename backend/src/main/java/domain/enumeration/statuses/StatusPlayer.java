package domain.enumeration.statuses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.enumeration.statuses.IStatusCodes.Player;

/**
 * Player status response
 */
public final class StatusPlayer extends StatusBase {

    /**
     * Song playing
     */
    @JsonIgnore
    private static final Status PLAYING = new Status("Playing music", Player.PLAYING);

    /**
     * Song paused
     */
    @JsonIgnore
    private static final Status PAUSED = new Status("Playing paused", Player.PAUSED);

    /**
     * Song stopped
     */
    @JsonIgnore
    private static final Status STOPPED = new Status("Playing stopped", Player.STOPPED);

    /**
     * Seek location
     */
    public long seek;

    /**
     * Volume
     */
    public int volume;

    /**
     * Song id
     */
    public int song;
}

