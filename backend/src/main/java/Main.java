import domain.AudioFile;
import domain.enumeration.IStatusCodes;
import domain.statuses.ApplicationStatus;
import domain.statuses.StatusBase;
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

import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
@ComponentScan({"controllers", "config"})
public class Main {

    //region Defaults

    //    private static final String rootPath = "/Users/elumixor/Music/iTunes/iTunes Media/Music"; // mac
    private static final String rootPath = "C:/Users/vlado/Music"; // windows

    private static final String cachePath = "backend/app_data/metadata";

    //endregion

    public static void main(String[] args) throws Exception {

        //region Basic initialization
        //region Launch spring services
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        //endregion

        // Get beans reference
        IStatusProvider statusProvider = applicationContext.getBean(IStatusProvider.class);
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.INITIALIZING));

        IPlayerProvider playerProvider = applicationContext.getBean(IPlayerProvider.class);
        ILibraryProvider libraryProvider = applicationContext.getBean(ILibraryProvider.class);
        ITimeTrackProvider timeTrackProvider = applicationContext.getBean(ITimeTrackProvider.class);
        IPlayListProvider playListProvider = applicationContext.getBean(IPlayListProvider.class);
        //endregion

        //region Get cached data
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.READING_CACHE));
        var audioFiles = new ArrayList<AudioFile>();
        try {
            audioFiles = timeTrackProvider.track(() -> {
                return libraryProvider.getLibraryCache(cachePath);
            }, "Getting cache");
        } catch (Exception e) {
            e.printStackTrace();
        }

        libraryProvider.setLibrary(audioFiles);
        //endregion

        //region Read full actual library
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.UPDATING_FILES));
        audioFiles = new ArrayList<>(timeTrackProvider.track(() -> {
            return libraryProvider.getLibraryFull(rootPath, "mp3");
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
//        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.SAVING_CACHE));
//        ArrayList<AudioFile> finalAudioFiles = audioFiles;
//        timeTrackProvider.track(() -> {
//            libraryProvider.saveCache(finalAudioFiles, cachePath);
//            return null;
//        }, "Saving Cache");
        //endregion

        //region Watch playlist for changes
        // todo: watch playlist
        statusProvider.setStatus(new ApplicationStatus(ApplicationStatus.WATCHING));
        //endregion
    }
}
