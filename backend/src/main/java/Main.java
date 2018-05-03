import domain.AudioFile;
import domain.statuses.StatusBase;
import domain.statuses.StatusGlobal;
import domain.statuses.StatusLibrary;
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

    private static final String rootPath = "C:/Users/vlado/Music";
    private static final String cachePath = "backend/app_data/metadata";

    //endregion

    public static void main(String[] args) throws Exception {

        //region Basic initialization
        //region Launch spring services
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        //endregion

        // Get beans reference
        IStatusProvider statusProvider = applicationContext.getBean(IStatusProvider.class);
        statusProvider.setStatus(new StatusGlobal(StatusGlobal.INITIALIZING));

        IPlayerProvider playerProvider = applicationContext.getBean(IPlayerProvider.class);
        ILibraryProvider libraryProvider = applicationContext.getBean(ILibraryProvider.class);
        ITimeTrackProvider timeTrackProvider = applicationContext.getBean(ITimeTrackProvider.class);
        IPlayListProvider playListProvider = applicationContext.getBean(IPlayListProvider.class);
        statusProvider.setStatus(new StatusGlobal(StatusGlobal.OK));
        //endregion

        //region Get cached data
        statusProvider.setStatus(new StatusLibrary(StatusLibrary.READING_CACHE));
        ArrayList<AudioFile> audioFiles = new ArrayList<>();
        try {
            audioFiles = timeTrackProvider.track(() -> {
                return libraryProvider.getLibraryCache(cachePath);
            }, "Getting cache");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //endregion

        //region Set playlist
        playListProvider.setFiles(audioFiles);
        //endregion

        //region Read full actual library
        statusProvider.setStatus(new StatusLibrary(StatusLibrary.UPDATING_FILES));
        ArrayList<AudioFile> files = new ArrayList<>(timeTrackProvider.track(() -> {
            return libraryProvider.getLibraryFull(rootPath, "mp3");
        }, "Getting files"));
        //endregion

        //region Set playlist
        playListProvider.setFiles(files);
        //endregion

        //region Get metadata
        statusProvider.setStatus(new StatusLibrary(StatusLibrary.UPDATING_METADATA));
        audioFiles = timeTrackProvider.trackProgress(files, (file) -> {
            try {
                return libraryProvider.getMetadata(file);
            } catch (IOException | TikaException | SAXException e) {
                e.printStackTrace();
            }
            return new AudioFile();
        }, "Getting metadata");

        libraryProvider.setLibrary(audioFiles);
        //endregion

        //region Merging
        // todo: save current playing file
        // todo: merge
        statusProvider.setStatus(new StatusLibrary(StatusLibrary.MERGING));
        //endregion

        //region Set playlist
        playListProvider.setFiles(audioFiles);
        //endregion

        //region Save/update cache
        statusProvider.setStatus(new StatusLibrary(StatusLibrary.SAVING_CACHE));
        ArrayList<AudioFile> finalAudioFiles = audioFiles;
        timeTrackProvider.track(() -> {
            libraryProvider.saveCache(finalAudioFiles, cachePath);
            return null;
        }, "Saving Cache");
        //endregion

        //region Watch files for changes
        // todo: watch files
        statusProvider.setStatus(new StatusLibrary(StatusLibrary.WATCHING));
        //endregion
    }
}
