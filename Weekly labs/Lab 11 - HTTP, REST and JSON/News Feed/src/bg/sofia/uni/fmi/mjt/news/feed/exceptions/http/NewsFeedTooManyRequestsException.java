package bg.sofia.uni.fmi.mjt.news.feed.exceptions.http;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.NewsFeedException;

public class NewsFeedTooManyRequestsException extends NewsFeedException {
    public NewsFeedTooManyRequestsException(String message) {
        super(message);
    }

    public NewsFeedTooManyRequestsException(String message, Throwable cause) {
        super(message, cause);
    }
}