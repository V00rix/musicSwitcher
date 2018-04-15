package components.audioPlayer.implementation;

import components.audioPlayer.api.IAudioPlayer;
import domain.exeptions.UnprovidedException;
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

    //region Providers

    private static IStatusProvider statusProvider;

    //endregion

    private static MediaPlayer player;
    private static CountDownLatch latch;
    private static Thread thread;
    private static String fileName;
    private static Duration seekLocation = new Duration(0);

    //endregion

    //region Constructors

    /**
     * Empty constructor for JavaFx application to run
     * THIS SHOULD NOT BE USED FROM OUTSIDE
     */
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

    @Override
    public void start(Stage primaryStage) {
        latch.countDown();
    }

    @Override
    public void setFile(String fileUri) {
        statusProvider.setStatus("File changed: " + fileUri);
        fileName = fileUri;
        Media audio = new Media(fileUri);
        player = new MediaPlayer(audio);
    }

    @Override
    public void play() {
        if (player != null) {
            player.seek(seekLocation);
            statusProvider.setStatus(("Playing: " + fileName));
            player.play();
        }
    }

    @Override
    public void stop() {
        if (player != null) {
            seekLocation = new Duration(0);
            statusProvider.setStatus("Ready to play music");
            player.stop();
        }
    }

    @Override
    public void pause() {
        if (player != null) {
            seekLocation = player.getCurrentTime();
            statusProvider.setStatus(("Paused: " + fileName));
            player.pause();
        }
    }

    //endregion
}
