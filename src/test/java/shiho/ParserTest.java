package shiho;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    public void parse_invalidInput_invalid() {
        assertEquals("invalid", new Parser().parse("blah blah blah"));
    }

    @Test
    public void test_parseFileLine() {
        Parser parser = new Parser();
        Task targetTask = parser.parseFileLine("D | 0 | buy milk at the store | 2026-01-27 2200");
        assertEquals("buy milk at the store", targetTask.description);
    }
}
