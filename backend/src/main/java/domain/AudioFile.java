package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.io.Serializable;

/**
 * Audio file with metadata
 */
public class AudioFile implements Serializable {
    //region Fields

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
    public String filePath;

    /**
     * Genre
     */
    public String genre;

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
    public AudioFile() {}


    /**
     * Convert from File
     * @param f File
     */
    public AudioFile(File f) {
        this.file = f;
        this.filePath = f.getName();
    }

    //endregion
}
