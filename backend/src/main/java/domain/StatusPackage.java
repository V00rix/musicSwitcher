package domain;

/**
 * Small status for synchronization
 * FE receives 3 statuses and compares to latest values
 * If value is different, update request is sent to corresponding status
 */
public class StatusPackage {
    public static final int GLOBAL = 1;
    public static final int PLAYER = 2;
    public static final int LIBRARY = 4;


    /**
     * Global status
     */
    private final long statusGlobal;

    /**
     * Player status
     */
    private final long statusPlayer;

    /**
     * Library status
     */
    private final long statusLibrary;

    /**
     * New status package
     *
     * @param global  Global status id
     * @param player  Player status id
     * @param library Library player id
     */
    public StatusPackage(long global, long player, long library) {
        this.statusGlobal = global;
        this.statusPlayer = player;
        this.statusLibrary = library;
    }

    /**
     * Update package
     *
     * @param type Flags of what to update
     * @return New package
     */
    public StatusPackage update(int type) {
        long newGlobal = 0, newPlayer = 0, newLibrary = 0;
        if (type > 0) {
            newGlobal = this.statusGlobal + 1;
        }
        if (type > 1) {
            newPlayer = this.statusPlayer + 1;
        }
        if (type > 2) {
            newLibrary = this.statusLibrary + 1;
        }
        return new StatusPackage(this.statusGlobal + 1, this.statusPlayer + 1, this.statusLibrary + 1);
    }
}