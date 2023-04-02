package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.http.NewsFeedUnauthorizedException;

public class ApiKeyMissingException extends NewsFeedUnauthorizedException {
    public ApiKeyMissingException(String message) {
        super(message);
    }

    public ApiKeyMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}