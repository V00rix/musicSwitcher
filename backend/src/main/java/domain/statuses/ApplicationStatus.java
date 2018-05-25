package domain.statuses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.enumeration.IStatusCodes.Global;
import domain.enumeration.IStatusCodes.Library;

/**
 *  Library status
 */
public class ApplicationStatus extends StatusBase {
    /**
     *  Library not yet initialized
     */
    @JsonIgnore
    public static final Status INITIALIZING = new Status("Application initializing", Global.INITIALIZING);

    /**
     * Reading cache
     */
    @JsonIgnore
    public static final Status READING_CACHE = new Status("Reading cache", Library.READING_CACHE);

    /**
     * Scanning directories, updating playlist
     */
    @JsonIgnore
    public static final Status UPDATING_FILES = new Status("Updating library", Library.UPDATING_FILES);

    /**
     * Updating metadata
     */
    @JsonIgnore
    public static final Status UPDATING_METADATA = new Status("Updating metadata", Library.UPDATING_METADATA);

    /**
     * Merging
     */
    @JsonIgnore
    public static final Status MERGING = new Status("Merging", Library.MERGING);

    /**
     * Saving cache
     */
    @JsonIgnore
    public static final Status SAVING_CACHE = new Status("Saving cache", Library.SAVING_CACHE);

    /**
     * Watching directories for changes
     */
    @JsonIgnore
    public static final Status WATCHING = new Status("Watching playlist for changes", Library.WATCHING);

    public ApplicationStatus(Status status) {
        super(status);
    }
}