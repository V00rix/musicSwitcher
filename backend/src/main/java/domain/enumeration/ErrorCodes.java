package domain.enumeration;

/**
 * Codes for exception
 */
public enum ErrorCodes {
    /**
     * Base exception code
     */
    UNKNOWN(1000),
    /**
     * Base exception code
     */
    BASE(1001),
    /**
     * Unprovided exception code
     */
    UNPROVIDED(1002),
    /**
     * Null or empty exception code
     */
    NULL_OR_EMPTY(1003),
    /**
     * Out of boundaries exception code
     */
    OUT_OF_BOUNDARIES(1004);

    //region Rich value support

    private int numVal;

    /**
     * Create error code from number
     *
     * @param numVal Error code as number
     */
    ErrorCodes(int numVal) {
        this.numVal = numVal;
    }

    /**
     * Get numeric value of error code
     *
     * @return Error code as int
     */
    public int getNumVal() {
        return numVal;
    }

    //endregion

    /**
     * Get error name and code
     *
     * @return Name and code as string: e.g. "BASE (1101)"
     */
    @Override
    public String toString() {
        return this.name() + "(" + this.numVal + ")";
    }
}