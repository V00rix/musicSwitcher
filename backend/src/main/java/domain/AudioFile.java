package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Audio file with metadata
 */
public class AudioFile implements Serializable {
    //region Defaults
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static int fileId = 0;
    //endregion

    //region Fields
    /**
     * File id
     */
    public int id;

    /**
     * Flag if metadata is retrieved for this file
     */
    public boolean metadataRetrieved  = false;

    /**
     * Title
     */
    public String title;

    /**
     * Name
     */
    public String artist;

    /**
     * Album
     */
    public String album;

    /**
     * Track  number
     */
    public int track;

    /**
     * File path
     */
    @JsonIgnore
    public String filePath;

    /**
     * Formatted file path to display when no metadata is found
     */
    public String path;

    /**
     * Genre
     */
    public String genre;
    /**
     * Genre
     */
    public String dateChanged;
    //endregion

    //region JsonIgnore
    /**
     * Actual file
     */
    @JsonIgnore
    public File file;
    //endregion

    //region Constructors
    /**
     * New audio file instance
     */
    public AudioFile() {
        this.id = fileId++;
    }

    /**
     * Convert from File
     *
     * @param f File
     */
    public AudioFile(File f) {
        this.id = fileId++;
        this.file = f;
        this.filePath = f.getAbsolutePath();
        this.path = f.getName();
        this.dateChanged = dateFormat.format(file.lastModified());
    }
    //endregion

    //region Override
    @Override
    public String toString() {
        return this.title + " - " + this.album + " - " + this.artist + " (" + this.filePath + ")";
    }
    //endregion
}
