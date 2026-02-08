package shiho;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parse_invalidInput_invalid() {
        assertEquals("invalid", new Parser().parse("blah blah blah"));
    }

    @Test
    public void test_parseFileLine() {
        Parser parser = new Parser();
        Task targetTask = parser.parseFileLineTask("D | 0 | buy milk at the store | 2026-01-27 2200");
        assertEquals("buy milk at the store", targetTask.getDescription());
    }
}
