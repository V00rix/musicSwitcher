package domain.exeptions;

import domain.enumeration.ErrorCodes;
import domain.enumeration.Severities;

/**
 * Unprovided exception: missing provider for component
 */
public class UnprovidedException extends BaseException {
    private static final ErrorCodes code = ErrorCodes.UNPROVIDED;

    /**
     * Unprovided exception: missing provider for component
     *
     * @param resource    Missing provider name
     * @param destination Consumer component name
     */
    public UnprovidedException(String resource, String destination) {
        super("No provider for " + destination + "! ("
                + destination + " <- "
                + resource
                + ")", Severities.FATAl, code);
    }
}
