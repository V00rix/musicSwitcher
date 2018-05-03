package components.audioPlayer.implementation;

import components.audioPlayer.api.IAudioPlayer;
import domain.exeptions.UnprovidedException;
import domain.statuses.StatusBase;
import domain.statuses.StatusPlayer;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import providers.IProviderBase;
import providers.status.api.IStatusProvider;

import java.util.concurrent.CountDownLatch;

/**
 * Audio player implementation
 */
public class AudioPlayer extends Application implements IAudioPlayer, IProviderBase {

    //region Fields

    //region Configuration

    private static final double volumeStep = 0.1;

    //endregion

    //region Providers

    private static IStatusProvider statusProvider;

    //endregion

    private static MediaPlayer player;
    private static CountDownLatch latch;
    private static Thread thread;
    private static String fileName;
    private static int songId;
    private static Duration seekLocation = new Duration(0);

    //endregion

    //region Constructors

    /**
     * Empty constructor for JavaFx application to run
     * THIS SHOULD NOT BE USED FROM OUTSIDE
     */
    @Deprecated
    public AudioPlayer() {
    }

    /**
     * (Re-) Initialize new audio player. Note: only one instance should run at a time
     *
     * @param _statusProvider IStatusProvider
     * @param _latch          CountDownLatch
     * @throws UnprovidedException UnprovidedException
     */
    public AudioPlayer(IStatusProvider _statusProvider, CountDownLatch _latch) throws Exception {
        // Close previous thread if is running
        stop(); // stops javaFx
        if (thread != null) {
            thread.interrupt(); // interrupt thread
        }

        // Check providers
        checkProviders(_statusProvider);
        statusProvider = _statusProvider;
        latch = _latch;
        (thread = new Thread(Application::launch)).start();
    }

    //endregion

    //region Implementation

    //region File control

    @Override
    public void start(Stage primaryStage) {
        latch.countDown();
    }

    @Override
    public void setFile(String fileUri, int songId) {
        System.out.println(fileUri);
        fileName = fileUri;
        Media audio = new Media(fileUri);
        player = new MediaPlayer(audio);

        statusProvider.setStatus(new StatusPlayer(StatusPlayer.FILE_CHANGED, 0, (int) (player.getVolume() * 10), AudioPlayer.songId = songId));
    }

    //endregion

    //region Play control

    @Override
    public void play(Runnable onEnd) {
        if (player != null) {
            player.setOnEndOfMedia(onEnd);

            player.seek(seekLocation);
            player.play();

            statusProvider.setStatus(new StatusPlayer(StatusPlayer.PLAYING, (long) seekLocation.toMillis(), (int) (player.getVolume() * 10), songId));
        }
    }

    @Override
    public void stop() {
        if (player != null) {
            player.setOnEndOfMedia(() -> {
            });

            seekLocation = new Duration(0);
            player.stop();

            statusProvider.setStatus(new StatusPlayer(StatusPlayer.STOPPED, (long) seekLocation.toMillis(), (int) (player.getVolume() * 10), -1));
        }
    }

    @Override
    public void pause() {
        if (player != null) {
            player.setOnEndOfMedia(() -> {
            });

            seekLocation = player.getCurrentTime();
            player.pause();

            statusProvider.setStatus(new StatusPlayer(StatusPlayer.PAUSED, (long) seekLocation.toMillis(), (int) (player.getVolume() * 10), songId));
        }
    }

    @Override
    public void seek(long millis) {
        long songDuration = (long) player.getMedia().getDuration().toMillis();
        seekLocation = new Duration(millis < 1 ? 0 : millis >= songDuration ? songDuration - 1 : millis);
    }

    //endregion

    //region Volume control

    @Override
    public void volume(double volume) {
        volume = (volume < 0) ? 0 : ((volume > 1) ? 1 : volume);
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

    //endregion

    //endregion
}
