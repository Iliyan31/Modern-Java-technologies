package bg.sofia.uni.fmi.mjt.cocktail.server.command;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.Ingredient;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.CocktailStorage;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.DefaultCocktailStorage;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommandExecutorTest {
    @Mock
    CocktailStorage cocktailStorage;
    CommandExecutor commandExecutor;

    @BeforeEach
    void setUp() {
        cocktailStorage = new DefaultCocktailStorage();
        commandExecutor = new CommandExecutor(cocktailStorage);
    }

//    @Test
//    void testExecuteCreate() throws CocktailAlreadyExistsException {
//        doNothing().when(cocktailStorage).createCocktail(
//            new Cocktail("manhattan", Set.of(new Ingredient("whisky", "100ml"))));
//
//        commandExecutor.execute(new Command("create", "manhattan", "whisky=100ml"));
//    }
}