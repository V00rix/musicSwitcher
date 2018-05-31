package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import components.DateHelper;

import java.io.File;
import java.io.Serializable;

/**
 * Audio file with metadata
 */
public class AudioFile implements Serializable, Comparable<AudioFile> {
    //region Defaults
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
    public boolean metadataRetrieved = false;

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
     * Actual file path
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
        this();
        this.file = f;
        this.filePath = f.getAbsolutePath();
        this.path = f.getName();
        this.dateChanged = DateHelper.dateFormat.format(f.lastModified());
    }
    //endregion

    //region Override
    @Override
    public String toString() {
        return this.title + " - " + this.album + " - " + this.artist + " (" + this.filePath + ") " + this.dateChanged;
    }
    //endregion


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != AudioFile.class) {
            return false;
        } else {
            var another = (AudioFile) obj;
            return this.compareTo(another) == 0;
        }
    }

    @Override
    public int compareTo(AudioFile o) {
        var filePathDifference = this.filePath.compareTo(o.filePath);
        return filePathDifference == 0 ? DateHelper.compare(this.dateChanged, o.dateChanged) : filePathDifference;
    }
}
