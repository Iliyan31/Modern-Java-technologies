package bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NutritionInfoTest {
    @Test
    void testConstructorForNegativeFats() {
        assertThrows(IllegalArgumentException.class, () -> new NutritionInfo(1, -1, 1));
    }

    @Test
    void testConstructorForNegativeCarbohydrates() {
        assertThrows(IllegalArgumentException.class, () -> new NutritionInfo(-1, 1, 1));
    }

    @Test
    void testConstructorForNegativeProteins() {
        assertThrows(IllegalArgumentException.class, () -> new NutritionInfo(1, 1, -1));
    }

    @Test
    void testSumGramsDoesntEqual100() {
        assertThrows(IllegalArgumentException.class, () -> new NutritionInfo(22, 23, 24),
            "Throws IllegalArgumentException if the sum does not add up to 100!");
    }

    @Test
    void testSumGramsEqual100() {
        NutritionInfo n = new NutritionInfo(55.151234, 23.45345, 21.395316);
        assertEquals(100.0, n.fats() + n.proteins() + n.carbohydrates(), 0.000001,
            "The sum of fats, proteins and carbohydrates must add up to 100!");
    }

    @Test
    void testCaloriesForCorrectValue() {
        NutritionInfo n = new NutritionInfo(55.151234, 23.45345, 21.395316);
        assertEquals(517.26725, n.calories(), 0.000001, "It should sum correctly the calories!");
    }

}
