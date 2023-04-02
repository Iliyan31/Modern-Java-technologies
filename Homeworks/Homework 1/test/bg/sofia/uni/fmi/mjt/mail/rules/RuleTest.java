package bg.sofia.uni.fmi.mjt.mail.rules;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RuleTest {

    @Test
    void testSubjectIncludesForNull() {
        assertThrows(IllegalArgumentException.class,
            () -> Rule.of(null, new HashSet<>(), new HashSet<>(), "a", RulePriority.ONE, "a"),
            "Throws IllegalArgumentException when subjectIncludes is null!");
    }

    @Test
    void testSubjectOrBodyIncludesForNull() {
        assertThrows(IllegalArgumentException.class,
            () -> Rule.of(new HashSet<>(), null, new HashSet<>(), "a", RulePriority.ONE, "a"),
            "Throws IllegalArgumentException when subjectOrBodyIncludes is null!");
    }

    @Test
    void testRecipientIncludesForNull() {
        assertThrows(IllegalArgumentException.class,
            () -> Rule.of(new HashSet<>(), new HashSet<>(), null, "a", RulePriority.ONE, "a"),
            "Throws IllegalArgumentException when recipientIncludes is null!");
    }

    @Test
    void testFromForNull() {
        assertThrows(IllegalArgumentException.class,
            () -> Rule.of(new HashSet<>(), new HashSet<>(), new HashSet<>(), null, RulePriority.ONE, "a"),
            "Throws IllegalArgumentException when from is null!");
    }

    @Test
    void testPriorityForNull() {
        assertThrows(IllegalArgumentException.class,
            () -> Rule.of(new HashSet<>(), new HashSet<>(), new HashSet<>(), "a", null, "a"),
            "Throws IllegalArgumentException when priority is null!");
    }

    @Test
    void testFolderPathForNull() {
        assertThrows(IllegalArgumentException.class,
            () -> Rule.of(new HashSet<>(), new HashSet<>(), new HashSet<>(), "a", RulePriority.ONE, null),
            "Throws IllegalArgumentException when priority is null!");
    }

    @Test
    void testFolderPathForEmpty() {
        assertThrows(IllegalArgumentException.class,
            () -> Rule.of(new HashSet<>(), new HashSet<>(), new HashSet<>(), "a", RulePriority.ONE, ""),
            "Throws IllegalArgumentException when priority is null!");
    }

    @Test
    void testFolderPathForBlank() {
        assertThrows(IllegalArgumentException.class,
            () -> Rule.of(new HashSet<>(), new HashSet<>(), new HashSet<>(), "a", RulePriority.ONE, "           "),
            "Throws IllegalArgumentException when priority is null!");
    }

    @Test
    void testEqualsRedefinitionEqual() {
        assertEquals(Rule.of(Set.of("a"), Set.of("b"), Set.of("c", "d"), "a", RulePriority.ONE, "/inbox"),
            Rule.of(Set.of("a"), Set.of("b"), Set.of("c", "d"), "a", RulePriority.ONE, "/inbox"),
            "Equals should correctly return equality!");
    }

    @Test
    void testEqualsRedefinitionDoesntEqual() {
        assertNotEquals(Rule.of(Set.of("a"), Set.of("b"), Set.of("c", "d"), "a", RulePriority.ONE, "/inbox"),
            Rule.of(Set.of("a"), Set.of("b"), Set.of("f", "d"), "a", RulePriority.ONE, "/inbox"),
            "Equals should correctly return equality!");
    }
}
