package shiho;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StorageTest {
    @Test
    public void testSaveAndLoadTasks() throws IOException {
        File taskFile = new File("data/testtasks.txt");
        // delete task file at start of test to ensure results are consistent
        taskFile.delete();
        Shiho shiho = new Shiho("data/testtasks.txt", "data/testnotes.txt");
        TaskList tasks = shiho.getTaskList();
        Storage taskStorage = shiho.getTaskStorage();
        tasks.add(new ToDo("math homework"));
        tasks.add(new ToDo("english homework"));
        tasks.add(new ToDo("science homework"));

        taskStorage.saveTasks(tasks.getAll());
        ArrayList<Task> loadedTasks = taskStorage.loadTasks();
        assertEquals(3, loadedTasks.size());
        assertEquals("math homework", loadedTasks.get(0).getDescription());
    }

    @Test
    public void testSaveAndLoadNotes() throws IOException {
        File noteFile = new File("data/testnotes.txt");
        // delete note at start of test to ensure results are consistent
        noteFile.delete();
        Shiho shiho = new Shiho("data/testtasks.txt", "data/testnotes.txt");
        NoteList notes = shiho.getNoteList();
        Storage noteStorage = shiho.getNoteStorage();
        notes.add(new Note("First day of school", "It was fun!"));
        notes.add(new Note("Second day of school", "It was nice!"));
        notes.add(new Note("Second day of school", "It was great!"));

        noteStorage.saveNotes(notes.getAll());
        ArrayList<Note> loadedNotes = noteStorage.loadNotes();
        assertEquals(3, loadedNotes.size());
        assertEquals("It was great!", loadedNotes.get(2).getDescription());
    }
}
