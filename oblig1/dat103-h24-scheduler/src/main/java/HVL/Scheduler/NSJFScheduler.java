package HVL.Scheduler;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class NSJFScheduler implements Scheduler {

    private Queue<Task> ready;
    private Task selected;

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
            ready.add(task);
        } else {
            Queue<Task> tempQueue = new ArrayDeque<>();
            boolean added = false;

            while (!ready.isEmpty()) {
                Task currentTask = ready.poll();
                if (!added && task.getSize() < currentTask.getSize()) {
                    tempQueue.add(task);
                    added = true;
                }
                tempQueue.add(currentTask);
            }
            if (!added) {
                tempQueue.add(task);
            }

            ready = tempQueue;
        }
    }

    @Override
    public void schedule() {
        if (selected == null) {
            selected = ready.poll();
            if (selected == null) {
                return;
            }
            selected.start();
        } else {
            if (selected.isDone()) {
                selected.stop();
                selected = null;
                schedule();
            }
        }
    }
}
