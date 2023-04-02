package bg.sofia.uni.fmi.mjt.sentiment.ratings;

public enum UserRating {
    UNKNOWN(-1),
    NEGATIVE(0),
    SOMEWHAT_NEGATIVE(1),
    NEUTRAL(2),
    SOMEWHAT_POSITIVE(3),
    POSITIVE(4);

    private final int rating;

    UserRating(final int rating) {
        this.rating = rating;
    }

    public final int getRating() {
        return rating;
    }

    public static String valueOfUserRating(int rating) {
        String toBeReplaced = "_";
        String replaceWith = " ";

        for (UserRating userRating : UserRating.values()) {
            if (userRating.getRating() == rating) {
                return userRating.toString().replaceAll(toBeReplaced, replaceWith).toLowerCase();
            }
        }

        throw new IllegalArgumentException("There is no such user rating!");
    }
}