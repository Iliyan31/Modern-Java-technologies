package bg.sofia.uni.fmi.mjt.sentiment;

import bg.sofia.uni.fmi.mjt.sentiment.exceptions.SentimentDictionarySizeException;
import bg.sofia.uni.fmi.mjt.sentiment.exceptions.StopwordException;
import bg.sofia.uni.fmi.mjt.sentiment.ratings.UserRating;
import bg.sofia.uni.fmi.mjt.sentiment.word.WordMetadata;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

public abstract class MovieReviewSentimentAnalyzerValidator {
    void validateStopwordsInReader(Reader stopwordsIn) {
        if (stopwordsIn == null) {
            throw new IllegalArgumentException("The stopwordsIn Reader must not be null!");
        }
    }

    void validateReviewsInReader(Reader reviewsIn) {
        if (reviewsIn == null) {
            throw new IllegalArgumentException("The reviewsIn Reader must not be null!");
        }
    }

    void validateReviewsOutWriter(Writer reviewsOut) {
        if (reviewsOut == null) {
            throw new IllegalArgumentException("The reviewsOut Writer must not be null!");
        }
    }

    void validateGivenString(String givenString) {
        if (givenString == null || givenString.isEmpty() || givenString.isBlank()) {
            throw new IllegalArgumentException("The given string cannot be null, empty or blank!");
        }
    }

    void validateForStopword(String word, Set<String> stopwords) {
        if (stopwords.contains(word.toUpperCase())) {
            throw new StopwordException("The given word is stopword!");
        }
    }

    void validateRangeNumber(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The range number cannot be below 0!");
        }
    }

    void validateSentiment(int sentiment) {
        if (sentiment < UserRating.NEGATIVE.getRating() || sentiment > UserRating.POSITIVE.getRating()) {
            throw new IllegalArgumentException("The sentiment must be between 0 and 4!");
        }
    }

    void validateSentimentDictionarySize(int n, Map<String, WordMetadata> sentimentDictionary) {
        if (sentimentDictionary.size() < n) {
            throw new SentimentDictionarySizeException("The sentiment dictionary is not big enough!");
        }
    }
}