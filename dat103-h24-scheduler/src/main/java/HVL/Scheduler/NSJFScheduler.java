package HVL.Scheduler;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class NSJFScheduler implements Scheduler {

    private Queue<Task> ready;  // Queue to store tasks
    private Task selected;      // Currently running task

    NSJFScheduler() {
        this.ready = new ArrayDeque<>();
    }

    @Override
    public Optional<Integer> scheduled() {
        if (selected == null) return Optional.empty();
        return Optional.of(selected.getId());
    }

    @Override
    public List<Integer> ready() {
        return ready.stream().map(Task::getId).toList();
    }

    @Override
    public void addTask(Task task) {
        if (ready.isEmpty()) {
            ready.add(task); // If the queue is empty, simply add the task
        } else {
            // Insert the task in a position such that the queue remains sorted by task size (burst time)
            Queue<Task> tempQueue = new ArrayDeque<>();
            boolean added = false;

            while (!ready.isEmpty()) {
                Task currentTask = ready.poll();
                // Use getSize() to compare task size (burst time)
                if (!added && task.getSize() < currentTask.getSize()) {
                    tempQueue.add(task);
                    added = true;
                }
                tempQueue.add(currentTask);
            }

            // If the task wasn't added, it's the longest burst time, add it at the end
            if (!added) {
                tempQueue.add(task);
            }

            ready = tempQueue; // Update the ready queue
        }
    }

    @Override
    public void schedule() {
        if (selected == null) {
            // Pick the next task (shortest job first, which is at the front of the ready queue)
            selected = ready.poll();
            if (selected == null) {
                return; // No task available to schedule
            }
            selected.start();
        } else {
            // If the selected task is done, stop it and pick the next task
            if (selected.isDone()) {
                selected.stop();
                selected = null;
                schedule();  // Try to schedule the next task
            }
            // No preemption, so no further logic needed for this scheduler
        }
    }
}
