package bg.sofia.uni.fmi.mjt.sentiment.word;

public class WordMetadata {
    private double sentimentScore;
    private int wordOccurrences;
    private int wordOccurInReviews;

    public WordMetadata(double sentimentScore, int wordOccurrences, int wordOccurInReviews) {
        this.sentimentScore = sentimentScore;
        this.wordOccurrences = wordOccurrences;
        this.wordOccurInReviews = wordOccurInReviews;
    }

    public double getSentimentScore() {
        return sentimentScore;
    }

    public int getWordOccurrences() {
        return wordOccurrences;
    }

    public void calculateSentimentScore(int score) {
        sentimentScore = sentimentScore + ((score - sentimentScore) / wordOccurInReviews);
    }

    public void incrementOccurrences() {
        ++wordOccurrences;
    }

    public void incrementWordOccurrencesInReviews() {
        ++wordOccurInReviews;
    }
}