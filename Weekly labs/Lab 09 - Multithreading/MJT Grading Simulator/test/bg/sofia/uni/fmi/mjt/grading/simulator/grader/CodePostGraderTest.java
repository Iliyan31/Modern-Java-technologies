package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import bg.sofia.uni.fmi.mjt.grading.simulator.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CodePostGraderTest {
    static CodePostGrader codePostGrader;
    static Thread s1;
    static Thread s2;

    @BeforeAll
    static void createStudents() {
        final int assistantsNumber = 1;
        codePostGrader = new CodePostGrader(assistantsNumber);

        final int one = 1;
        final int two = 2;

        s1 = new Thread(new Student(one, "Iliyan1", codePostGrader));
        s2 = new Thread(new Student(two, "Iliyan2", codePostGrader));

        s1.run();
        s2.run();
    }

    @Test
    void testSubmittedAssignments() {
        assertEquals(2, codePostGrader.getSubmittedAssignmentsCount(),
            "There must be correctly created the wanted number of submittions!");
    }

    @Test
    void testFinalizeGrading() {
        codePostGrader.finalizeGrading();
        assertNull(codePostGrader.getAssignment(), "After finalize there must not be any submittions left!");
    }

    @Test
    void getNumberOfGradedAssignments() {
        assertEquals(2, codePostGrader.getAssistants().get(0).getNumberOfGradedAssignments(),
            "There must me correct number of graded assignments!");
    }

    @AfterAll
    static void joinStudents() throws InterruptedException {
        s1.join();
        s2.join();
    }
}
