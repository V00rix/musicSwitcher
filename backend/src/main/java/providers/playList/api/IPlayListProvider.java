package providers.playList.api;

import domain.AudioFile;
import domain.exeptions.BaseException;
import domain.exeptions.checks.BoundariesCheck;

import java.util.ArrayList;

/**
 * Play list provider API
 */
public interface IPlayListProvider {

    /**
     * Set up new list for playing
     *
     * @param fileList Files for play queue
     */
    void setList(ArrayList<AudioFile> fileList);

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
}
