package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FoodEntryTest {

    @Test
    void testConstructorForIllegalArgumentExceptionForNullFood() {
        assertThrows(IllegalArgumentException.class, () -> new FoodEntry(null, 1, new NutritionInfo(50, 20, 30)),
            "The food must not be null!");
    }

    @Test
    void testConstructorForIllegalArgumentExceptionForBlankFood() {
        assertThrows(IllegalArgumentException.class, () -> new FoodEntry(" ", 1, new NutritionInfo(50, 20, 30)),
            "The food must not be blank!");
    }

    @Test
    void testConstructorForIllegalArgumentExceptionForEmptyFood() {
        assertThrows(IllegalArgumentException.class, () -> new FoodEntry("", 1, new NutritionInfo(50, 20, 30)),
            "The food must not be empty!");
    }

    @Test
    void testConstructorForIllegalArgumentExceptionForNegativeServingSize() {
        assertThrows(IllegalArgumentException.class, () -> new FoodEntry("a", -1, new NutritionInfo(50, 20, 30)),
            "The serving size must not be negative number!");
    }

    @Test
    void testConstructorForIllegalArgumentExceptionForNullNutrientInfo() {
        assertThrows(IllegalArgumentException.class, () -> new FoodEntry("a", 1, null),
            "The nutrition info must not be null!");
    }
}
