import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import providers.player.api.IPlayerProvider;
import providers.status.api.IStatusProvider;

@SpringBootApplication
@ComponentScan({"controllers", "config"})
public class Main {
    public static void main(String[] args) {

        // Launch spring application
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);

        // Get beans reference
        IStatusProvider statusProvider = applicationContext.getBean(IStatusProvider.class);
        IPlayerProvider playerProvider = applicationContext.getBean(IPlayerProvider.class);

        statusProvider.setStatus("Application launched");

        // 1. Launch REST services
//        SpringApplication.run(Main.class, args);

//        // 2. Launch gui
//        // todo
//
//        // 3. Launch player thread
//        Runnable task = () -> {
//            statusProvider.setStatus("Launching player");
//            Application.launch(TestClass.class, args);
//        };
//        Thread thread = new Thread(task);
//        thread.start();
//
//        // 4. Initialize library
//        Library library = new Library("C:/Users/vlado/Music");
//
//        // 5. Read cache
//        Track(() -> {
//            library.ReadCache("app_data/metadata");
//            return null;
//        }, "Reading Cache");
//
//        // 6. Set playlist
//        playerLatch.await();
//        PlayList.SetList(Library.files);
//
//        // 7. Update library etc.
//        Track(() -> {
//            library.UpdateFiles();
//            return null;
//        }, "Updating library");
//
//        // 8. Update playlist
//        PlayList.SetList(Library.files);
//
//        // 9. Update cache file
//        Track(() -> {
//            library.SaveCache("app_data/metadata");
//            return null;
//        }, "Saving Cache");
    }
}
