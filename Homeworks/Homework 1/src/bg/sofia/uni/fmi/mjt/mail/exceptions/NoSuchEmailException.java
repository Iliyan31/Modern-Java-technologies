package bg.sofia.uni.fmi.mjt.mail.exceptions;

public class NoSuchEmailException extends RuntimeException {
    public NoSuchEmailException(String message) {
        super(message);
    }

//    public NoSuchEmailException(String message, Throwable cause) {
//        super(message, cause);
//    }
}
