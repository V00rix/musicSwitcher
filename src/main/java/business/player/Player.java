package business.player;

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

public class Player extends Application {
    private static MediaPlayer player;
    private static Duration seekLocation = new Duration(0);

    public static void SetFile(File file) {
        SetFile(file.toURI().toString());
    }

    public static void SetFile(String fileUri) {
        System.out.println("File changed: " + fileUri);
        Media audio = new Media(fileUri);
        player = new MediaPlayer(audio);
    }

    public static void Play() {
        if (player != null) {
            player.seek(seekLocation);
            player.play();
        }
    }

    public static void Stop() {
        if (player != null) {
            seekLocation = new Duration(0);
            player.stop();
        }
    }

    public static void Pause() {
        if (player != null) {
            seekLocation = player.getCurrentTime();
            player.pause();
        }
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
