package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.http.NewsFeedUnauthorizedException;

public class ApiKeyInvalidException extends NewsFeedUnauthorizedException {
    public ApiKeyInvalidException(String message) {
        super(message);
    }

    public ApiKeyInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}