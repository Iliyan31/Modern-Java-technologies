package bg.sofia.uni.fmi.mjt.mail;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {
    @Test
    void testEmailForNullValue() {
        assertThrows(IllegalArgumentException.class, () -> Account.of(null, "a"), "Null value for account email!");
    }

    @Test
    void testNameForNullValue() {
        assertThrows(IllegalArgumentException.class, () -> Account.of("a", null), "Null value for account email!");
    }
}
