package providers.player.implementation;

import business.audioPlayer.api.IAudioPlayer;
import business.audioPlayer.implementation.AudioPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import providers.IProviderBase;
import providers.player.api.IPlayerProvider;
import providers.status.api.IStatusProvider;

import java.io.File;
import java.util.concurrent.CountDownLatch;

//import static providers.status.api.implementation.StatusProvider.setStatus;
//import static providers.status.api.implementation.StatusProvider.playerLatch;
@Service
public class PlayerProvider implements IPlayerProvider, IProviderBase {
    private final IStatusProvider statusProvider;
    private final IAudioPlayer audioPlayer;

    @Autowired
    public PlayerProvider(IStatusProvider statusProvider) throws Exception {
        checkProviders(statusProvider);

        this.statusProvider = statusProvider;

        this.statusProvider.setStatus("Launching audio player");
        CountDownLatch latch = new CountDownLatch(1);
        this.audioPlayer = new AudioPlayer(this.statusProvider, latch);
        latch.await(); // wait till the player is initialized, running and responding

        this.statusProvider.setStatus("Audio player launched");
    }

    //    private static MediaPlayer player;
//    private static Duration seekLocation = new Duration(0);
//    private static String fileName;
//
//    public static void setFile(File file) {
//        setFile(file.toURI().toString());
//    }
//
//    public static void setFile(String fileUri) {
//        setStatus("File changed: " + fileUri);
//        fileName = fileUri;
//        Media audio = new Media(fileUri);
//        player = new MediaPlayer(audio);
//    }
//
//    public static void play() {
//        if (player != null) {
//            player.seek(seekLocation);
//            setStatus("Playing: " + fileName);
//            player.play();
//        }
//    }
//
//    public static void stop() {
//        if (player != null) {
//            seekLocation = new Duration(0);
//            setStatus("Ready to play music");
//            player.stop();
//        }
//    }
//
//    public static void pause() {
//        if (player != null) {
//            seekLocation = player.getCurrentTime();
//            setStatus("Paused: " + fileName);
//            player.pause();
//        }
//    }
//
    @Override
    public void setFile(File file) {

    }

    @Override
    public void setFile(String fileUri) {

    }

    @Override
    public void play() {
        this.audioPlayer.play();
    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }
}
