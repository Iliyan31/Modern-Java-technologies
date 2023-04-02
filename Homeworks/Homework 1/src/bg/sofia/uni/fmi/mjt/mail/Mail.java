package bg.sofia.uni.fmi.mjt.mail;

import java.time.LocalDateTime;
import java.util.Set;

public record Mail(Account sender, Set<String> recipients, String subject, String body, LocalDateTime received) {
    public Mail {
        if (sender == null) {
            throw new IllegalArgumentException("Sender cannot be null!");
        }

        if (recipients == null) {
            throw new IllegalArgumentException("Recipients cannot be null!");
        }

        for (String recipient : recipients) {
            if (recipient == null) {
                throw new IllegalArgumentException("Recipient in recipients set is null!");
            }
        }

        if (subject == null) {
            throw new IllegalArgumentException("Subject name cannot be null!");
        }

        if (body == null) {
            throw new IllegalArgumentException("Body name cannot be null!");
        }

        if (received == null) { //  || received().isBefore(LocalDateTime.now())   or in the past
            throw new IllegalArgumentException("Received local date time cannot be null");
        }
    }

    public static Mail of(Account sender, Set<String> recipients, String subject, String body, LocalDateTime received) {
        return new Mail(sender, recipients, subject, body, received);
    }
}