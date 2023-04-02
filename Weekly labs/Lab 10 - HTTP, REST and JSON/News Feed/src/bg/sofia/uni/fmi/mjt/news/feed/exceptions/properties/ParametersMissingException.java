package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.http.NewsFeedBadRequest;

public class ParametersMissingException extends NewsFeedBadRequest {
    public ParametersMissingException(String message) {
        super(message);
    }

    public ParametersMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}