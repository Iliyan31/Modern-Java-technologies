package bg.sofia.uni.fmi.mjt.mail;

public record Account(String emailAddress, String name) {
    public Account {
        if (emailAddress == null) {
            throw new IllegalArgumentException("Account email address cannot be null!");
        }

        if (name == null) {
            throw new IllegalArgumentException("Account name cannot be null");
        }
    }

    public static Account of(String emailAddress, String name) {
        return new Account(emailAddress, name);
    }
}