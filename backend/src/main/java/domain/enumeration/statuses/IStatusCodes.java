package domain.enumeration.statuses;

/**
 * Status codes
 */
public interface IStatusCodes {
    //region Global
    public static final class Global {
        static final int INITIALIZING = 1100;
        static final int OK = 1101;
        static final int CRASHED = 1102;
    }
    //endregion

    //region Library
    public static final class Library {
        static final int INITIALIZING = 1200;
        static final int READING_CACHE = 1201;
        static final int UPDATING_FILES = 1202;
        static final int UPDATING_METADATA = 1203;
        static final int MERGING = 1204;
        static final int WATCHING = 1205;
        static final int SAVING_CACHE = 1206;
    }
    //endregion

    //region Player
    public static final class Player {
        static final int PLAYING = 1300;
        static final int PAUSED = 1301;
        static final int STOPPED = 1302;
    }
    //endregion
}
