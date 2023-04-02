package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.http.NewsFeedTooManyRequestsException;

public class ApiKeyExhaustedException extends NewsFeedTooManyRequestsException {
    public ApiKeyExhaustedException(String message) {
        super(message);
    }

    public ApiKeyExhaustedException(String message, Throwable cause) {
        super(message, cause);
    }
}