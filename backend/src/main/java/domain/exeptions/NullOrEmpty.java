package domain.exeptions;

import domain.enumeration.ErrorCodes;
import domain.enumeration.Severities;

import java.util.ArrayList;

/**
 * Interface to check for null or empty arrays
 * @param <T>
 */
public interface NullOrEmpty<T> {
    /**
     * Check if array is null or empty
     * @param list List
     * @param <T> List type
     * @throws NullOrEmptyException NullOrEmptyException
     */
    static <T> void check(ArrayList<T> list) throws NullOrEmptyException {
        if (list == null || list.size() < 1) {
            throw new NullOrEmptyException("Received an uninitialized or empty list");
        }
    }

    /**
     * Exception for a null or an empty array
     */
    class NullOrEmptyException extends BaseException {
        /**
         * Exception for a null or an empty array
         * @param s
         */
        NullOrEmptyException(String s) {
            super(s, Severities.ERROR, ErrorCodes.NULL_OR_EMPTY);
        }
    }
}
