package domain.enumeration;

/**
 * Codes for exception
 */
public enum ErrorCodes {
    /**
     * Base exception code
     */
    BASE(1001),

    /**
     * Unprovided exception code
     */
    UNPROVIDED(1002);

    private int numVal;

    /**
     * Get error code from number
     * @param numVal Error code as number
     */
    ErrorCodes(int numVal) {
        this.numVal = numVal;
    }

    /**
     * Get numeric value of error code
     * @return Error code as int
     */
    public int getNumVal() {
        return numVal;
    }

    /**
     * Get error name and code
     * @return Name and code as string: e.g. "BASE (1101)"
     */
    @Override
    public String toString() {
        return this.name() + "(" + this.numVal + ")";
    }
}