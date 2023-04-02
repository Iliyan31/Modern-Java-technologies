package bg.sofia.uni.fmi.mjt.mail;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MailTest {
    @Test
    void testMailNullSender() {
        assertThrows(IllegalArgumentException.class,
            () -> Mail.of(null, new HashSet<>(), "a", "a", LocalDateTime.now()), "Null Sender!");
    }

    @Test
    void testMailNullRecipients() {
        assertThrows(IllegalArgumentException.class,
            () -> Mail.of(Account.of("a", "a"), null, "a", "a", LocalDateTime.now()), "Null Recipient set!");
    }

    @Test
    void testMailNullRecipientsNullElementInSet() {
        Set<String> s = new HashSet<>();
        s.add("a");
        s.add(null);
        assertThrows(IllegalArgumentException.class,
            () -> Mail.of(Account.of("a", "a"), s, "a", "a", LocalDateTime.now()), "Null element in recipient set!");
    }

    @Test
    void testMailNullSubject() {
        assertThrows(IllegalArgumentException.class,
            () -> Mail.of(Account.of("a", "a"), new HashSet<>(), null, "a", LocalDateTime.now()), "Null subject!");
    }

    @Test
    void testMailNullBody() {
        assertThrows(IllegalArgumentException.class,
            () -> Mail.of(Account.of("a", "a"), new HashSet<>(), "a", null, LocalDateTime.now()), "Null body!");
    }

    @Test
    void testMailNullReceived() {
        assertThrows(IllegalArgumentException.class,
            () -> Mail.of(Account.of("a", "a"), new HashSet<>(), "a", "a", null), "Null body!");
    }

}
