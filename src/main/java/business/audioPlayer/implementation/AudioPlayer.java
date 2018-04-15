package business.audioPlayer.implementation;

import business.audioPlayer.api.IAudioPlayer;
import domain.exeptions.UnprovidedException;
import javafx.application.Application;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import providers.IProviderBase;
import providers.status.api.IStatusProvider;

import java.util.concurrent.CountDownLatch;

/**
 * Audio player implementation
 */
public class AudioPlayer extends Application implements IAudioPlayer, IProviderBase {
    private static IStatusProvider statusProvider;
    private static MediaPlayer player;
    private static CountDownLatch latch;
    private static Thread thread;

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
        this.stop(); // stops javaFx
        if (thread != null) {
            thread.interrupt(); // interrupt thread
        }

        // Check providers
        checkProviders(_statusProvider);
        statusProvider = _statusProvider;
        latch = _latch;
        (thread = new Thread(Application::launch)).start();
    }

    @Override
    public void start(Stage primaryStage) {
        latch.countDown();
    }

    @Override
    public void play() {
        statusProvider.setStatus("Playing");
        // todo
    }
}
