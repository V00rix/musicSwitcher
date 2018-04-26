package domain.enumeration;

public enum Statuses {
    /**
     * Media is playing
     */
    PLAYING("Playing media");

    //region Rich value support

    private String value;

    /**
     * Create status from string
     *
     * @param value Error code as number
     */
    Statuses(String value) {
        this.value = value;
    }

    /**
     * Get string value of status
     *
     * @return Error code as int
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Get error name and code
     *
     * @return Name and code as string: e.g. "BASE (1101)"
     */
    @Override
    public String toString() {
        return this.name();
    }

    //endregion
}
