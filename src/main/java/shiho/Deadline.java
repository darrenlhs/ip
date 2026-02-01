package shiho;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class representing a Deadline task.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Initialises a new Deadline task with a description and by-date.
     *
     * @param description The task description.
     * @param by The date and time the task is due by.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]"
                + super.toString()
                + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"))
                + ")";
    }

    @Override
    public String toFileString() {
        return "D" + super.toFileString() + " | " + by;
    }
}
