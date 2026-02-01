package shiho;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class representing an Event task.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Initialises an Event task with a description, from-date, and to-date.
     *
     * @param description The task description.
     * @param from The starting date and time of the event.
     * @param to The ending date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]"
                + super.toString()
                + " (from: " + from.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"))
                + " to " + to.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"))
                + ")";
    }

    @Override
    public String toFileString() {
        return "E" + super.toFileString() + " | " + from + "-" + to;
    }
}
