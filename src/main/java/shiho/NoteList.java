package shiho;

import java.util.ArrayList;

/**
 * A class representing the note list.
 */
public class NoteList {

    private ArrayList<Note> notes;

    public NoteList() {
        notes = new ArrayList<>();
    }

    public NoteList(ArrayList<Note> notes) {
        this.notes = notes;
    }

    /**
     * Adds the specified note to the note list.
     */
    public void add(Note note) {
        notes.add(note);
    }

    /**
     * Returns the note at the specified index in the note list.
     *
     * @param index Index of the note to retrieve.
     * @return The note at the corresponding index.
     */
    public Note get(int index) {
        return notes.get(index);
    }

    /**
     * Removes the note at the specified index in the note list.
     *
     * @param index Index of the note to remove.
     * @return The note that was removed.
     */
    public Note remove(int index) {
        return notes.remove(index);
    }

    /**
     * Returns a boolean representing whether the note list is empty.
     *
     * @return Boolean indicating whether note list is empty.
     */
    public boolean isEmpty() {
        return notes.isEmpty();
    }

    /**
     * Returns the size of the current note list.
     *
     * @return The size of the current note list, as an integer.
     */
    public int size() {
        return notes.size();
    }

    /**
     * Returns the current note list as an ArrayList.
     *
     * @return The current note list, represented as an ArrayList.
     */
    public ArrayList<Note> getAll() {
        return notes;
    }
}
