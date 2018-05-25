package providers.playList.api;

import domain.AudioFile;
import domain.exeptions.BaseException;
import domain.statuses.PlayerStatus;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Play list provider API
 */
public interface IPlayListProvider {
    /**
     * Set playlist with file ids
     * @param ids File ids
     */
    void setPlaylist(ArrayList<Integer> ids) throws BaseException;

    /**
     * Play or pause an audio file
     */
    void togglePlay();

    /**
     * Play next file in the queue
     */
    void playNext() throws BaseException;

    /**
     * Play previous file in the queue
     */
    void playPrevious() throws BaseException;

    /**
     * Change playing position and play file at index in current queue
     *
     * @param index Specified
     */
    void playFile(int index) throws BaseException;

    /**
     * Get full info package
     *
     * @return Full status package
     */
    PlayerStatus playerStatus();
}
