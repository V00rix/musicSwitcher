import business.library.Library;
import business.player.PlayList;
import business.player.Player;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static business.utiliy.Status.SetStatus;
import static business.utiliy.Status.playerLatch;
import static business.utiliy.TimeTrack.Track;

@SpringBootApplication
@ComponentScan({"controllers"})
public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Launch REST services
        SpringApplication.run(Main.class, args);
        SetStatus("Spring application launched");

        // 2. Launch player thread
        Runnable task = () -> {
            SetStatus("Launching player");
            Application.launch(Player.class, args);
        };
        Thread thread = new Thread(task);
        thread.start();

        // 3. Initialize library
        Library library = new Library("C:/Users/vlado/Music");

        // 4. Read cache
        Track(() -> {
            library.ReadCache("app_data/metadata");
            return null;
        }, "Reading Cache");

        // 5. Set playlist
        playerLatch.await();
        PlayList.SetList(Library.files);

        // 6. Update library etc.
        Track(() -> {
            library.UpdateFiles();
            return null;
        }, "Updating library");

        // 7. Update playlist
        PlayList.SetList(Library.files);

        // 8. Update cache file
        Track(() -> {
            library.SaveCache("app_data/metadata");
            return null;
        }, "Saving Cache");
    }
}
