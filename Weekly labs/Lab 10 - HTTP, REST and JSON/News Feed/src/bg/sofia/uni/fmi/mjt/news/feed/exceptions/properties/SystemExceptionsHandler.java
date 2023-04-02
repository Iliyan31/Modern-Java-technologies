package bg.sofia.uni.fmi.mjt.news.feed.exceptions.properties;

import bg.sofia.uni.fmi.mjt.news.feed.entities.NewsFeedResponseErrorBody;
import bg.sofia.uni.fmi.mjt.news.feed.exceptions.NewsFeedException;

public class SystemExceptionsHandler {
    NewsFeedResponseErrorBody feedResponseError;

    public SystemExceptionsHandler(NewsFeedResponseErrorBody newsFeedResponseErrorBody) {
        this.feedResponseError = newsFeedResponseErrorBody;
    }

    public void throwCorrectSystemException() throws NewsFeedException {
        switch (feedResponseError.code()) {
            case "apiKeyDisabled" -> throw new ApiKeyDisabledException(feedResponseError.message());
            case "apiKeyExhausted" -> throw new ApiKeyExhaustedException(feedResponseError.message());
            case "apiKeyInvalid" -> throw new ApiKeyInvalidException(feedResponseError.message());
            case "apiKeyMissing" -> throw new ApiKeyMissingException(feedResponseError.message());
            case "parameterInvalid" -> throw new ParameterInvalidException(feedResponseError.message());
            case "parametersMissing" -> throw new ParametersMissingException(feedResponseError.message());
            case "rateLimited" -> throw new RateLimitedException(feedResponseError.message());
            case "sourcesTooMany" -> throw new SourcesTooManyException(feedResponseError.message());
            case "sourceDoesNotExist" -> throw new SourceDoesNotExistException(feedResponseError.message());
            case "unexpectedError" -> throw new UnexpectedErrorException(feedResponseError.message());
            default -> throw new NewsFeedException("Unknown http request exception!");
        }
    }
}