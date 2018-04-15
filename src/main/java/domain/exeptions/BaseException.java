package domain.exeptions;

import domain.enumeration.ErrorCodes;
import domain.enumeration.Severities;

/**
 * Base application exception
 */
public class BaseException extends Exception {
    // private fields
    private static final String messageDefault = "Error has occurred!";
    private static final ErrorCodes codeDefault = ErrorCodes.BASE;
    private static final Severities severityDefault = Severities.ERROR;

    /**
     * Error message
     */
    public final String message;

    /**
     * Error code
     */
    public final ErrorCodes code;

    /**
     * Error severity
     */
    public final Severities severity;

    /**
     * Base application exception
     */
    public BaseException() {
        this(messageDefault, severityDefault, codeDefault);
    }

    /**
     * Base application exception
     *
     * @param message Error message
     */
    public BaseException(String message) {
        this(message, severityDefault, codeDefault);
    }

    /**
     * Base application exception
     *
     * @param message  Error message
     * @param severity Severities
     */
    public BaseException(String message, Severities severity) {
        this(message, severity, codeDefault);
    }

    /**
     * Base application exception
     *
     * @param message  Error message
     * @param severity Severities
     * @param code     Error code
     */
    public BaseException(String message, Severities severity, ErrorCodes code) {
        super(message + " " + code.toString());
        this.message = message + " " + code.toString();
        this.severity = severity;
        this.code = code;
        if (severity == Severities.FATAl) {
            this.printStackTrace();
            System.exit(code.getNumVal());
        }
    }
}
