import business.library.Library;
import business.player.PlayList;
import business.player.Player;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

import static business.Status.SetStatus;

@SpringBootApplication
@ComponentScan({"controllers"})
public class Main {
    public static void main(String[] args) throws InterruptedException {
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
        // TODO
        Library.GetMetadata(Library.files[0]);


        // Launch player thread
        Runnable task = () -> {
            SetStatus("Launching player");
            Application.launch(Player.class, args);
        };

        Thread thread = new Thread(task);
        thread.start();

        // Wait one second to make sure player has launched... todo: wait, what?
        // I should actually create some task (promise) and react on complete
        // and place it somewhere at, say, constructor on a high level functionality
        TimeUnit.SECONDS.sleep(1);
        SetStatus("Player Launched");


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
