package components.audioPlayer.api;

import domain.AudioFile;

import java.io.File;
import java.net.URI;

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
    default void setFile(AudioFile file) {
        this.setFile(file.file.toURI().toString(), file.id);
    }

    /**
     * Set current file to be played
     *
     * @param fileUri File path
     */
    void setFile(String fileUri, int songId);
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
     * Set seconds
     *
     * @param seconds Milliseconds from start
     */
    void seek(int seconds);

    /**
     * Get seconds location
     *
     * @return Millis from start of playing
     */
    int getSeek();
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

    /**
     * Get current volume
     * @return Volume
     */
    double getVolume();
    //endregion
}
