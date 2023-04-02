package bg.sofia.uni.fmi.mjt.cocktail.server.command;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CommandCreatorTest {

    /**
     * [0] -> get all
     * [1] -> get by-name <cocktail_name>
     * [2] -> get by-ingredient <ingredient_name>
     */
    static List<Command> getCommands;
    static Command create;
    static Command create2;
    static Command disconnect;

    @BeforeAll
    static void setUp() {
        create = new Command("create", "manhattan", "whisky=100ml");
        create2 = new Command("create", "manhattan", "whisky=100ml", "liquor=20ml");

        getCommands = List.of(new Command("get", "all"),
            new Command("get", "by-name", "mOcKItO"),
            new Command("get", "by-ingredient", "vOdKa"));

        disconnect = new Command("disconnect");
    }

    @Test
    void testNewCommandCreationName() {
        assertEquals(create.command(), CommandCreator.newCommand("create manhattan whisky=100ml").command(),
            "The creator should correctly create commands!");
    }

    @Test
    void testNewCommandCreateWithMoreIngredients() {
        assertEquals(create2.command(), CommandCreator.newCommand("create manhattan whisky=100ml liquor=20ml").command(),
            "The creator should correctly create commands!");
    }

    @Test
    void testNewCommandCreationArgsLength() {
        assertEquals(create.arguments().length, CommandCreator.newCommand("create manhattan whisky=100ml").arguments().length,
            "The creator should correctly create commands!");
    }

    @Test
    void testNewCommandGetAll() {
        assertEquals(getCommands.get(0).command(), CommandCreator.newCommand("get all").command(),
            "The creator should correctly create commands!");
    }

    @Test
    void testNewCommandGetByName() {
        assertEquals(getCommands.get(1).command(), CommandCreator.newCommand("get by-name mOcKItO").command(),
            "The creator should correctly create commands!");
    }

    @Test
    void testNewCommandGetByIngredient() {
        assertEquals(getCommands.get(2).command(), CommandCreator.newCommand("get by-ingredient vOdKa").command(),
            "The creator should correctly create commands!");
    }

    @Test
    void testNewCommandDisconnect() {
        assertEquals(disconnect.command(), CommandCreator.newCommand("disconnect").command(),
            "The creator should correctly create commands!");
    }

    @Test
    void testNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> CommandCreator.newCommand(null),
            "The command cannot be null, empty or blank!");
    }

    @Test
    void testEmptyCommand() {
        assertThrows(IllegalArgumentException.class, () -> CommandCreator.newCommand(""),
            "The command cannot be null, empty or blank!");
    }

    @Test
    void testBlankCommand() {
        assertThrows(IllegalArgumentException.class, () -> CommandCreator.newCommand(" "),
            "The command cannot be null, empty or blank!");
    }

//    @Test
//    void testNewCommandCreationArgsLength() {
//        assertEquals(create.arguments().length, CommandCreator.newCommand("create manhattan whisky=100ml").arguments().length,
//            "The creator should correctly create commands!");
//    }
}
