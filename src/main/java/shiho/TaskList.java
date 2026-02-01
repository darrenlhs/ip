package shiho;

import java.util.ArrayList;

/**
 * A class representing the task list.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds the specified task to the task list.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Returns the task at the specified index in the task list.
     *
     * @param index Index of the task to retrieve.
     * @return The task at the corresponding index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Removes the task at the specified index in the task list.
     *
     * @param index Index of the task to remove.
     * @return The task that was removed.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns a boolean representing whether the task list is empty.
     *
     * @return Boolean indicating whether task list is empty.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns the size of the current task list.
     *
     * @return The size of the current task list, as an integer.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the current task list as an ArrayList.
     *
     * @return The current task list, represented as an ArrayList.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }
}
