import business.library.Library;
import business.player.PlayList;
import business.player.Player;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static business.Status.SetStatus;
import static business.Status.playerLatch;

@SpringBootApplication
@ComponentScan({"controllers"})
public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        // Launch REST services
        SpringApplication.run(Main.class, args);
        SetStatus("Spring application launched");

        // Get library from cached library
        // todo

        // Assemble library
        Library library = new Library("C:/Users/vlado/Music");
        Library.files = library.ListFilesRecursively(null, "mp3");

        SetStatus("Library read");

        // Get Metadata, sort
        for (int i = 0; i < Library.files.length - 1; i++) {
//            Library.GetMetadata(Library.files[i]);
        }

        // Launch player thread
        Runnable task = () -> {
            SetStatus("Launching player");
            Application.launch(Player.class, args);
        };

        Thread thread = new Thread(task);
        thread.start();

        playerLatch.await();

        // Set basic playlist
        try {
            PlayList.SetList(Library.files);
            SetStatus("Basic playlist set");
        } catch (Exception e) {
            // Some playlist error... ehh?
            e.printStackTrace();
        }
    }
}
