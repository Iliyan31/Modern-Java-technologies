package bg.sofia.uni.fmi.mjt.news.feed.exceptions.http;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.NewsFeedException;

public class NewsFeedServerErrorException extends NewsFeedException {
    public NewsFeedServerErrorException(String message) {
        super(message);
    }

    public NewsFeedServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}