package bg.sofia.uni.fmi.mjt.news.feed.exceptions.http;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.NewsFeedException;

public class NewsFeedBadRequest extends NewsFeedException {
    public NewsFeedBadRequest(String message) {
        super(message);
    }

    public NewsFeedBadRequest(String message, Throwable cause) {
        super(message, cause);
    }
}