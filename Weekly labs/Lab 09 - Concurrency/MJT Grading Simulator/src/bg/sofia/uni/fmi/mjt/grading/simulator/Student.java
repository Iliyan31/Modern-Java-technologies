package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.AssignmentType;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.StudentGradingAPI;

public class Student implements Runnable {
    private final String name;
    private final StudentGradingAPI studentGradingAPI;
    private final int fn;
    private final int maximumTimeToSolveTask = 1_000;
    private final int minimumTimeToSolveTask = 0;

    public Student(int fn, String name, StudentGradingAPI studentGradingAPI) {
        this.fn = fn;
        this.name = name;
        this.studentGradingAPI = studentGradingAPI;
    }

    @Override
    public void run() {
        Assignment assignment = new Assignment(fn, name, AssignmentType.getRandomAssignmentType());

        try {
            Thread.sleep(
                (int) ((Math.random() * (maximumTimeToSolveTask - minimumTimeToSolveTask)) + minimumTimeToSolveTask));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        studentGradingAPI.submitAssignment(assignment);
    }

    public int getFn() {
        return fn;
    }

    public String getName() {
        return name;
    }

    public StudentGradingAPI getGrader() {
        return studentGradingAPI;
    }

}