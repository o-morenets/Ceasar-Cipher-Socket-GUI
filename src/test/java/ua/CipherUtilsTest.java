package ua;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CipherUtilsTest {

    @Test
    public void encode__shift_0_returns_same_letter() {
        Assertions.assertEquals("a", CipherUtils.encrypt("a", 0));
    }

    @Test
    public void encode__shift_1_returns_next_letter() {
        Assertions.assertEquals("b", CipherUtils.encrypt("a", 1));
    }

    @Test
    public void encode__shift_minus_1_returns_previous_letter() {
        Assertions.assertEquals("a", CipherUtils.encrypt("b", -1));
    }

    @Test
    public void encode__last_letter_shift_1_returns_first_letter() {
        Assertions.assertEquals("a", CipherUtils.encrypt("z", 1));
    }

    @Test
    public void encode__first_letter_shift_minus_1_returns_last_letter() {
        Assertions.assertEquals("z", CipherUtils.encrypt("a", -1));
    }

    @Test
    public void encode__shift_25_returns_last_letter() {
        Assertions.assertEquals("z", CipherUtils.encrypt("a", 25));
    }

    @Test
    public void encode__shift_26_returns_first_letter() {
        Assertions.assertEquals("a", CipherUtils.encrypt("a", 26));
    }

    @Test
    public void encode__shift_5x26_returns_same_letter() {
        Assertions.assertEquals("f", CipherUtils.encrypt("f", 5 * 26));
    }

    @Test
    public void encode__shift_minus_5x26_returns_same_letter() {
        Assertions.assertEquals("f", CipherUtils.encrypt("f", -5 * 26));
    }

    @Test
    public void encode__shift_5x26_plus_3_returns_shifted_by_3_letter() {
        Assertions.assertEquals("d", CipherUtils.encrypt("a", 5 * 26 + 3));
    }

    @Test
    void encode__long_text() {
        Assertions.assertEquals("rxfsuz", CipherUtils.encrypt("qwerty", 1));
    }
}