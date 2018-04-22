package components.audioPlayer.api;

import java.io.File;

/**
 * Audio player JavaFx application
 */
public interface IAudioPlayer {
    /**
     * Set current file to be played
     * @param file File
     */
    default void setFile(File file) {
        this.setFile(file.toURI().toString());
    }

    /**
     * Set current file to be played
     * @param fileUri File path
     */
    void setFile(String fileUri);

    /**
     * Play current file
     */
    default void play() {
        this.play(() -> {});
    }

    /**
     * Play current file
     */
    void play(Runnable onEnd);

    /**
     * Stop playing
     */
    void stop();

    /**
     * Pause playing
     */
    void pause();
}
