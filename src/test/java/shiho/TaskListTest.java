package shiho;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    // all methods below have either been cleaned up or added with the help of ChatGPT as part of A-AiAssisted

    @Test
    public void testSize2Tasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList tasklist = new TaskList(tasks);
        tasklist.add(new ToDo("math homework"));
        tasklist.add(
                new Deadline("finish science project",
                        LocalDateTime.parse("2026-01-27 2000",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")))
        );

        assertEquals(2, tasklist.size());
    }

    @Test
    public void testGet() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList tasklist = new TaskList(tasks);
        tasklist.add(new ToDo("math homework"));
        tasklist.add(
                new Deadline("finish science project",
                        LocalDateTime.parse("2026-01-27 2000",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")))
        );
        tasklist.add(
                new Event("family dinner",
                        LocalDateTime.parse("2026-01-27 1800",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")),
                        LocalDateTime.parse("2026-01-27 1900",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")))
        );

        assertEquals("finish science project", tasklist.get(1).getDescription());
    }

    @Test
    public void testIsEmpty() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList tasklist = new TaskList(tasks);
        assertTrue(tasklist.isEmpty());
    }

    @Test
    public void testRemove() {
        ArrayList<Task> tasks = new ArrayList<>();
        TaskList tasklist = new TaskList(tasks);
        tasklist.add(new ToDo("math homework"));
        tasklist.add(
                new Deadline("finish science project",
                        LocalDateTime.parse("2026-01-27 2000",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")))
        );
        tasklist.remove(0);
        assertEquals(1, tasklist.size());
    }
}
