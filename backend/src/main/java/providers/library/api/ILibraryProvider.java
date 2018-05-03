package providers.library.api;

import domain.AudioFile;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Library provider API
 */
public interface ILibraryProvider {
    /**
     * Get cached library
     *
     * @return List of files
     */
    ArrayList<AudioFile> getLibraryCache(String filepath) throws Exception;

    /**
     * Scan filesystem and full library
     *
     * @return List of files
     */
    ArrayList<AudioFile> getLibraryFull(String rootPath, final String... fileTypes);

    /**
     * Get metadata for file
     *
     * @param file File
     * @return File with metadata
     */
    AudioFile getMetadata(AudioFile file) throws IOException, TikaException, SAXException;

    /**
     * Get metadata for file
     *
     * @param file File
     * @return File with metadata
     */
    default AudioFile getMetadata(File file) throws IOException, TikaException, SAXException {
        return this.getMetadata(new AudioFile(file));
    };

    /**
     * Save Cache
     */
    void saveCache(ArrayList<AudioFile> audioFiles, String filePath) throws IOException;

    /**
     * Get audio files
     */
    default ArrayList<AudioFile> getFiles() {
        return this.getFiles(SortBy.TITLE);
    }

    /**
     * Get audio files
     * @param sortBy Sorting
     * @return Audio files
     */
    ArrayList<AudioFile> getFiles(SortBy sortBy);

    /**
     * DELETE LATER, library should be set in update loop
     * @param files New Files
     */
    void setLibrary(ArrayList<AudioFile> files);

    /**
     * Get limited amount of audio files
     * @param count Files count
     * @param sortBy Sorting
     * @return Audio files
     */
    default ArrayList<AudioFile> getFiles(int count, SortBy sortBy) {
        return new ArrayList<AudioFile>(this.getFiles().subList(0, count));
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
