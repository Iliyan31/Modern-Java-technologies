package bg.sofia.uni.fmi.mjt.mail.sent;

import bg.sofia.uni.fmi.mjt.mail.Mail;

import java.util.HashSet;
import java.util.Set;

public class Sent {
    private final Set<Mail> sentMails;

    public Sent() {
        this.sentMails = new HashSet<>();
    }

    public Set<Mail> getSentMails() {
        return sentMails;
    }

    public void addMail(Mail mail) {
        sentMails.add(mail);
    }
}
