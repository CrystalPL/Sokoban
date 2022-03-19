package pl.crystalek.sokoban;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.crystalek.sokoban.util.TimeUtil;

final class TimeUtilTester {

    @Test
    void timeUtilTest() {
        final String dateInShortForm = TimeUtil.getDateInString(8712638L, ", ", true);
        final String dateInLongForm = TimeUtil.getDateInString(8712638L, ", ", false);

        Assertions.assertEquals(dateInShortForm, "2 h, 25 min, 12 s");
        Assertions.assertEquals(dateInLongForm, "2 godziny, 25 minut, 12 sekund");
    }
}
