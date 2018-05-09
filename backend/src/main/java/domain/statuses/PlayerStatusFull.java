package domain.statuses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.enumeration.IStatusCodes.Player;

import java.util.ArrayList;

/**
 * Player status response
 */
public class PlayerStatusFull {
    //region Fields
    /**
     * Volume
     */
    public int volume;

    /**
     * Song id
     */
    public int song;

    /**
     * Playlist (songs ids)
     */
    public ArrayList<Integer> files;

    /**
     * Playing or paused
     */
    public boolean isPlaying;
    //endregion
}

