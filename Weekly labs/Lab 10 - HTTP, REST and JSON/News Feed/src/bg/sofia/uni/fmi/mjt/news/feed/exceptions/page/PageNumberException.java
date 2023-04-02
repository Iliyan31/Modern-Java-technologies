package bg.sofia.uni.fmi.mjt.news.feed.exceptions.page;

public class PageNumberException extends RuntimeException {
    public PageNumberException(String message) {
        super(message);
    }

    public PageNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}