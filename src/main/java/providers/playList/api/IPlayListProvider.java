package providers.playList.api;

import domain.AudioFile;

import java.util.ArrayList;

/**
 * Play list provider API
 */
public interface IPlayListProvider {

    /**
     * Set up new list for playing
     *
     * @param fileList Files for play queue
     * @throws Exception Exception
     */
    void setList(ArrayList<AudioFile> fileList) throws Exception;

    /**
     * Play or pause an audio file
     */
    void togglePlay();

    /**
     * Play next file in the queue
     */
    void playNext();

    /**
     * Play previous file in the queue
     */
    void playPrevious();
}
