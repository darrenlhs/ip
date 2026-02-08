package shiho;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void testSize2Tasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("math homework"));
        tasks.add(
                new Deadline("finish science project",
                        LocalDateTime.parse("2026-01-27 2000",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")))
        );

        assertEquals(2, tasks.size());
    }

    @Test
    public void testGet() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("math homework"));
        tasks.add(
                new Deadline("finish science project",
                        LocalDateTime.parse("2026-01-27 2000",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")))
        );
        tasks.add(
                new Event("family dinner",
                        LocalDateTime.parse("2026-01-27 1800",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")),
                        LocalDateTime.parse("2026-01-27 1900",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")))
        );

        assertEquals("finish science project", tasks.get(1).getDescription());
    }
}
