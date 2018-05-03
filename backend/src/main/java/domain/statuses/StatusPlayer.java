package domain.statuses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.enumeration.IStatusCodes.Player;

/**
 * Player status response
 */
public class StatusPlayer extends StatusBase {

    /**
     * No files to play
     */
    @JsonIgnore
    public static final Status NO_FILES = new Status("No audio files to play", Player.NO_FILES);

    /**
     * Playlist set
     */
    @JsonIgnore
    public static final Status PLAYLIST_SET = new Status("Playlist set", Player.PLAYLIST_SET);

    /**
     * Song changed
     */
    @JsonIgnore
    public static final Status FILE_CHANGED = new Status("Playing music", Player.FILE_CHANGED);

    /**
     * Song playing
     */
    @JsonIgnore
    public static final Status PLAYING = new Status("Playing music", Player.PLAYING);

    /**
     * Song paused
     */
    @JsonIgnore
    public static final Status PAUSED = new Status("Playing paused", Player.PAUSED);

    /**
     * Song stopped
     */
    @JsonIgnore
    public static final Status STOPPED = new Status("Playing stopped", Player.STOPPED);

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

    public StatusPlayer(Status status) {
        super(status);
    }

    public StatusPlayer(Status status, long seek, int volume, int song) {
        super(status);
        this.seek = seek;
        this.volume = volume;
        this.song = song;
    }
}

