package business.player;

import business.Status;
import business.library.Library;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static business.Status.SetStatus;
import static business.Status.playerLatch;

public class Player extends Application {
    private static MediaPlayer player;
    private static Duration seekLocation = new Duration(0);
    private static String fileName;

    public static void SetFile(File file) {
        SetFile(file.toURI().toString());
    }

    public static void SetFile(String fileUri) {
        SetStatus("File changed: " + fileUri);
        fileName = fileUri;
        Media audio = new Media(fileUri);
        player = new MediaPlayer(audio);
    }

    public static void Play() {
        if (player != null) {
            player.seek(seekLocation);
            SetStatus("Playing: " + fileName);
            player.play();
        }
    }

    public static void Stop() {
        if (player != null) {
            seekLocation = new Duration(0);
            SetStatus("Ready to play music");
            player.stop();
        }
    }

    public static void Pause() {
        if (player != null) {
            seekLocation = player.getCurrentTime();
            SetStatus("Paused: " + fileName);
            player.pause();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // todo: well, remember the TASK stuff you were talking about?
        // todo: so you set this thing down below
        SetStatus("Player Launched");
        playerLatch.countDown();
    }
}
