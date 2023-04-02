package bg.sofia.uni.fmi.mjt.sentiment;

import bg.sofia.uni.fmi.mjt.sentiment.ratings.UserRating;
import bg.sofia.uni.fmi.mjt.sentiment.word.WordMetadata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieReviewSentimentAnalyzer extends MovieReviewSentimentAnalyzerValidator implements SentimentAnalyzer {
    private final Set<String> stopwords;
    private final Map<String, WordMetadata> sentimentDictionary;
    private final Writer reviewsOut;
    private boolean isReviewsInEmpty;

    public MovieReviewSentimentAnalyzer(Reader stopwordsIn, Reader reviewsIn, Writer reviewsOut) {
        validateStopwordsInReader(stopwordsIn);
        validateReviewsInReader(reviewsIn);
        validateReviewsOutWriter(reviewsOut);

        this.sentimentDictionary = new HashMap<>();
        this.reviewsOut = reviewsOut;
        this.stopwords = new HashSet<>();

        checkForEmptyReviewsInReader(reviewsIn);
        extractAllStopWords(stopwordsIn);
        getUserReviews(reviewsIn);
    }

    @Override
    public double getReviewSentiment(String review) {
        validateGivenString(review);

        return extractWords(review.toUpperCase()).stream()
            .filter(sentimentDictionary::containsKey)
            .mapToDouble(w -> sentimentDictionary.get(w).getSentimentScore())
            .average()
            .orElse(UserRating.UNKNOWN.getRating());
    }

    @Override
    public String getReviewSentimentAsName(String review) {
        validateGivenString(review);

        return UserRating.valueOfUserRating((int) Math.round(getReviewSentiment(review)));
    }


    @Override
    public double getWordSentiment(String word) {
        validateGivenString(word);

        if (!sentimentDictionary.containsKey(word.toUpperCase())) {
            return UserRating.UNKNOWN.getRating();
        }

        return sentimentDictionary.get(word.toUpperCase()).getSentimentScore();
    }

    @Override
    public int getWordFrequency(String word) {
        validateGivenString(word);
        validateForStopword(word, stopwords);

        if (!sentimentDictionary.containsKey(word.toUpperCase())) {
            return 0;
        }

        return sentimentDictionary.get(word.toUpperCase()).getWordOccurrences();
    }

    @Override
    public List<String> getMostFrequentWords(int n) {
        validateRangeNumber(n);
        validateSentimentDictionarySize(n, sentimentDictionary); // could be without

        return sentimentDictionary.entrySet().stream()
            .sorted((e1, e2) -> Integer.compare(e2.getValue().getWordOccurrences(), e1.getValue().getWordOccurrences()))
            .map(Map.Entry::getKey)
            .limit(n)
            .toList();

    }

    @Override
    public List<String> getMostPositiveWords(int n) {
        validateRangeNumber(n);
        validateSentimentDictionarySize(n, sentimentDictionary); // could be without

        return sentimentDictionary.entrySet().stream()
            .sorted((e1, e2) -> Double.compare(e2.getValue().getSentimentScore(), e1.getValue().getSentimentScore()))
            .map(Map.Entry::getKey)
            .limit(n)
            .toList();
    }

    @Override
    public List<String> getMostNegativeWords(int n) {
        validateRangeNumber(n);
        validateSentimentDictionarySize(n, sentimentDictionary); // could be without

        return sentimentDictionary.entrySet().stream()
            .sorted(Comparator.comparingDouble(e -> e.getValue().getSentimentScore()))
            .map(Map.Entry::getKey)
            .limit(n)
            .toList();
    }

    @Override
    public boolean appendReview(String review, int sentiment) {
        validateGivenString(review);
        validateSentiment(sentiment);

        String blankSpace = " ";
        String reviewWithSentiment = sentiment + blankSpace + review;

        extractDataFromReview(reviewWithSentiment.toUpperCase());

        try (var bufferedWriter = new BufferedWriter(reviewsOut)) {
            if (!isReviewsInEmpty) {
                bufferedWriter.write(System.lineSeparator());
                bufferedWriter.flush();
            }

            bufferedWriter.write(reviewWithSentiment);
            bufferedWriter.flush();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public int getSentimentDictionarySize() {
        return sentimentDictionary.size();
    }

    @Override
    public boolean isStopWord(String word) {
        validateGivenString(word);

        return stopwords.contains(word.toUpperCase());
    }

    private void extractAllStopWords(Reader stopwordsIn) {
        String line;

        try (var bufferedReader = new BufferedReader(stopwordsIn)) {
            while ((line = bufferedReader.readLine()) != null) {
                stopwords.add(line.toUpperCase());
            }
        } catch (IOException e) {
            throw new RuntimeException("There was problem while reading from the stopwordsIn", e);
        }
    }

    private void getUserReviews(Reader reviewsIn) {
        String line;

        try (var bufferedReader = new BufferedReader(reviewsIn)) {
            while ((line = bufferedReader.readLine()) != null) {
                extractDataFromReview(line.toUpperCase());
            }
        } catch (IOException e) {
            throw new RuntimeException("There was problem while reading from the reviews", e);
        }
    }

    private void extractDataFromReview(String line) {
        final int indexOfFirstSpaceForScore = 2;
        String splittingWord = " ";

        String[] splitString = line.split(splittingWord, indexOfFirstSpaceForScore);

        final int score = Integer.parseInt(splitString[0]);

        fillWordsWithMetadataMap(extractWords(splitString[1]), score);
    }

    private List<String> extractWords(String line) {
        final int minimumWordLength = 2;
        String splittingWord = " ";
        String regexForAnyCharacterExcept = "[^0-9A-Za-z']";

        String filteredLine = line.replaceAll(regexForAnyCharacterExcept, splittingWord);

        return Arrays.stream(filteredLine.split(splittingWord))
            .filter(w -> !w.isEmpty() && !w.isBlank() && w.length() >= minimumWordLength && !stopwords.contains(w))
            .collect(Collectors.toList());
    }

    private void fillWordsWithMetadataMap(List<String> filteredWords, int score) {
        Set<String> repeatingWordsForLine = new HashSet<>();

        for (String word : filteredWords) {
            if (!sentimentDictionary.containsKey(word)) {
                sentimentDictionary.put(word, new WordMetadata(0, 0, 0));
            }

            if (!repeatingWordsForLine.contains(word)) {
                repeatingWordsForLine.add(word);

                sentimentDictionary.get(word).incrementWordOccurrencesInReviews();
                sentimentDictionary.get(word).calculateSentimentScore(score);
            }

            sentimentDictionary.get(word).incrementOccurrences();
        }
    }

    private void checkForEmptyReviewsInReader(Reader reviewsIn) {
        try {
            isReviewsInEmpty = !reviewsIn.ready();
        } catch (IOException e) {
            throw new RuntimeException("There was problem in ready() method when checking for empty reviewsIn reader!",
                e);
        }
    }
}