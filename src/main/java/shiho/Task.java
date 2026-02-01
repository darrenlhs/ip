package shiho;

/**
 * A class representing a task.
 */
public class Task {

    protected String description;
    protected boolean isDone;

    /**
     * Initialises a Task object with a description.
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a string representing the status icon ("X" or blank) of a task.
     *
     * @return Status icon representing whether the task is done or not.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Toggles the isDone boolean of the task to true.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Toggles the isDone boolean of the task to false.
     */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Returns the string representation of the task, including its status icon and description.
     *
     * @return String representation of task.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns the string representation of the task to be stored in a task list file, including '|' separators.
     *
     * @return String representation of task to be stored in a file.
     */
    public String toFileString() {
        return " | " + (isDone ? 1 : 0) + " | " + this.description;
    }
}

