package pl.crystalek.sokoban.util;

enum Time {
    YEAR("rok", "lata", "lat", "y", 31_536_000_000L),
    MONTH("miesiąc", "miesiace", "miesiecy", "m", 2_592_000_000L),
    WEEK("tydzień", "tygodnie", "tygodni", "w", 604_800_000L),
    DAY("dzień", "dni", "dni", "d", 86_400_000L),
    HOUR("godzina", "godziny", "godzin", "h", 3_600_000L),
    MINUTE("minuta", "minuty", "minut", "min", 60_000),
    SECOND("sekunda", "sekundy", "sekund", "s", 1_000);

    private final String text1;
    private final String text2;
    private final String text3;
    private final String shortForm;
    private final long millis;

    Time(final String text1, final String text2, final String text3, final String shortForm, final long millis) {
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.shortForm = shortForm;
        this.millis = millis;
    }

    String getForm(final long number, final boolean shortForm) {
        if (number == 0) {
            return "";
        }

        if (shortForm) {
            return this.shortForm;
        }

        if (number == 1) {
            return text1;
        }

        final long onesDigit = number % 10;
        final long tensNumber = number % 100;

        if (onesDigit < 2 || onesDigit > 4 || tensNumber >= 12 && tensNumber <= 14) {
            return text3;
        }

        return text2;
    }

    long getMillis() {
        return millis;
    }
}
