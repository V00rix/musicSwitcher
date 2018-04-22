package providers.library.implementation;

import domain.AudioFile;
import domain.exeptions.UnprovidedException;
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
import java.util.Arrays;

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
        this.statusProvider.setStatus("LibraryProvider initialized");
    }
    //endregion

    //region Implementation

    @Override
    public ArrayList<AudioFile> getLibraryCache(String filepath) throws Exception {
        return this.timeTrackProvider.track(() -> {

            System.out.println(filepath);
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
    public File[] getLibraryFull(String root, final String... fileTypes) {
        File[] files = this.ListFiles(root, fileTypes);
        File[] dirs = this.ListSubdirectories(root);

        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(files));

        for (File dir : dirs) {
            File[] fls = this.getLibraryFull(dir.getAbsolutePath(), fileTypes);
            allFiles.addAll(Arrays.asList(fls));
        }

        return allFiles.toArray(new File[0]);
    }

    @Override
    public AudioFile GetMetadata(File file) throws IOException, TikaException, SAXException {
        AudioFile f = new AudioFile(file);

        this.statusProvider.setStatus("Getting metadata for " + f.filePath);

        InputStream stream = new FileInputStream(f.file);
        parser.parse(stream, contentHandler, metadata, parseContext);
        stream.close();

        f.title = metadata.get("title");
        f.album = metadata.get("xmpDM:album");
        f.artist = metadata.get("xmpDM:artist");
        f.track = Integer.parseInt(metadata.get("xmpDM:trackNumber").split("/")[0]);
        f.genre = metadata.get("xmpDM:genre");

        return f;
    }

    @Override
    public void SaveCache(ArrayList<AudioFile> audioFiles, String filePath) throws IOException {
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
    public ArrayList<AudioFile> getFiles() {
        return this.files;
    };

    //endregion

    //region Helpers

    /**
     * List only files in directory
     *
     * @param root      Directory address
     * @param fileTypes File types
     * @return List of files
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
