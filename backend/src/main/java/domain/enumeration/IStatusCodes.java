package domain.enumeration;

/**
 * Status codes
 */
public interface IStatusCodes {
    //region Global
    public static final class Global {
        public static final int INITIALIZING = 1100;
        public static final int OK = 1101;
        public static final int CRASHED = 1102;
    }
    //endregion

    //region Library
    public static final class Library {
        public static final int READING_CACHE = 1200;
        public static final int UPDATING_FILES = 1201;
        public static final int UPDATING_METADATA = 1202;
        public static final int MERGING = 1203;
        public static final int WATCHING = 1204;
        public static final int SAVING_CACHE = 1205;
    }
    //endregion

    //region Player
    public static final class Player {
        public static final int NO_FILES = 1300;
        public static final int PLAYLIST_SET = 1301;
        public static final int FILE_CHANGED = 1302;
        public static final int PLAYING = 1303;
        public static final int PAUSED = 1304;
        public static final int STOPPED = 1305;
    }
    //endregion
}
