package providers.library.implementation;

import domain.AudioFile;
import domain.exeptions.UnprovidedException;
import javafx.util.Pair;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import providers.IProviderBase;
import providers.library.api.ILibraryProvider;
import providers.status.api.IStatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Library provider implementation
 */
public class LibraryProvider implements ILibraryProvider, IProviderBase {
    //region Fields
    //region Providers
    private final IStatusProvider statusProvider;
    private final ITimeTrackProvider timeTrackProvider;
    //endregion

    private final Parser parser;
    private final ContentHandler contentHandler;
    private final Metadata metadata;
    private final ParseContext parseContext;

    private ArrayList<AudioFile> files;
    private boolean isComplete = false;
    //endregion

    //region Constructor
    /**
     * New instance of library provider
     *
     * @param statusProvider Status provider
     * @throws UnprovidedException UnprovidedException
     */
    public LibraryProvider(IStatusProvider statusProvider, ITimeTrackProvider timeTrackProvider) throws UnprovidedException {
        this.checkProviders(statusProvider);
        this.statusProvider = statusProvider;
        this.timeTrackProvider = timeTrackProvider;
        parser = new Mp3Parser();
        files = new ArrayList<AudioFile>();
        parseContext = new ParseContext();
        metadata = new Metadata();
        contentHandler = new DefaultHandler();
    }
    //endregion

    //region Implementation
    @Override
    public ArrayList<AudioFile> getLibraryCache(String filepath) throws Exception {
        return this.timeTrackProvider.track(() -> {
            Pattern pattern = Pattern.compile("^(.+)/");
            Matcher matcher = pattern.matcher(filepath);
            if (matcher.find()) {
                File directory = new File(matcher.group(1));
                directory.mkdir();
            }

            File f = new File(filepath);
            if (!f.exists()) {
                throw new FileNotFoundException();
            }

            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.files = (ArrayList<AudioFile>) ois.readObject();
            ois.close();
            return this.files;
        }, "Reading cache");
    }

    @Override
    public ArrayList<AudioFile> getLibraryFull(String root, final String... fileTypes) {
        // TODO: 31-May-18 not sure if this is the right place to check for interruption
        if (Thread.currentThread().isInterrupted())
            return new ArrayList<>();

        ArrayList<AudioFile> files = new ArrayList<>();

        File[] fls = this.ListFiles(root, fileTypes);

        for (File file : fls) {
            files.add(new AudioFile(file));
        }
        File[] dirs = this.ListSubdirectories(root);

        ArrayList<AudioFile> allFiles = new ArrayList<>(files);

        for (File dir : dirs) {
            ArrayList<AudioFile> dirFiles = this.getLibraryFull(dir.getAbsolutePath(), fileTypes);
            allFiles.addAll(dirFiles);
        }

        // todo: overwrite only playlist with different change

        return allFiles;
    }

    @Override
    public void getMetadata(AudioFile file) throws IOException, TikaException, SAXException {
        System.out.println("Getting metadata for " + file.filePath);

        InputStream stream = new FileInputStream(file.file);
        parser.parse(stream, contentHandler, metadata, parseContext);
        stream.close();

        file.title = metadata.get("title");
        file.album = metadata.get("xmpDM:album");
        file.artist = metadata.get("xmpDM:artist");
        file.track = Integer.parseInt(metadata.get("xmpDM:trackNumber").split("/")[0]);
        file.genre = metadata.get("xmpDM:genre");
        file.metadataRetrieved = true;
    }

    @Override
    public void saveCache(ArrayList<AudioFile> audioFiles, String filePath) throws IOException {
        File f = new File(filePath);
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();

        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(audioFiles);
        oos.flush();
        oos.close();
    }

    @Override
    public AudioFile file(int id) {
        return this.files.stream().filter(a -> a.id == id).findFirst().get();
    }

    @Override
    public Pair<ArrayList<AudioFile>, Boolean> getFiles(SortBy sortBy) {
        // todo: sorting
        /*
            Either create new list while sorting, or have a mutation error while iterating over list in a different thread
         */
        //        switch (sortBy) {
        //            case ALBUM:
        //                this.playlist.sort((a1, a2) -> {
        //                    return (a1.album == null) ? ((a2.album == null) ? 1 : -1) : ((a2.album == null) ? 1 : a1.album.compareTo(a2.album));
        //                });
        //                break;
        //            case TITLE:
        //            default:
        //                this.playlist.sort((a1, a2) -> {
        //                    return (a1.title == null) ? ((a2.title == null) ? 1 : -1) : ((a2.title == null) ? 1 : a1.title.compareTo(a2.title));
        //                });
        //        }
        return new Pair<>(this.files, this.isComplete);
    }

    @Override
    public void setLibraryCompletion(boolean isComplete) {
        this.isComplete = isComplete;
    }

    @Override
    public void setLibrary(ArrayList<AudioFile> files) {
        this.files = files;
    }
    //endregion

    //region Helpers
    /**
     * List only playlist in directory
     *
     * @param root      Directory address
     * @param fileTypes File types
     * @return List of playlist
     */
    private File[] ListFiles(String root, final String... fileTypes) {
        return new File(root).listFiles(new FileFilter() {
            private final FileNameExtensionFilter filter =
                    new FileNameExtensionFilter("Audio Files",
                            fileTypes);

            public boolean accept(File file) {
                return filter.accept(file) && !file.isDirectory();
            }
        });
    }

    /**
     * List only directories in directory
     *
     * @param root Directory address
     * @return List of directories
     */
    private File[] ListSubdirectories(String root) {
        return new File(root).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
    }
    //endregion
}
