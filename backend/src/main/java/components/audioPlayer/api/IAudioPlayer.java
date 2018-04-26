package components.audioPlayer.api;

import java.io.File;

/**
 * Audio player JavaFx application
 */
public interface IAudioPlayer {
    //region File control

    /**
     * Set current file to be played
     *
     * @param file File
     */
    default void setFile(File file) {
        this.setFile(file.toURI().toString());
    }

    /**
     * Set current file to be played
     *
     * @param fileUri File path
     */
    void setFile(String fileUri);

    //endregion

    //region Play control

    /**
     * Play current file
     */
    default void play() {
        this.play(() -> {
        });
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

    /**
     * Set seek
     *
     * @param millis Milliseconds from start
     */
    void seek(long millis);

    //endregion

    //region Volume control

    /**
     * Set player volume to direct value (e.g. via slider)
     *
     * @param volume New volume value
     */
    void volume(double volume);

    /**
     * Increase volume by step
     */
    void volumeIncrement();

    /**
     * Decrease volume by step
     */
    void volumeDecrement();

    //endregion
}
