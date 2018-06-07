package components.audioPlayer.implementation;

import components.audioPlayer.api.IAudioPlayer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.concurrent.CountDownLatch;

/**
 * Audio control implementation
 */
public class AudioPlayer extends Application implements IAudioPlayer {
    //region Fields
    //region Configuration
    private static final double volumeStep = 0.1;
    //endregion

    //endregion
    public static CountDownLatch latch = new CountDownLatch(1);
    public static IAudioPlayer instance;

    private static MediaPlayer player;
    private static Thread thread;
    private static double volume = 0.8;
    private static int songId;
    private static Duration seekLocation = new Duration(0);
    //endregion

    public AudioPlayer() {
    }

    //region Implementation
    //region File control
    @Override
    public void start(Stage primaryStage) {
        instance = this;
        latch.countDown();
    }

    @Override
    public void setFile(String fileUri, int songId) {
        System.out.println(fileUri);
        Media audio = new Media(fileUri);

        player = new MediaPlayer(audio);
        seek(0);
        volume(volume);
    }
    //endregion

    //region Play control
    @Override
    public void play(Runnable onEnd) {
        if (player != null) {
            player.setOnEndOfMedia(onEnd);

            player.seek(seekLocation);
            player.play();
        }
    }

    @Override
    public void stop() {
        if (player != null) {
            player.setOnEndOfMedia(() -> {
            });

            seekLocation = new Duration(0);
            player.stop();
        }
    }

    @Override
    public void pause() {
        if (player != null) {
            player.setOnEndOfMedia(() -> {
            });

            seekLocation = player.getCurrentTime();
            player.pause();
        }
    }

    @Override
    public void seek(int seconds) {
        int songDuration = (int) player.getMedia().getDuration().toSeconds();
        seekLocation = new Duration((seconds < 1 ? 0 : seconds >= songDuration ? songDuration - 1 : seconds) * 1000);
        player.seek(seekLocation);
    }

    @Override
    public Pair<Integer, Integer> getSeek() {
        if (player != null) {
            return new Pair<Integer, Integer>((int) player.currentTimeProperty().get().toSeconds(), (int) player.getTotalDuration().toSeconds());
        }
        return null;
    }
    //endregion

    //region Volume control
    @Override
    public void volume(double vol) {
        volume = (vol < 0) ? 0 : ((vol > 1) ? 1 : vol);
        player.setVolume(volume);
    }

    @Override
    public void volumeIncrement() {
        volume(player.getVolume() + volumeStep);
    }

    @Override
    public void volumeDecrement() {
        volume(player.getVolume() - volumeStep);
    }

    @Override
    public double getVolume() {
        return player != null ? player.getVolume() : 0;
    }

    @Override
    public void terminate() {
        Platform.exit();
    }
    //endregion
    //endregion
}
