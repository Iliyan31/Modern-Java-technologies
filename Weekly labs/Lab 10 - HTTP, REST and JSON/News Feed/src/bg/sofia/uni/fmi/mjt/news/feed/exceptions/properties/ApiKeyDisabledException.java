package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.http.NewsFeedUnauthorizedException;

public class ApiKeyDisabledException extends NewsFeedUnauthorizedException {
    public ApiKeyDisabledException(String message) {
        super(message);
    }

    public ApiKeyDisabledException(String message, Throwable cause) {
        super(message, cause);
    }
}