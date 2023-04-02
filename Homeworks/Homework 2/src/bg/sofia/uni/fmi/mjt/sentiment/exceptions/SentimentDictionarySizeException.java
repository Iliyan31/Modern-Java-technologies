package bg.sofia.uni.fmi.mjt.sentiment.exceptions;

public class SentimentDictionarySizeException extends RuntimeException {
    public SentimentDictionarySizeException(String message) {
        super(message);
    }

    public SentimentDictionarySizeException(String message, Throwable cause) {
        super(message, cause);
    }
}