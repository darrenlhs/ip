package shiho;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parse_invalidInput_Invalid() {
        assertEquals("invalid", new Parser().parse("blah blah blah"));
    }

    @Test
    public void test_parseFileLine_deadline() {
        Task targetTask = Parser.parseFileLineTask("D | 0 | buy milk at the store | 2026-01-27 2200");
        assertEquals("buy milk at the store", targetTask.getDescription());
    }

    // all methods below were written with the help of ChatGPT as part of the AiAssisted increment

    @Test
    public void test_byeInput_bye() {
        assertEquals("bye", new Parser().parse("bye"));
    }

    @Test
    public void test_byeInputWithSpace_invalid() {
        assertEquals("invalid", new Parser().parse("bye "));
    }

    @Test
    public void test_listInput_list() {
        assertEquals("list", new Parser().parse("list"));
    }

    @Test
    public void test_markInput_mark() {
        assertEquals("mark", new Parser().parse("mark 1"));
    }

    @Test
    public void test_unmarkInput_unmark() {
        assertEquals("unmark", new Parser().parse("unmark 1"));
    }

    @Test
    public void test_deleteInput_delete() {
        assertEquals("delete", new Parser().parse("delete 1"));
    }

    @Test
    public void test_findInput_find() {
        assertEquals("find", new Parser().parse("find project"));
    }

    @Test
    public void test_todoInput_todo() {
        assertEquals("todo", new Parser().parse("todo brush teeth"));
    }

    @Test
    public void test_deadlineInput_deadline() {
        assertEquals("deadline", new Parser().parse("deadline math homework /by 2026-02-17 2000"));
    }

    @Test
    public void test_eventInput_event() {
        assertEquals("event", new Parser().parse("event family dinner /from 2026-02-17 1830 /to 2026-02-17 1930"));
    }

    @Test
    public void test_notenewInput_notenew() {
        assertEquals("notenew", new Parser().parse("notenew /title First day at school /desc It was fun!"));
    }

    @Test
    public void test_notelistInput_notelist() {
        assertEquals("notelist", new Parser().parse("notelist"));
    }

    @Test
    public void test_notelistInputWithSpace_invalid() {
        assertEquals("invalid", new Parser().parse("notelist "));
    }

    @Test
    public void test_notedeleteInput_notedelete() {
        assertEquals("notedelete", new Parser().parse("notedelete 1"));
    }

    @Test
    public void test_parseFileLineDescription_todo() {
        Task targetTask = Parser.parseFileLineTask("T | 0 | math homework");
        assertEquals("math homework", targetTask.getDescription());
    }

    @Test
    public void test_parseFileLineIsDone_todo() {
        Task targetTask = Parser.parseFileLineTask("T | 0 | math homework");
        assertEquals(false, targetTask.getIsDone());
    }

    @Test
    public void test_parseFileLineDescription_event() {
        Task targetTask = Parser.parseFileLineTask("E | 1 | family dinner | 2026-02-17 1830 to 2026-02-17 1930");
        assertEquals("family dinner", targetTask.getDescription());
    }

    @Test
    public void test_parseFileLineIsDone_event() {
        Task targetTask = Parser.parseFileLineTask("E | 1 | family dinner | 2026-02-17 1830 to 2026-02-17 1930");
        assertEquals(true, targetTask.getIsDone());
    }

    @Test
    public void test_parseFileLineNote_title() {
        Note targetNote = Parser.parseFileLineNote("First day of school: It was fun!");
        assertEquals("First day of school", targetNote.getTitle());
    }

    @Test
    public void test_parseFileLineNote_description() {
        Note targetNote = Parser.parseFileLineNote("First day of school: It was fun!");
        assertEquals("It was fun!", targetNote.getDescription());
    }
}
