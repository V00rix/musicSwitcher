package components.util;

public interface IRichConsole {
    //region Options
    /**
     * Reset
     */
    String RESET = "\033[0m";  // Text Reset

    /**
     * Regular colors
     */
    abstract class Colors {
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
        public static final String GREEN = "\033[0;32m";   // GREEN
        public static final String YELLOW = "\033[0;33m";  // YELLOW
        public static final String BLUE = "\033[0;34m";    // BLUE
        public static final String PURPLE = "\033[0;35m";  // PURPLE
        public static final String CYAN = "\033[0;36m";    // CYAN
        public static final String WHITE = "\033[0;37m";   // WHITE

    }

    /**
     * Bold
     */
    abstract class BoldColors {
        public static final String BLACK = "\033[1;30m";  // BLACK
        public static final String RED = "\033[1;31m";    // RED
        public static final String GREEN = "\033[1;32m";  // GREEN
        public static final String YELLOW = "\033[1;33m"; // YELLOW
        public static final String BLUE = "\033[1;34m";   // BLUE
        public static final String PURPLE = "\033[1;35m"; // PURPLE
        public static final String CYAN = "\033[1;36m";   // CYAN
        public static final String WHITE = "\033[1;37m";  // WHITE
    }

    /**
     * Underlined
     */
    abstract class UnderlinedColors {
        public static final String BLACK = "\033[4;30m";  // BLACK
        public static final String RED = "\033[4;31m";    // RED
        public static final String GREEN = "\033[4;32m";  // GREEN
        public static final String YELLOW = "\033[4;33m"; // YELLOW
        public static final String BLUE = "\033[4;34m";   // BLUE
        public static final String PURPLE = "\033[4;35m"; // PURPLE
        public static final String CYAN = "\033[4;36m";   // CYAN
        public static final String WHITE = "\033[4;37m";  // WHITE
    }

    /**
     * Backgrounds
     */
    abstract class BackgroundColors {
        public static final String BLACK = "\033[40m";  // BLACK
        public static final String RED = "\033[41m";    // RED
        public static final String GREEN = "\033[42m";  // GREEN
        public static final String YELLOW = "\033[43m"; // YELLOW
        public static final String BLUE = "\033[44m";   // BLUE
        public static final String PURPLE = "\033[45m"; // PURPLE
        public static final String CYAN = "\033[46m";   // CYAN
        public static final String WHITE = "\033[47m";  // WHITE
    }

    /**
     * High intensity
     */
    abstract class BrightColors {

        public static final String BLACK = "\033[0;90m";  // BLACK
        public static final String RED = "\033[0;91m";    // RED
        public static final String GREEN = "\033[0;92m";  // GREEN
        public static final String YELLOW = "\033[0;93m"; // YELLOW
        public static final String BLUE = "\033[0;94m";   // BLUE
        public static final String PURPLE = "\033[0;95m"; // PURPLE
        public static final String CYAN = "\033[0;96m";   // CYAN
        public static final String WHITE = "\033[0;97m";  // WHITE
    }

    /**
     * Bold High Intensity
     */
    abstract class BoldBrightColors {
        public static final String BLACK = "\033[1;90m"; // BLACK
        public static final String RED = "\033[1;91m";   // RED
        public static final String GREEN = "\033[1;92m"; // GREEN
        public static final String YELLOW = "\033[1;93m";// YELLOW
        public static final String BLUE = "\033[1;94m";  // BLUE
        public static final String PURPLE = "\033[1;95m";// PURPLE
        public static final String CYAN = "\033[1;96m";  // CYAN
        public static final String WHITE = "\033[1;97m"; // WHITE
    }

    /**
     * High Intensity backgrounds
     */
    abstract class BrightBackgroundColors {
        public static final String BLACK = "\033[0;100m";// BLACK
        public static final String RED = "\033[0;101m";// RED
        public static final String GREEN = "\033[0;102m";// GREEN
        public static final String YELLOW = "\033[0;103m";// YELLOW
        public static final String BLUE = "\033[0;104m";// BLUE
        public static final String PURPLE = "\033[0;105m"; // PURPLE
        public static final String CYAN = "\033[0;106m";  // CYAN
        public static final String WHITE = "\033[0;107m";   // WHITE
    }
    //endregion

    /**
     * Style a sting
     * @param s String
     * @param options Style options
     * @return Styled string
     */
    default String style(String s, final String... options) {
        StringBuilder str = new StringBuilder();
        for (String option : options) {
            str.append(option);
        }
        str.append(s);
        str.append(RESET);

        return str.toString();
    }
}
