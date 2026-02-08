package shiho;

/**
 * A class representing a note.
 */
public class Note {

    private String description;
    private String title;

    /**
     * Initialises a Note object with a description.
     *
     * @param description The note description.
     * @param title The note title.
     */
    public Note(String title, String description) {
        this.description = description;
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Returns the string representation of the note, including its title and description.
     *
     * @return String representation of note.
     */
    @Override
    public String toString() {
        return this.title + ": " + this.description;
    }
}

