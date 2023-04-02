package bg.sofia.uni.fmi.mjt.escaperoom.room;

import bg.sofia.uni.fmi.mjt.escaperoom.rating.Ratable;

public class EscapeRoom implements Ratable {

    private final String name;
    private final Theme theme;
    private final Difficulty difficulty;
    private final int maxTimeToEscape;
    private final double priceToPlay;
    private final int maxReviewsCount;
    private double rating;
    private final Review[] reviews;
    private int reviewTimer;
    private final int[] reviewTimerArray;

    public EscapeRoom(String name, Theme theme, Difficulty difficulty, int maxTimeToEscape, double priceToPlay,
                      int maxReviewsCount) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.maxTimeToEscape = maxTimeToEscape;
        this.priceToPlay = priceToPlay;
        this.maxReviewsCount = maxReviewsCount;
        this.rating = 0.0;
        this.reviews = new Review[maxReviewsCount];
        this.reviewTimerArray = new int[maxReviewsCount];
        initialiseReviewTimerArray();
    }

    public void initialiseReviewTimerArray() {
        for(int i = 0; i < maxReviewsCount; i++) {
            reviewTimerArray[i] = -1;
        }
    }

    @Override
    public double getRating() {
        return this.rating;
    }

    public String getName() {
        return this.name;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public int getMaxTimeToEscape() {
        return this.maxTimeToEscape;
    }

    public double getPriceToPlay() {
        return this.priceToPlay;
    }

    public int getMaxReviewsCount() {
        return this.maxReviewsCount;
    }

    public Review[] getReviews() {
        return this.reviews;
    }

    public void addReview(Review review) {
        if(this.reviews.length == 0) {
            return;
        }

        int numberOfReviews = getNumberOfReviews();

        if(numberOfReviews + 1 <= this.maxReviewsCount) {
            fillReviewsArray(numberOfReviews, review);
        }
        else {
            fillReviewsArray(getIndexOfOldestReview(), review);
        }
    }

    public Review[] getSortedByTimeAddedReviews() {
        int sizeOfOrderedReviewsArray = getTheSizeWithNotNullValuesFromReviewArray();

        Review[] orderedReviews = null;
        orderedReviews = new Review[sizeOfOrderedReviewsArray];

        int k = 0;
        int startIndex = getIndexOfOldestReview();

        for(int i = startIndex; i < this.reviewTimerArray.length; i++) {
            if(this.reviews[i] != null) {
                orderedReviews[k++] = this.reviews[i];
            }
        }

        for(int i = 0; i < startIndex; i++) {
            if(this.reviews[i] != null) {
                orderedReviews[k++] = this.reviews[i];
            }
        }

        return orderedReviews;
    }

    private void fillReviewsArray(int index, Review review) {
        this.reviews[index] = review;
        this.reviewTimerArray[index] = ++reviewTimer;

        updateRatingAverage(review);
    }

    private int getNumberOfReviews() {
        int numberOfReviews = 0;

        for(Review r : this.reviews) {
            if(r == null) {
                break;
            }

            numberOfReviews++;
        }

        return numberOfReviews;
    }

    private void updateRatingAverage(Review review) {
        this.rating = (this.rating * (this.reviewTimer - 1) + review.rating())/ this.reviewTimer;
    }

    private int getIndexOfOldestReview() {
        int min = Integer.MAX_VALUE;
        int index = 0;

        for(int i = 0; i < this.reviewTimerArray.length; i++) {
            if(reviewTimerArray[i] != -1 && reviewTimerArray[i] < min) {
                min = reviewTimerArray[i];
                index = i;
            }
        }

        return index;
    }

    private int getTheSizeWithNotNullValuesFromReviewArray() {
        int size = 0;

        for(Review review : this.reviews) {
            if(review != null) {
                size++;
            }
        }

        return size;
    }

}
