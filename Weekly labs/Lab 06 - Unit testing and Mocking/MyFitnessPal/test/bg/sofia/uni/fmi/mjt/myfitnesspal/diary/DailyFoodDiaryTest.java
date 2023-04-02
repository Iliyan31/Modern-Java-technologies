package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import bg.sofia.uni.fmi.mjt.myfitnesspal.exception.UnknownFoodException;
import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfo;
import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfoAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DailyFoodDiaryTest {

    @Mock
    private NutritionInfoAPI nutritionInfoAPIMock;

    @InjectMocks
    private DailyFoodDiary diary;

    @Test
    void testAddFoodForNullMealIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> diary.addFood(null, "a", 2),
            "The meal must not be null!");
    }

    @Test
    void testAddFoodForNullFoodNameIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> diary.addFood(Meal.BREAKFAST, null, 2),
            "The food name must not be null!");
    }

    @Test
    void testAddFoodForEmptyFoodNameIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> diary.addFood(Meal.BREAKFAST, "", 2),
            "The food name must not be empty!");
    }

    @Test
    void testAddFoodForBlankFoodNameIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> diary.addFood(Meal.BREAKFAST, "      ", 2),
            "The food name must not be blank!");
    }

    @Test
    void testAddFoodNegativeServingSizeIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> diary.addFood(Meal.BREAKFAST, "apple", -2),
            "The serving size must not be negative number!");
    }

    @Test
    void testAddFoodWithNullNutritionInfoAPI() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(null);

        assertThrows(UnknownFoodException.class, () -> diary.addFood(Meal.BREAKFAST, "apple", 2),
            "Throws UnknownFoodException when there is no such nutrition info!");

        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("apple");
    }

    @Test
    void testAddFood() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(new NutritionInfo(50, 20, 30));

        diary.addFood(Meal.BREAKFAST, "apple", 2);

        assertEquals(1, diary.getMealsSize(), "The needed size must be 1 when one food entry added!");

        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("apple");
    }

    @Test
    void testGetAllFoodEntries() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(new NutritionInfo(50, 20, 30));
        when(nutritionInfoAPIMock.getNutritionInfo("sandwich")).thenReturn(new NutritionInfo(75, 10, 15));
        when(nutritionInfoAPIMock.getNutritionInfo("soup")).thenReturn(new NutritionInfo(60, 30, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("pear")).thenReturn(new NutritionInfo(50, 30, 20));
        when(nutritionInfoAPIMock.getNutritionInfo("pizza")).thenReturn(new NutritionInfo(85, 10, 5));

        diary.addFood(Meal.BREAKFAST, "apple", 2);
        diary.addFood(Meal.LUNCH, "sandwich", 1);
        diary.addFood(Meal.DINNER, "soup", 1);
        diary.addFood(Meal.BREAKFAST, "pear", 1);
        diary.addFood(Meal.DINNER, "pizza", 1);

        assertEquals(5, diary.getAllFoodEntries().size(),
            "The needed size must be above 0 when food entries are added!");

        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("apple");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("sandwich");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("soup");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pear");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pizza");
    }

    @Test
    void testGetAllFoodEntriesByProteinContent() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(new NutritionInfo(50, 20, 30));
        when(nutritionInfoAPIMock.getNutritionInfo("sandwich")).thenReturn(new NutritionInfo(75, 10, 15));
        when(nutritionInfoAPIMock.getNutritionInfo("soup")).thenReturn(new NutritionInfo(60, 30, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("pear")).thenReturn(new NutritionInfo(50, 30, 20));
        when(nutritionInfoAPIMock.getNutritionInfo("pizza")).thenReturn(new NutritionInfo(85, 10, 5));

        diary.addFood(Meal.BREAKFAST, "apple", 2);
        diary.addFood(Meal.LUNCH, "sandwich", 1);
        diary.addFood(Meal.DINNER, "soup", 1);
        diary.addFood(Meal.BREAKFAST, "pear", 1);
        diary.addFood(Meal.DINNER, "pizza", 1);

        List<FoodEntry> list = new LinkedList<>();
        list.add(new FoodEntry("pizza", 1, nutritionInfoAPIMock.getNutritionInfo("pizza"))); // 5
        list.add(new FoodEntry("soup", 1, nutritionInfoAPIMock.getNutritionInfo("soup"))); // 10
        list.add(new FoodEntry("sandwich", 1, nutritionInfoAPIMock.getNutritionInfo("sandwich"))); //15
        list.add(new FoodEntry("pear", 1, nutritionInfoAPIMock.getNutritionInfo("pear"))); // 20
        list.add(new FoodEntry("apple", 2, nutritionInfoAPIMock.getNutritionInfo("apple"))); // 60

        assertIterableEquals(List.copyOf(list), diary.getAllFoodEntriesByProteinContent(),
            "The food entries must be in ascending order returned!");

        verify(nutritionInfoAPIMock, times(2)).getNutritionInfo("apple");
        verify(nutritionInfoAPIMock, times(2)).getNutritionInfo("sandwich");
        verify(nutritionInfoAPIMock, times(2)).getNutritionInfo("soup");
        verify(nutritionInfoAPIMock, times(2)).getNutritionInfo("pear");
        verify(nutritionInfoAPIMock, times(2)).getNutritionInfo("pizza");
    }

    @Test
    void testGetDailyCaloriesIntake() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(new NutritionInfo(50, 20, 30));
        when(nutritionInfoAPIMock.getNutritionInfo("sandwich")).thenReturn(new NutritionInfo(75, 10, 15));
        when(nutritionInfoAPIMock.getNutritionInfo("soup")).thenReturn(new NutritionInfo(60, 30, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("pear")).thenReturn(new NutritionInfo(50, 30, 20));
        when(nutritionInfoAPIMock.getNutritionInfo("pizza")).thenReturn(new NutritionInfo(85, 10, 5));

        diary.addFood(Meal.BREAKFAST, "apple", 2);
        diary.addFood(Meal.LUNCH, "sandwich", 1);
        diary.addFood(Meal.DINNER, "soup", 1);
        diary.addFood(Meal.BREAKFAST, "pear", 1);
        diary.addFood(Meal.DINNER, "pizza", 1);

        assertEquals(3000.0, diary.getDailyCaloriesIntake(), 0.00001, "The calories are not calculated correctly!");

        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("apple");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("sandwich");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("soup");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pear");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pizza");
    }

    @Test
    void testGetDailyCaloriesIntakePerMealForBreakfast() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(new NutritionInfo(50, 20, 30));
        when(nutritionInfoAPIMock.getNutritionInfo("sandwich")).thenReturn(new NutritionInfo(75, 10, 15));
        when(nutritionInfoAPIMock.getNutritionInfo("soup")).thenReturn(new NutritionInfo(60, 30, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("pear")).thenReturn(new NutritionInfo(50, 30, 20));
        when(nutritionInfoAPIMock.getNutritionInfo("pizza")).thenReturn(new NutritionInfo(85, 10, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("spaghetti")).thenReturn(new NutritionInfo(70, 20, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("croissant")).thenReturn(new NutritionInfo(65, 30, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("cake")).thenReturn(new NutritionInfo(90, 5, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("crisps")).thenReturn(new NutritionInfo(80, 15, 5));

        diary.addFood(Meal.BREAKFAST, "apple", 2);
        diary.addFood(Meal.BREAKFAST, "pear", 1);
        diary.addFood(Meal.BREAKFAST, "croissant", 1);
        diary.addFood(Meal.LUNCH, "sandwich", 1);
        diary.addFood(Meal.LUNCH, "spaghetti", 1);
        diary.addFood(Meal.LUNCH, "cake", 1);
        diary.addFood(Meal.DINNER, "soup", 1);
        diary.addFood(Meal.DINNER, "crisps", 2);
        diary.addFood(Meal.DINNER, "pizza", 1);


        assertEquals(2100, diary.getDailyCaloriesIntakePerMeal(Meal.BREAKFAST), 0.00001,
            "The sum for breakfast is not calculated correctly!");

        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("apple");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("sandwich");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("soup");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pear");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pizza");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("spaghetti");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("croissant");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("cake");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("crisps");
    }

    @Test
    void testGetDailyCaloriesIntakePerMealForLunch() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(new NutritionInfo(50, 20, 30));
        when(nutritionInfoAPIMock.getNutritionInfo("sandwich")).thenReturn(new NutritionInfo(75, 10, 15));
        when(nutritionInfoAPIMock.getNutritionInfo("soup")).thenReturn(new NutritionInfo(60, 30, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("pear")).thenReturn(new NutritionInfo(50, 30, 20));
        when(nutritionInfoAPIMock.getNutritionInfo("pizza")).thenReturn(new NutritionInfo(85, 10, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("spaghetti")).thenReturn(new NutritionInfo(70, 20, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("croissant")).thenReturn(new NutritionInfo(65, 30, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("cake")).thenReturn(new NutritionInfo(90, 5, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("crisps")).thenReturn(new NutritionInfo(80, 15, 5));

        diary.addFood(Meal.BREAKFAST, "apple", 2);
        diary.addFood(Meal.BREAKFAST, "pear", 1);
        diary.addFood(Meal.BREAKFAST, "croissant", 1);
        diary.addFood(Meal.LUNCH, "sandwich", 1);
        diary.addFood(Meal.LUNCH, "spaghetti", 1);
        diary.addFood(Meal.LUNCH, "cake", 1);
        diary.addFood(Meal.DINNER, "soup", 1);
        diary.addFood(Meal.DINNER, "crisps", 2);
        diary.addFood(Meal.DINNER, "pizza", 1);


        assertEquals(1375, diary.getDailyCaloriesIntakePerMeal(Meal.LUNCH), 0.00001,
            "The sum for breakfast is not calculated correctly!");

        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("apple");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("sandwich");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("soup");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pear");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pizza");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("spaghetti");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("croissant");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("cake");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("crisps");
    }

    @Test
    void testGetDailyCaloriesIntakePerMealForDinner() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(new NutritionInfo(50, 20, 30));
        when(nutritionInfoAPIMock.getNutritionInfo("sandwich")).thenReturn(new NutritionInfo(75, 10, 15));
        when(nutritionInfoAPIMock.getNutritionInfo("soup")).thenReturn(new NutritionInfo(60, 30, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("pear")).thenReturn(new NutritionInfo(50, 30, 20));
        when(nutritionInfoAPIMock.getNutritionInfo("pizza")).thenReturn(new NutritionInfo(85, 10, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("spaghetti")).thenReturn(new NutritionInfo(70, 20, 10));
        when(nutritionInfoAPIMock.getNutritionInfo("croissant")).thenReturn(new NutritionInfo(65, 30, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("cake")).thenReturn(new NutritionInfo(90, 5, 5));
        when(nutritionInfoAPIMock.getNutritionInfo("crisps")).thenReturn(new NutritionInfo(80, 15, 5));

        diary.addFood(Meal.BREAKFAST, "apple", 2);
        diary.addFood(Meal.BREAKFAST, "pear", 1);
        diary.addFood(Meal.BREAKFAST, "croissant", 1);
        diary.addFood(Meal.LUNCH, "sandwich", 1);
        diary.addFood(Meal.LUNCH, "spaghetti", 1);
        diary.addFood(Meal.LUNCH, "cake", 1);
        diary.addFood(Meal.DINNER, "soup", 1);
        diary.addFood(Meal.DINNER, "crisps", 2);
        diary.addFood(Meal.DINNER, "pizza", 1);


        assertEquals(1950, diary.getDailyCaloriesIntakePerMeal(Meal.DINNER), 0.00001,
            "The sum for breakfast is not calculated correctly!");

        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("apple");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("sandwich");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("soup");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pear");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("pizza");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("spaghetti");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("croissant");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("cake");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("crisps");
    }

    @Test
    void testGetDailyCaloriesIntakePerMealForNullMeal() {
        assertThrows(IllegalArgumentException.class, () -> diary.getDailyCaloriesIntakePerMeal(null));
    }

    @Test
    void testGetDailyCaloriesIntakePerMealFoodEntriesNull() throws UnknownFoodException {
        when(nutritionInfoAPIMock.getNutritionInfo("apple")).thenReturn(new NutritionInfo(50, 20, 30));
        when(nutritionInfoAPIMock.getNutritionInfo("sandwich")).thenReturn(new NutritionInfo(75, 10, 15));

        diary.addFood(Meal.BREAKFAST, "apple", 2);
        diary.addFood(Meal.LUNCH, "sandwich", 1);

        assertEquals(0.0, diary.getDailyCaloriesIntakePerMeal(Meal.DINNER), 0.1,
            "Should return 0.0 when there is no such meal!");

        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("apple");
        verify(nutritionInfoAPIMock, times(1)).getNutritionInfo("sandwich");
    }

}
