package shiho;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoteListTest {
    // all methods below have been added with the help of ChatGPT as part of A-AiAssisted

    @Test
    public void testSize2Notes() {
        ArrayList<Note> notes = new ArrayList<>();
        NoteList notelist = new NoteList(notes);
        notelist.add(new Note("Hi", "How are you?"));
        notelist.add(new Note("First day of school", "It was fun!"));
        assertEquals(2, notelist.size());
    }

    @Test
    public void testGet() {
        ArrayList<Note> notes = new ArrayList<>();
        NoteList notelist = new NoteList(notes);
        notelist.add(new Note("Hi", "How are you?"));
        notelist.add(new Note("First day of school", "It was fun!"));
        notelist.add(new Note("What's 2 + 2?", "The answer is 4!"));

        assertEquals("It was fun!", notelist.get(1).getDescription());
    }

    @Test
    public void testIsEmpty() {
        ArrayList<Note> notes = new ArrayList<>();
        NoteList notelist = new NoteList(notes);
        assertTrue(notelist.isEmpty());
    }

    @Test
    public void testRemove() {
        ArrayList<Note> notes = new ArrayList<>();
        NoteList notelist = new NoteList(notes);
        notelist.add(new Note("Hi", "How are you?"));
        notelist.add(new Note("First day of school", "It was fun!"));
        notelist.remove(0);
        assertEquals(1, notelist.size());
    }
}
