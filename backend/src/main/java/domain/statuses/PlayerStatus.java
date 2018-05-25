package domain.statuses;

import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Player status response
 */
public class PlayerStatus {
    //region Fields
    /**
     * Volume
     */
    public double volume;

    /**
     * Song id
     */
    public int song;

    /**
     * Playlist (songs ids)
     */
    public ArrayList<Integer> playlist;
    /**
     * Playing position
     */
    public Pair<Integer, Integer> seek;

    /**
     * Playing or paused
     */
    public boolean isPlaying;
    //endregion

    /**
     * Status set/update
     *
     * @param volume    Player's volume
     * @param song      Current song order id
     * @param playlist  Current playlist's songs' ids
     * @param seek      Pair of seconds from playing start and full duration
     * @param isPlaying Flag if the control is playing
     */
    public void set(double volume, int song, ArrayList<Integer> playlist, Pair<Integer, Integer> seek, boolean isPlaying) {
        this.volume = volume;
        this.song = song;
        this.playlist = playlist;
        this.seek = seek;
        this.isPlaying = isPlaying;
    }
}

