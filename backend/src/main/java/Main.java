import components.gui.controlWindow.api.IControlWindow;
import components.gui.controlWindow.implementation.ControlWindow;
import components.util.RichConsole;
import domain.AppConfig;
import domain.AudioFile;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import providers.control.api.IControlProvider;
import providers.library.api.ILibraryProvider;
import providers.playList.api.IPlayListProvider;
import providers.status.api.IStatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static components.util.RichConsole.out;
import static components.util.RichConsole.style;

@SpringBootApplication
@ComponentScan({"controllers", "config"})
public class Main {
    //region Default file paths
    private static final String configPath = "backend/app_data/config";
    private static final String cachePath = "backend/app_data/metadata";
    //endregion

    //region Fields
    //region Providers
    private static IStatusProvider statusProvider;
    private static IControlProvider playerProvider;
    private static ILibraryProvider libraryProvider;
    private static ITimeTrackProvider timeTrackProvider;
    private static IPlayListProvider playListProvider;
    //endregion

    private static IControlWindow controlWindow;
    private static String rootPath;
    private static AppConfig config;
    private static Thread refreshThread;

    private static ArrayList<AudioFile> audioFiles = new ArrayList<>();

    private static Runnable refreshLibraryLogic = () -> {
        try {
            libraryProvider.setLibraryCompletion(false);

            updateFiles(audioFiles, getLibraryFull(config));

            System.out.println("y boi");
            audioFiles.forEach(value -> {
                System.out.println(value.id);
            });

            updateMetadata(audioFiles);

            saveCache();

            libraryProvider.setLibraryCompletion(true);
        } catch (Exception e) {
            out("An error has occurred while refreshing the library!", RichConsole.Colors.RED);
            e.printStackTrace();
        }
    };
    //endregion

    /**
     * Application entry point
     *
     * @param args Args
     * @throws Exception Exception
     */
    public static void main(String[] args) throws Exception {
        controlWindow = launchControlWindow();

        getProviders(SpringApplication.run(Main.class, args));

        getRootPath();

        libraryProvider.setLibrary(audioFiles = getLibraryCache());

        refreshLibrary();

        // TODO: 30-May-18 Watch library for changes
    }

    //region Helpers logic

    /**
     * Create control window GUI
     *
     * @return Control window instance
     * @throws InterruptedException InterruptedException
     */
    private static IControlWindow launchControlWindow() throws InterruptedException {
        new Thread(() -> Application.launch(ControlWindow.class)).start();
        ControlWindow.latch.await();

        var controlWindow = ControlWindow.instance;


        //region Configure events
        controlWindow.setOnDirectoryChanged(newDir -> {
            config.rootPath = newDir;
            saveConfig(config, configPath);
            refreshLibrary();
        });

        controlWindow.setOnExit(() -> {
            try {
                saveCache();
            } catch (Exception e) {
                out("Could not save cache! " + e.getMessage(), RichConsole.Colors.RED);
                e.printStackTrace();
            }
            System.exit(0);
        });


        //endregion
        return controlWindow;
    }

    /**
     * Refresh library logic thread
     */
    private static void refreshLibrary() {
        if (refreshThread != null) {
            refreshThread.interrupt();
        }
        refreshThread = new Thread(refreshLibraryLogic);
        refreshThread.start();
    }

    /**
     * Get providers' references
     *
     * @param springContext Spring's application context
     */
    private static void getProviders(ApplicationContext springContext) {
        statusProvider = springContext.getBean(IStatusProvider.class);
        playerProvider = springContext.getBean(IControlProvider.class);
        libraryProvider = springContext.getBean(ILibraryProvider.class);
        timeTrackProvider = springContext.getBean(ITimeTrackProvider.class);
        playListProvider = springContext.getBean(IPlayListProvider.class);
    }

    /**
     * Set library root path
     *
     * @throws Exception Exception
     */
    private static void getRootPath() throws Exception {
        config = new AppConfig();
        if (new File(configPath).exists()) {
            FileInputStream fis = new FileInputStream(configPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            config = (AppConfig) ois.readObject();
        } else {
            controlWindow.changeDirectory();
        }
    }

    /**
     * Gets library from cache
     *
     * @return Array of audio files
     */
    private static ArrayList<AudioFile> getLibraryCache() {
        try {
            return timeTrackProvider.track(() -> {
                try {
                    return libraryProvider.getLibraryCache(cachePath);
                } catch (Exception e) {
                    out("Could not get cached library!", RichConsole.Colors.RED);
                    e.printStackTrace();
                    return new ArrayList<>();
                }
            }, style("Getting cache", RichConsole.BoldColors.YELLOW));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Scans file structure under {@link Main#rootPath}
     *
     * @param config AppConfig
     * @return Array of actual files without metadata
     * @throws Exception Exception
     */
    private static ArrayList<AudioFile> getLibraryFull(AppConfig config) throws Exception {
        return new ArrayList<>(timeTrackProvider.track(() -> {
            var x = libraryProvider.getLibraryFull(config.rootPath, "mp3");
            out("Got " + x.size() + " audio files.", RichConsole.Colors.GREEN);
            return x;
        }, "Getting playlist"));
    }

    /**
     * Updates library
     *
     * @param library    Old (current) library
     * @param libraryNew New (actual) library
     */
    private static void updateFiles(ArrayList<AudioFile> library, ArrayList<AudioFile> libraryNew) {
        var toRemove = new ArrayList<AudioFile>();

        library.forEach(f -> {
            if (libraryNew.contains(f)) {
                libraryNew.remove(f);
            } else {
                toRemove.add(f);
            }
        });

        System.out.println("to be removed: " + toRemove.size());

        toRemove.forEach(library::remove);

        System.out.println("to be added: " + libraryNew.size());

        library.addAll(libraryNew);
    }

    /**
     * Update library metadata
     *
     * @param audioFiles Library
     * @throws Exception Exception
     */
    private static void updateMetadata(ArrayList<AudioFile> audioFiles) throws Exception {
        var newFiles = audioFiles.stream().filter(f -> !f.metadataRetrieved).collect(Collectors.toCollection(ArrayList::new));
        var oldFiles = audioFiles.stream().filter(f -> f.metadataRetrieved).collect(Collectors.toCollection(ArrayList::new));

        timeTrackProvider.trackProgress(newFiles, (file) -> {
            try {
                libraryProvider.getMetadata(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new AudioFile();
        }, "Getting metadata");

        timeTrackProvider.trackProgress(oldFiles, (file) -> {
            try {
                libraryProvider.getMetadata(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new AudioFile();
        }, "Updating metadata");
    }

    /**
     * Save library to a cached  file
     *
     * @throws Exception Exception
     */
    private static void saveCache() throws Exception {
        timeTrackProvider.track(() -> {
            libraryProvider.saveCache(audioFiles, cachePath);
            return null;
        }, "Saving Cache");
    }

    /**
     * Save configuration
     *
     * @param config     Configuration
     * @param configPath Save path
     */
    private static void saveConfig(AppConfig config, String configPath) {
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
            oos.writeObject(config);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            RichConsole.out("Could not write to a configuration file!", RichConsole.Colors.RED);
            e.printStackTrace();
        }
    }
    //endregion
}
