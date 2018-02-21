package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.File;
import java.io.Serializable;

public class AudioFile implements Serializable {
    public String title;
    public String artist;
    public String album;
    public int track;

    public String filePath;

    @JsonIgnore
    public File file;
    public String genre;
}
