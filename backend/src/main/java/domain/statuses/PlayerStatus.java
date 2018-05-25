package domain.statuses;

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
    public int seconds;

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
     * @param seconds      Milliseconds from playing start
     * @param isPlaying Flag if the player is playing
     */
    public void set(double volume, int song, ArrayList<Integer> playlist, int seconds, boolean isPlaying) {
        this.volume = volume;
        this.song = song;
        this.playlist = playlist;
        this.seconds = seconds;
        this.isPlaying = isPlaying;
    }
}

