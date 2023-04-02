package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.http.NewsFeedBadRequest;

public class SourceDoesNotExistException extends NewsFeedBadRequest {
    public SourceDoesNotExistException(String message) {
        super(message);
    }

    public SourceDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
