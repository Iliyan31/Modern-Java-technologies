package bg.sofia.uni.fmi.mjt.cocktail.server.storage;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.Ingredient;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultCocktailStorageTest {
    CocktailStorage cocktailStorage;
    Cocktail one;
    Cocktail two;
    Cocktail three;

    @BeforeEach
    void setUp() throws CocktailAlreadyExistsException {
        cocktailStorage = new DefaultCocktailStorage();
        one = new Cocktail("mockito", Set.of(new Ingredient("vodka", "50ml")));
        two = new Cocktail("cosmo", Set.of(new Ingredient("vodka", "30ml")));
        three = new Cocktail("bourbon", Set.of(new Ingredient("whiskey", "50ml")));

        cocktailStorage.createCocktail(one);
        cocktailStorage.createCocktail(two);
        cocktailStorage.createCocktail(three);
    }

    @Test
    void testCreateCocktail() {
        assertEquals(3, cocktailStorage.getCocktails().size(), "The system should correctly add cocktails");
    }

    @Test
    void testCreateCocktailWithAlreadyExisting() {
        assertThrows(CocktailAlreadyExistsException.class, () -> cocktailStorage.createCocktail(one),
            "The system should not allow to add an existing cocktail");
    }

    @Test
    void testCreateCocktailForNull() {
        assertThrows(IllegalArgumentException.class, () -> cocktailStorage.createCocktail(null),
            "The system should not allow to add null cocktail");
    }

    @Test
    void testGetCocktailsWithIngredientName() {
        assertEquals(2, cocktailStorage.getCocktailsWithIngredient("vOdKa").size(),
            "The system should correctly get all cocktails with such ingredients");
    }

    @Test
    void testGetCocktailsWithNullIngredientName() {
        assertThrows(IllegalArgumentException.class, () -> cocktailStorage.getCocktailsWithIngredient(null),
            "The system should not allow to add null ingredient name");
    }

    @Test
    void testGetCocktailsWithEmptyIngredientName() {
        assertThrows(IllegalArgumentException.class, () -> cocktailStorage.getCocktailsWithIngredient(""),
            "The system should not allow to add empty ingredient name");
    }

    @Test
    void testGetCocktailsWithBlankIngredientName() {
        assertThrows(IllegalArgumentException.class, () -> cocktailStorage.getCocktailsWithIngredient(" "),
            "The system should not allow to add blank ingredient name");
    }

    @Test
    void testGetCocktailWithNullCocktailName() {
        assertThrows(IllegalArgumentException.class, () -> cocktailStorage.getCocktail(" "),
            "The system should not allow to add null cocktail name");
    }

    @Test
    void testGetCocktailWithCocktailName() throws CocktailNotFoundException {
        assertEquals("mockito", cocktailStorage.getCocktail("mOcKItO").name(),
            "The system should correctly get cocktail with such name");
    }

    @Test
    void testGetCocktailWithNoSuchCocktailName() {
        assertThrows(CocktailNotFoundException.class, () -> cocktailStorage.getCocktail("Daiquiri"),
            "The system should not allow to get cocktail with no suck cocktail name");
    }
}