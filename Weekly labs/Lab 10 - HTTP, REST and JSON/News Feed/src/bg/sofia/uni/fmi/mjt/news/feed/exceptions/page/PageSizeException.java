package bg.sofia.uni.fmi.mjt.news.feed.exceptions.page;

public class PageSizeException extends RuntimeException {
    public PageSizeException(String message) {
        super(message);
    }

    public PageSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}