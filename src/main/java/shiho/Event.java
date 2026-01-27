package shiho;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

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
