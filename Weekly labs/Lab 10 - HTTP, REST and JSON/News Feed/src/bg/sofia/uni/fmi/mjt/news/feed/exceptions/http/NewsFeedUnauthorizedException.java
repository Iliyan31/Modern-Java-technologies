package bg.sofia.uni.fmi.mjt.news.feed.exceptions.http;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.NewsFeedException;

public class NewsFeedUnauthorizedException extends NewsFeedException {
    public NewsFeedUnauthorizedException(String message) {
        super(message);
    }

    public NewsFeedUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}