package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.http.NewsFeedBadRequest;

public class SourcesTooManyException extends NewsFeedBadRequest {
    public SourcesTooManyException(String message) {
        super(message);
    }

    public SourcesTooManyException(String message, Throwable cause) {
        super(message, cause);
    }
}