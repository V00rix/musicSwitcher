package providers.library.api;

import domain.AudioFile;
import javafx.util.Pair;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Library provider API
 */
public interface ILibraryProvider {
    /**
     * Get cached library
     *
     * @return List of playlist
     */
    ArrayList<AudioFile> getLibraryCache(String filepath) throws Exception;

    /**
     * Scan filesystem and full library
     *
     * @return List of playlist
     */
    ArrayList<AudioFile> getLibraryFull(String rootPath, final String... fileTypes);

    /**
     * Get metadata for file
     *
     * @param file File
     */
    void getMetadata(AudioFile file) throws IOException, TikaException, SAXException;

    /**
     * Get metadata for file
     *
     * @param file File
     */
    default void getMetadata(File file) throws IOException, TikaException, SAXException {
        this.getMetadata(new AudioFile(file));
    }

    /**
     * Save Cache
     */
    void saveCache(final ArrayList<AudioFile> audioFiles, String filePath) throws IOException;

    /**
     * Get file by file id
     *
     * @param id AudioFile unique id
     */
    AudioFile file(int id);

    /**
     * Get audio playlist
     */
    default Pair<ArrayList<AudioFile>, Boolean> getFiles() {
        return this.getFiles(SortBy.TITLE);
    }

    /**
     * Get audio playlist
     *
     * @param sortBy Sorting
     * @return Audio playlist
     */
    Pair<ArrayList<AudioFile>, Boolean> getFiles(SortBy sortBy);

    /**
     * Set library playlist
     *
     * @param files New Files
     */
    void setLibrary(ArrayList<AudioFile> files);

    /**
     * Set updating completion flag
     *
     * @param isComplete Flag if the library is expected to get updated
     */
    void setLibraryCompletion(boolean isComplete);

    /**
     * Get limited amount of audio playlist
     *
     * @param count  Files count
     * @param sortBy Sorting
     * @return Audio playlist
     */
    default Pair<ArrayList<AudioFile>, Boolean> getFiles(int count, SortBy sortBy) {
        var files = this.getFiles();
        return new Pair<>(new ArrayList<AudioFile>(files.getKey().subList(0, count)), files.getValue());
    }

    enum SortBy {
        RAW,
        TITLE,
        ARTIST,
        ALBUM,
        GENRE,
        DATE_ADDED,
        DATE_CHANGED,
        DURATION
    }
}
