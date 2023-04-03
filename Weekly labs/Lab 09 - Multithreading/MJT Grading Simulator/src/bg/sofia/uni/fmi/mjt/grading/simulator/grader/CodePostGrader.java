package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import bg.sofia.uni.fmi.mjt.grading.simulator.Assistant;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class CodePostGrader implements AdminGradingAPI {
    private final List<Assistant> assistants;
    private final Queue<Assignment> assignmentQueue;
    private boolean isFinalizeGradingCalled;
    private final AtomicInteger counter;

    public CodePostGrader(int numberOfAssistants) {
        this.assistants = new LinkedList<>();
        this.assignmentQueue = new LinkedList<>();
        this.counter = new AtomicInteger();

        for (int i = 0; i < numberOfAssistants; i++) {
            Assistant assistant = new Assistant("Assistant" + i, this);
            assistants.add(assistant);
        }

        for (Assistant assistant : assistants) {
            assistant.start();
        }
    }

    @Override
    public synchronized Assignment getAssignment() {
        while (assignmentQueue.isEmpty() && !isFinalizeGradingCalled) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return assignmentQueue.poll();
    }

    @Override
    public int getSubmittedAssignmentsCount() {
        return counter.get();
    }


    @Override
    public void finalizeGrading() {
        isFinalizeGradingCalled = true;

        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public List<Assistant> getAssistants() {
        return assistants;
    }

    @Override
    public synchronized void submitAssignment(Assignment assignment) {
        if (!isFinalizeGradingCalled) {
            assignmentQueue.add(assignment);
            counter.addAndGet(1);
            notify();
        }
    }
}
