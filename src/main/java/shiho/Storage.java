package shiho;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * A class representing the file storage.
 */
public class Storage {

    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks from the storage file. Creates the file if it does not exist.
     *
     * @return An ArrayList of tasks stored in the given file path.
     * @throws IOException If an error occurs while creating or loading the file.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        // loads the existing task list from the file path, and creates a new one if it doesn't exist

        if (!Files.exists(filePath)) {
            // if the directory and file does not exist yet, creates it
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }

        ArrayList<Task> tasks = new ArrayList<>();

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(filePath));

        for (String line: lines) {
            Task t = Parser.parseFileLineTask(line);
            tasks.add(t);
        }
        return tasks;
    }

    /**
     * Loads notes from the storage file. Creates the file if it does not exist.
     *
     * @return An ArrayList of notes stored in the given file path.
     * @throws IOException If an error occurs while creating or loading the file.
     */
    public ArrayList<Note> loadNotes() throws IOException {
        // loads the existing note list from the file path, and creates a new one if it doesn't exist

        if (!Files.exists(filePath)) {
            // if the directory and file does not exist yet, creates it
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }

        ArrayList<Note> notes = new ArrayList<>();

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(filePath));

        for (String line: lines) {
            Note note = Parser.parseFileLineNote(line);
            notes.add(note);
        }
        return notes;
    }

    /**
     * Saves tasks stored in the storage file.
     *
     * @throws IOException If an error occurs while saving the file.
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task t : tasks) {
                writer.write(t.toFileString());
                writer.newLine();
            }
        }
    }

    /**
     * Saves notes stored in the storage file.
     *
     * @throws IOException If an error occurs while saving the file.
     */
    public void saveNotes(ArrayList<Note> notes) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Note note : notes) {
                writer.write(note.toString());
                writer.newLine();
            }
        }
    }
}
