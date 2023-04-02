package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.exceptions.http.NewsFeedBadRequest;

public class ParameterInvalidException extends NewsFeedBadRequest {
    public ParameterInvalidException(String message) {
        super(message);
    }

    public ParameterInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}