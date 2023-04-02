package bg.sofia.uni.fmi.mjt.sentiment.exceptions;

public class StopwordException extends RuntimeException {
    public StopwordException(String message) {
        super(message);
    }

    public StopwordException(String message, Throwable cause) {
        super(message, cause);
    }
}