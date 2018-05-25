import components.audioPlayer.implementation.AudioPlayer;
import components.gui.controlWindow.implementation.ControlWindow;
import domain.AppConfig;
import domain.AudioFile;
import domain.statuses.ApplicationStatus;
import javafx.application.Application;
import org.apache.tika.exception.TikaException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.xml.sax.SAXException;
import providers.library.api.ILibraryProvider;
import providers.playList.api.IPlayListProvider;
import providers.control.api.IControlProvider;
import providers.status.api.IStatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ComponentScan({"controllers", "config"})
public class Main {
    //region Defaults
    private static final String configPath = "backend/app_data/config";
    private static final String cachePath = "backend/app_data/metadata";
    //endregion

    public static void main(String[] args) throws Exception {

        //region Basic initialization
        new Thread(() -> Application.launch(ControlWindow.class)).start();
        AudioPlayer.latch.await();
        var controlWindow = ControlWindow.instance;
        controlWindow.setOnExit(() -> {
            // todo: save data, etc.
            System.exit(0);
        });

        AppConfig config = new AppConfig();

        var latch = new CountDownLatch(1);

        AppConfig finalConfig = config;
        controlWindow.setOnDirectoryChanged(newDir -> {
            finalConfig.rootPath = newDir;

            File f = new File(configPath);
            if (f.exists()) {
                f.delete();
            }
            try {
                f.createNewFile();
                FileOutputStream fos = null;
                fos = new FileOutputStream(configPath);
                ObjectOutputStream oos = null;
                oos = new ObjectOutputStream(fos);
                oos.writeObject(finalConfig);
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            latch.countDown();
        });


        if (new File(configPath).exists()) {
            latch.countDown();
            FileInputStream fis = new FileInputStream(configPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            config = (AppConfig) ois.readObject();
        } else {
            controlWindow.changeDirectory();
            config = finalConfig;
            System.out.println(config);
        }

        latch.await();
        //endregion

        //region Launch spring services
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        //endregion

        //region Get beans reference
        IStatusProvider statusProvider = applicationContext.getBean(IStatusProvider.class);
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.INITIALIZING));

        IControlProvider playerProvider = applicationContext.getBean(IControlProvider.class);
        ILibraryProvider libraryProvider = applicationContext.getBean(ILibraryProvider.class);
        ITimeTrackProvider timeTrackProvider = applicationContext.getBean(ITimeTrackProvider.class);
        IPlayListProvider playListProvider = applicationContext.getBean(IPlayListProvider.class);
        //endregion

        //region Get cached data
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.READING_CACHE));
        var audioFiles = new ArrayList<AudioFile>();
        try {
            audioFiles = timeTrackProvider.track(() -> {
                try {
                    var x = libraryProvider.getLibraryCache(cachePath);
                    return x;
                } catch (Exception ignored) {
                    return new ArrayList<>();
                }
            }, "Getting cache");
        } catch (Exception e) {
            e.printStackTrace();
        }

        libraryProvider.setLibrary(audioFiles);
        //endregion

        //region Read full actual library
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.UPDATING_FILES));
        AppConfig finalConfig1 = config;
        audioFiles = new ArrayList<>(timeTrackProvider.track(() -> {
            return libraryProvider.getLibraryFull(finalConfig1.rootPath, "mp3");
        }, "Getting playlist"));

        libraryProvider.setLibrary(audioFiles);
        //endregion

        //region Get metadata
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.UPDATING_METADATA));
        timeTrackProvider.trackProgress(audioFiles, (file) -> {
            try {
                libraryProvider.getMetadata(file);
            } catch (IOException | TikaException | SAXException e) {
                e.printStackTrace();
            }
            return new AudioFile();
        }, "Getting metadata");

        libraryProvider.setLibrary(audioFiles, true);
        //endregion

        //region Merging
        // todo: save current playing file
        // todo: merge
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.MERGING));
        //endregion

        //region Save/update cache
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.SAVING_CACHE));
        ArrayList<AudioFile> finalAudioFiles = audioFiles;
        timeTrackProvider.track(() -> {
            libraryProvider.saveCache(finalAudioFiles, cachePath);
            return null;
        }, "Saving Cache");
        //endregion

        //region Watch playlist for changes
        // todo: watch playlist
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.WATCHING));
        //endregion
    }
}
