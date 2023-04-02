package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FoodEntryProteinContentComparatorTest {
    FoodEntryProteinContentComparator com = new FoodEntryProteinContentComparator();

    @Test
    void testCompareSecondGreaterThanFirstFoodEntry() {
        FoodEntry f1 = new FoodEntry("A", 2, new NutritionInfo(50, 30, 20));
        FoodEntry f2 = new FoodEntry("B", 3, new NutritionInfo(50, 30, 20));
        assertEquals(-1, com.compare(f1, f2), "FoodEntry 2 must be greater than FoodEntry 1!");
    }

    @Test
    void testCompareFirstGreaterThanSecondFoodEntry() {
        FoodEntry f1 = new FoodEntry("A", 3, new NutritionInfo(50, 30, 20));
        FoodEntry f2 = new FoodEntry("B", 2, new NutritionInfo(50, 30, 20));
        assertEquals(1, com.compare(f1, f2), "FoodEntry 2 must be greater than FoodEntry 1!");
    }

    @Test
    void testCompareFirstEqualsSecondFoodEntry() {
        FoodEntry f1 = new FoodEntry("A", 3, new NutritionInfo(50, 20, 30));
        FoodEntry f2 = new FoodEntry("B", 3, new NutritionInfo(50, 20, 30));
        assertEquals(0, com.compare(f1, f2), "FoodEntry 2 must be greater than FoodEntry 1!");
    }
}
