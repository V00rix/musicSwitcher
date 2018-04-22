package providers.library.api;

import domain.AudioFile;
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
     * @return List of files
     */
    ArrayList<AudioFile> getLibraryCache(String filepath) throws Exception;

    /**
     * Scan filesystem and full library
     * @return List of files
     */
    File[] getLibraryFull(String rootPath, final String... fileTypes);

    /**
     * Get metadata for file
     * @param file File
     * @return File with metadata
     */
    AudioFile GetMetadata(File file) throws IOException, TikaException, SAXException;

    /**
     * Save Cache
     */
    void SaveCache(ArrayList<AudioFile> audioFiles, String filePath) throws IOException;

    /**
     * Get audio files
     */
    ArrayList<AudioFile> getFiles();
}
