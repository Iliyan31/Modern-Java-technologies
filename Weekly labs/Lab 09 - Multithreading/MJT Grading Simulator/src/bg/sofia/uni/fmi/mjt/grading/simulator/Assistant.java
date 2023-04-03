package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.AdminGradingAPI;

import java.util.concurrent.atomic.AtomicInteger;

public class Assistant extends Thread {

    private final String name;
    private final AdminGradingAPI grader;
    private final AtomicInteger gradedAssignments;

    public Assistant(String name, AdminGradingAPI grader) {
        this.name = name;
        this.grader = grader;
        this.gradedAssignments = new AtomicInteger();
    }

    @Override
    public void run() {
        Assignment assignment = grader.getAssignment();

        while (assignment != null) {
            try {
                Thread.sleep(assignment.type().getGradingTime());
            } catch (InterruptedException e) {
                throw new RuntimeException("There was problem with run in the current thread!!", e);
            }

            gradedAssignments.addAndGet(1);

            assignment = grader.getAssignment();
        }
    }

    public int getNumberOfGradedAssignments() {
        return gradedAssignments.get();
    }

}