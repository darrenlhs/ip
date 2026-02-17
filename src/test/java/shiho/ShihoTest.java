package shiho;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShihoTest {
    // all methods below have been added with the help of ChatGPT as part of A-AiAssisted

    @Test
    public void testFullRun() {
        Shiho shiho = new Shiho("data/tasks.txt", "data/notes.txt");

        TaskList tasks = shiho.getTaskList();
        NoteList notes = shiho.getNoteList();

        String firstResponse = shiho.getResponse("todo read book");
        // by now, only 1 task (read book) in tasks
        assertTrue(firstResponse.contains("Now you have 1 task in the list"));

        String secondResponse = shiho.getResponse("list");
        // should successfully list the read book task
        assertTrue(secondResponse.contains("read book"));

        shiho.getResponse("mark 0");
        // should successfully mark "read book" as done
        assertEquals(true, tasks.get(0).getIsDone());

        shiho.getResponse("unmark 0");
        // should successfully mark "read book" as undone
        assertEquals(false, tasks.get(0).getIsDone());

        shiho.getResponse("todo math homework");
        shiho.getResponse("delete 0");
        // adds "math homework" and deletes "read book". only 1 task remains
        assertEquals(1, tasks.size());

        shiho.getResponse("todo find milk");
        shiho.getResponse("todo science homework");
        String thirdResponse = shiho.getResponse("find project");
        // no task containing "project", find should fail
        assertTrue(thirdResponse.contains("Sorry, no matching tasks were found."));

        shiho.getResponse("notenew /title First day of school /desc It was fun!");
        shiho.getResponse("notenew /title Maths is boring /desc It's too difficult!");
        shiho.getResponse("notedelete 0");
        String fourthResponse = shiho.getResponse("notelist");

        // by the end of this sequence, only the "Maths is boring" note should remain in the note list
        assertTrue(fourthResponse.contains("Maths is boring"));
    }
}
