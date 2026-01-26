package shiho;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    private TaskList tasks;
    private Ui ui;

    public Parser(TaskList tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }


    public String parse(String userInput) {
        // returns a specific String depending on userInput and handles it in Shiho
        String[] parts = userInput.split(" ");

        if (userInput.equals("bye")) {
            // bye command ends the current instance
            return "bye";

        } else if (userInput.equals("list")) {
            // list command displays current list of tasks
            return "list";

        } else if (parts[0].equals("mark")) {
            // mark command marks a specific task as done
            return "mark";

        } else if (parts[0].equals("unmark")) {
            // unmark command marks a specific task as undone
            return "unmark";

        } else if (parts[0].equals("delete")) {
            // delete command removes a specific task from the list
            return "delete";

        } else {
            // todo, deadline and event commands indicate specific types of tasks to be added to the list

            boolean isValid = true;

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


    public static Task parseFileLine(String line) {
        // parses a line from a text file and returns a Task representing the line
        // line format is 'X | y | description | deadline/duration (if applicable)'
        // X = T, D or E depending on task type
        // y = 0 or 1 depending on if task is marked undone or done respectively
        String[] parts = line.split(" \\| ");

        // parts[0] = X
        // parts[1] = y
        // parts[2] = description
        // parts[3] = deadline/duration
        boolean isDone = parts[1].equals("1");
        if (parts[0].equals("T")) {
            // ToDo task
            return new ToDo(parts[2]);

        } else if (parts[0].equals("D")) {
            // Deadline task
            LocalDateTime deadlineDate = LocalDateTime.parse(parts[3],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task task = new Deadline(parts[2], deadlineDate);
            task.isDone = isDone;
            return task;

        } else {
            // Event task
            String[] fromTo = parts[3].split(" to ");
            LocalDateTime eventFrom = LocalDateTime.parse(fromTo[0],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            LocalDateTime eventTo = LocalDateTime.parse(fromTo[1],
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            Task task = new Event(parts[2], eventFrom, eventTo);
            task.isDone = isDone;
            return task;
        }

    }
}