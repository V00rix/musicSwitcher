package domain.exeptions.checks;

import domain.enumeration.ErrorCodes;
import domain.enumeration.Severities;
import domain.exeptions.BaseException;

import java.util.ArrayList;

/**
 * Interface to check for boundaries
 */
public interface BoundariesCheck {

    //region Defaults

    static final Severities severity = Severities.ERROR;

    //endregion

    //region Check

    /**
     * Check if index lies within array's boundaries
     *
     * @param index Index
     * @param list  List
     * @param <T>   List type
     * @throws OutOfBoundariesException OutOfBoundariesException
     */
    static <T> int check(int index, ArrayList<T> list) throws OutOfBoundariesException {
        return check(index, list, severity);
    }

    /**
     * Check if index lies within array's boundaries
     *
     * @param index  List
     * @param lower  Lower bound
     * @param higher Higher bound
     * @throws OutOfBoundariesException OutOfBoundariesException
     */
    static int check(int index, int lower, int higher) throws OutOfBoundariesException {
        return check(index, lower, higher, severity);
    }

    /**
     * Check if index lies within array's boundaries
     *
     * @param index    Index
     * @param list     List
     * @param <T>      List type
     * @param severity Error severity
     * @throws OutOfBoundariesException OutOfBoundariesException
     */
    static <T> int check(int index, ArrayList<T> list, Severities severity) throws OutOfBoundariesException {
        int size = list.size();
        if (index < 0 || index > size) {
            throw new OutOfBoundariesException(index, size, severity);
        }
        return index;
    }

    /**
     * Check if index lies within array's boundaries
     *
     * @param index    List
     * @param lower    Lower bound
     * @param higher   Higher bound
     * @param severity Error severity
     * @throws OutOfBoundariesException OutOfBoundariesException
     */
    static int check(int index, int lower, int higher, Severities severity) throws OutOfBoundariesException {
        if (index < lower || index > higher) {
            throw new OutOfBoundariesException(index, lower, higher, severity);
        }
        return index;
    }

    //endregion

    /**
     * Out of boundaries exception
     */
    class OutOfBoundariesException extends BaseException {
        //region Defaults

        private static final ErrorCodes code = ErrorCodes.OUT_OF_BOUNDARIES;

        //endregion

        //region Constructors

        /**
         * Out of boundaries exception for array
         *
         * @param index    Value
         * @param size     Array size
         * @param severity Error severity
         */
        OutOfBoundariesException(int index, int size, Severities severity) {
            super(String.format("Index '%d' was out of array's boundaries: size = '%d'", index, size), severity, code);
        }

        /**
         * Out of boundaries exception
         *
         * @param index    Value
         * @param low      Lower bound
         * @param high     Higher bound
         * @param severity Error severity
         */
        OutOfBoundariesException(int index, int low, int high, Severities severity) {
            super(String.format("Index '%d' was out of boundaries: (%d, %d)", index, low, high), severity, code);
        }

        //endregion
    }
}
