package shiho;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * A class representing the Shiho chatbot.
 */
public class Shiho {

    private Storage storage;
    private TaskList tasks;
    private Parser parser;
    private String startupMessage;

    /**
     * Initialises a Shiho object with the corresponding file path.
     * @param filePath The file path containing the task list file.
     *
     */
    public Shiho(String filePath) {
        storage = new Storage(filePath);
        parser = new Parser();
        startupMessage = "";

        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            startupMessage = "No existing task list found in storage. Creating empty task list.";
            tasks = new TaskList();
        }
    }

    /**
     * Runs the Shiho chatbot and handles inputs depending on return value of the Parser.
     *
     * @return String to be sent to the MainWindow class to be printed out
     */
    @SuppressWarnings("checkstyle:WhitespaceAround")
    public String getResponse(String input) {
        String[] userInputParts = input.split(" ");
        String response = "";

        String parserPhrase = parser.parse(input);
        assert parserPhrase != null : "Parser output should not be null";

        // initially assume input is valid (isValid only applies for todo/deadline/event commands)
        // hence, isValid is set to false for all commands except todo/deadline/event ones
        boolean isValid = true;

        switch (parserPhrase) {
        case "bye":
            response = "Bye. Come back soon.";
            isValid = false;
            break;
        case "list":
            if (tasks.isEmpty()) {
                response = "The task list is empty.\n";
            } else {
                response = "Here are your tasks:\n";
                for (int i = 0; i < tasks.size(); i++) {
                    int number = i + 1;
                    response += number
                            + "."
                            + tasks.get(i).toString()
                            + "\n";

                    if (i == tasks.size() - 1) {
                        response += "\n"; // adds a newline if it's the last element of the list
                    }
                }
            }
            isValid = false;
            break;

        case "mark":
            try {
                int taskNumber = Integer.parseInt(userInputParts[1]);
                tasks.get(taskNumber - 1).markAsDone();
                response = "Okay. I've marked this task as done:\n"
                        + "   ["
                        + tasks.get(taskNumber - 1).getStatusIcon()
                        + "] "
                        + tasks.get(taskNumber - 1).getDescription()
                        + "\n";
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                if (tasks.isEmpty()) {
                    response = "The task list is empty.\n";
                } else {
                    response = "Invalid task index\n";
                }
            } catch (NumberFormatException e) {
                response = "Non-integer task index provided\n";
            }
            isValid = false;
            break;

        case "unmark":
            try {
                int taskNumber = Integer.parseInt(userInputParts[1]);
                tasks.get(taskNumber - 1).markAsUndone();
                response = "Okay. I've marked this task as not done yet:\n"
                        + "   ["
                        + tasks.get(taskNumber - 1).getStatusIcon()
                        + "] "
                        + tasks.get(taskNumber - 1).getDescription()
                        + "\n";
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                if (tasks.isEmpty()) {
                    response = "The task list is empty.\n";
                } else {
                    response = "Invalid task index\n";
                }
            } catch (NumberFormatException e) {
                response = "Non-integer task index provided\n";
            }
            isValid = false;
            break;

        case "delete":
            try {
                int taskNumber = Integer.parseInt(userInputParts[1]);
                Task removed = tasks.remove(taskNumber - 1);
                response = "Noted. I've removed this task:\n   " + removed.toString() + "\n";

                response += "Now you have " + tasks.size() + " tasks in the list.\n";

            } catch (IndexOutOfBoundsException | NullPointerException e) {
                if (tasks.isEmpty()) {
                    response = "The task list is empty.\n";
                } else {
                    response = "Invalid task index\n";
                }
            } catch (NumberFormatException e) {
                response = "Non-integer task index provided\n";
            }
            isValid = false;
            break;

        case "find":
            String[] findArr = input.split("find ");
            isValid = false;
            if (tasks.isEmpty()) {
                response = "The task list is empty; no tasks to be found.\n";
            }
            String searchPhrase = findArr[1];
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (Task t: tasks.getAll()) {
                if (t.getDescription().contains(searchPhrase)) {
                    matchingTasks.add(t);
                }
            }
            if (!matchingTasks.isEmpty()) {
                response = "Here are the matching tasks in your list:\n";
                for (int j = 0; j < matchingTasks.size(); j++) {
                    Task t = matchingTasks.get(j);
                    response += (j + 1) + "." + t;
                }
            } else {
                response = "Sorry, no matching tasks were found. "
                        + "Please double check your search phrase and task list again.\n";
            }
            break;

        case "todo":
            try {
                String[] todoArr = input.split("todo ");
                tasks.add(new ToDo(todoArr[1]));
            } catch (IndexOutOfBoundsException e) {
                response = "Wrong input syntax. Correct syntax is 'todo (task name)'.\n";
                isValid = false;
            }
            break;

        case "deadline":
            /* given the command deadline finish homework /by 2026-01-25 2000:
                deadlineArr1 = [, finish homework /by 2026-01-25 2000]
                deadlineArr2 = [finish homework, 2026-01-25 2000]
            */
            try {
                String[] deadlineArr1 = input.split("deadline ");
                String[] deadlineArr2 = deadlineArr1[1].split(" /by ");
                String description = deadlineArr2[0];
                String dateStr = deadlineArr2[1];

                LocalDateTime dateTime =
                        LocalDateTime.parse(dateStr,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));


                tasks.add(new Deadline(description, dateTime));
            } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                response = "Wrong input syntax. Correct syntax is 'deadline (task name) /by (deadline)'.\n"
                        + "Format for deadline is 'YYYY-MM-DD time'.\n"
                        + "Times should be in 24-hour format i.e XXXX.\n";
                isValid = false;
            }
            break;

        case "event":
            /* given the command event family dinner /from 2026-01-25 6pm /to 2026-01-25 7pm:
                eventArr1 = [, family dinner /from 2026-01-25 6pm /to 2026-01-25 7pm]
                eventArr2 = [family dinner, 2026-01-25 6pm /to 2026-01-25 7pm]
                eventArr3 = [2026-01-25 6pm, 2026-01-25 7pm]
            */
            try {
                String[] eventArr1 = input.split("event ");
                String[] eventArr2 = eventArr1[1].split(" /from ");
                String[] eventArr3 = eventArr2[1].split(" /to ");

                String fromDateStr = eventArr3[0];
                String toDateStr = eventArr3[1];

                LocalDateTime fromDateTime =
                        LocalDateTime.parse(fromDateStr,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

                LocalDateTime toDateTime =
                        LocalDateTime.parse(toDateStr,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

                tasks.add(new Event(eventArr2[0], fromDateTime, toDateTime));
            } catch (IndexOutOfBoundsException | DateTimeParseException e) {
                response = "Wrong input syntax. Correct syntax is 'event (task name) /from (start) /to (end)'.\n"
                        + "Format for both start and end is 'YYYY-MM-DD time'.\n"
                        + "Times should be in 24-hour format i.e XXXX.\n";
                isValid = false;
            }
            break;

        case "invalid":
            // invalid command or empty input
            response = "Your input is either empty or not recognised. Please try again.\n";
            isValid = false;
            break;

        default:
            assert false : "Unknown parser phrase: " + parserPhrase;
            break;
        }

        if (isValid) {
            // only increments and adds to task list if task command is valid
            response = "\n" + "Got it. I've added this task: " + tasks.get(tasks.size() - 1).toString() + "\n";
            response += "Now you have " + tasks.size() + " tasks in the list.\n";
            assert !tasks.isEmpty() : "Valid command but no task was added";
        }

        try {
            storage.save(tasks.getAll());
        } catch (Exception e) {
            response = "Error saving tasks.";
        }
        return response;
    }

    public String getStartupMessage() {
        return startupMessage;
    }
}




