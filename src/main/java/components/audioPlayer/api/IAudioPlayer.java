package components.audioPlayer.api;

import java.io.File;

/**
 * Audio player JavaFx application
 */
public interface IAudioPlayer {
    /**
     * Set current file to be played
     * @param file
     */
    default void setFile(File file) {
        this.setFile(file.toURI().toString());
    }

    /**
     * Set current file to be played
     * @param fileUri
     */
    void setFile(String fileUri);

    /**
     * Play current file
     */
    void play();

    /**
     * Stop playing
     */
    void stop();

    /**
     * Pause playing
     */
    void pause();
}
