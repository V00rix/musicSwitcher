package domain.enumeration;

/**
 * Error severities
 */
public enum Severities {
    /**
     * Fatal error - application should quit with error
     */
    FATAl,

    /**
     * Severe error - application is expected to stop running properly
     */
    ERROR,

    /**
     * Non-severe error - operation will fail, but application is expected to run properly
     */
    WARNING,

    /**
     * Information message
     */
    INFO,

    /**
     * Debug message
     */
    DEBUG
}
