import domain.AudioFile;
import org.apache.tika.exception.TikaException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.xml.sax.SAXException;
import providers.library.api.ILibraryProvider;
import providers.playList.api.IPlayListProvider;
import providers.player.api.IPlayerProvider;
import providers.status.api.IStatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan({"controllers", "config"})
public class Main {
    //region Defaults

    private static String rootPath = "C:/Users/vlado/Music";
    private static String cachePath = "app_data/metadata";

    //endregion

    public static void main(String[] args) throws Exception {

        //region Basic initialization

        // Launch spring services
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);

        // Get beans reference
        IStatusProvider statusProvider = applicationContext.getBean(IStatusProvider.class);
        IPlayerProvider playerProvider = applicationContext.getBean(IPlayerProvider.class);
        ILibraryProvider libraryProvider = applicationContext.getBean(ILibraryProvider.class);
        ITimeTrackProvider timeTrackProvider = applicationContext.getBean(ITimeTrackProvider.class);
        IPlayListProvider playListProvider = applicationContext.getBean(IPlayListProvider.class);

        statusProvider.setStatus("Application launched");

        //endregion

        //region Get cached data

        ArrayList<AudioFile> audioFiles = new ArrayList<AudioFile>();
        boolean filesRetrieved = false;

        try {
            audioFiles = timeTrackProvider.track(() -> {
                return libraryProvider.getLibraryCache(cachePath);
            }, "Getting cache");
            filesRetrieved = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //endregion

        //region Set playlist

        playListProvider.setList(audioFiles);

        //endregion

        //region Read full actual library

        ArrayList<File> files = new ArrayList<>(Arrays.asList(timeTrackProvider.track(() -> {
            return libraryProvider.getLibraryFull(rootPath, "mp3");
        }, "Getting files")));

        audioFiles = timeTrackProvider.trackProgress(files, (File file) -> {
            try {
                return libraryProvider.GetMetadata(file);
            } catch (IOException | TikaException | SAXException e) {
                e.printStackTrace();
            }
            return new AudioFile();
        }, "Getting metadata");

        filesRetrieved = true;

        //endregion

        //region Update playlist

        playListProvider.setList(audioFiles);

        //endregion

        //region Save/update cache

        ArrayList<AudioFile> finalAudioFiles = audioFiles;
        timeTrackProvider.track(() -> {
            libraryProvider.SaveCache(finalAudioFiles, "app_data/metadata");
            return null;
        }, "Saving Cache");

        //endregion

    }
}
