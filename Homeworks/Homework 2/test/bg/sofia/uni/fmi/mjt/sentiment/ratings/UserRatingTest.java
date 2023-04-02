package bg.sofia.uni.fmi.mjt.sentiment.ratings;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRatingTest {
    @Test
    void testValueOfUserRatingLessThanMinusOne() {
        assertThrows(IllegalArgumentException.class, () -> UserRating.valueOfUserRating(-2),
            "The user rating cannot be less than -1!");
    }

    @Test
    void testValueOfUserRatingGreaterThanFour() {
        assertThrows(IllegalArgumentException.class, () -> UserRating.valueOfUserRating(5),
            "The user rating cannot be greater than 4!");
    }

    @Test
    void testValueOfUserRatingMinusOne() {
        assertEquals("unknown", UserRating.valueOfUserRating(-1),
            "The Should correctly return the user rating value by name for -1");
    }

    @Test
    void testValueOfUserRatingZero() {
        assertEquals("negative", UserRating.valueOfUserRating(0),
            "The Should correctly return the user rating value by name for 0");
    }

    @Test
    void testValueOfUserRatingOne() {
        assertEquals("somewhat negative", UserRating.valueOfUserRating(1),
            "The Should correctly return the user rating value by name for 1");
    }

    @Test
    void testValueOfUserRatingTwo() {
        assertEquals("neutral", UserRating.valueOfUserRating(2),
            "The Should correctly return the user rating value by name for 2");
    }

    @Test
    void testValueOfUserRatingThree() {
        assertEquals("somewhat positive", UserRating.valueOfUserRating(3),
            "The Should correctly return the user rating value by name for 3");
    }

    @Test
    void testValueOfUserRatingFour() {
        assertEquals("positive", UserRating.valueOfUserRating(4),
            "The Should correctly return the user rating value by name for 4");
    }
}