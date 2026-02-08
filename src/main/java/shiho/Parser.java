package shiho;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class that processes the user input.
 */
public class Parser {

    public Parser() {}

    /**
     * Returns a string (to be encoded by Shiho.getResponse()) depending on the user input
     *
     * @param userInput The input entered into the chatbot by the user
     * @return String representing the type of user input
     */
    public String parse(String userInput) {
        // returns a specific String depending on userInput and handles it in Shiho
        String[] userInputParts = userInput.split(" ");

        if (userInput.equals("bye")) {
            // bye command ends the current instance
            return "bye";

        } else if (userInput.equals("list")) {
            // list command displays current list of tasks
            return "list";

        } else if (userInputParts[0].equals("mark")) {
            // mark command marks a specific task as done
            return "mark";

        } else if (userInputParts[0].equals("unmark")) {
            // unmark command marks a specific task as undone
            return "unmark";

        } else if (userInputParts[0].equals("delete")) {
            // delete command removes a specific task from the list
            return "delete";
        } else if (userInputParts[0].equals("find")) {
            // find command finds and returns all tasks containing the specified phrase
            return "find";
        } else {
            // todo, deadline and event commands indicate specific types of tasks to be added to the list
            if (userInput.startsWith("todo")) {
                return "todo";
            } else if (userInput.startsWith("deadline")) {
                return "deadline";
            } else if (userInput.startsWith("event")) {
                return "event";
            } else {
                return "invalid";
            }
        }
    }

    /**
     * Returns a Task object that the file line represents.
     *
     * @param line The current line in the text file being parsed.
     * @return Task object represented by the line.
     */
    public static Task parseFileLine(String line) {
        String[] fileLineParts = line.split(" \\| ");

        // fileLineParts[0] = X
        // fileLineParts[1] = y
        // fileLineParts[2] = description
        // fileLineParts[3] = deadline/duration
        boolean isDone = fileLineParts[1].equals("1");
        if (fileLineParts[0].equals("T")) {
            // ToDo task
            return new ToDo(fileLineParts[2]);

        } else if (fileLineParts[0].equals("D")) {
            // Deadline task
            LocalDateTime deadlineDate =
                    LocalDateTime.parse(fileLineParts[3],
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task task = new Deadline(fileLineParts[2], deadlineDate);
            task.setIsDone(isDone);
            return task;
        } else {
            // Event task
            String[] fromTo = fileLineParts[3].split(" to ");
            LocalDateTime eventFrom =
                    LocalDateTime.parse(fromTo[0],
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            LocalDateTime eventTo =
                    LocalDateTime.parse(fromTo[1],
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task task = new Event(fileLineParts[2], eventFrom, eventTo);
            task.setIsDone(isDone);
            return task;
        }
    }
}
