package shiho;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * A class representing the Shiho chatbot.
 */
@SuppressWarnings("checkstyle:Regexp")
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
    public String getResponse(String input) {
        String[] userInputParts = input.split(" ");
        String response = "";

        String parserPhrase = parser.parse(input);
        assert parserPhrase != null : "Parser output should not be null";

        switch (parserPhrase) {
        case "bye":
            response = handleBye();
            break;
        case "list":
            response = handleList();
            break;
        case "mark":
            response = handleMark(userInputParts);
            break;
        case "unmark":
            response = handleUnmark(userInputParts);
            break;
        case "delete":
            response = handleDelete(userInputParts);
            break;
        case "find":
            response = handleFind(input);
            break;
        case "todo":
            response = handleToDo(input);
            break;
        case "deadline":
            response = handleDeadline(input);
            break;
        case "event":
            response = handleEvent(input);
            break;

        case "invalid":
            response = handleInvalid();
            break;

        default:
            assert false : "Unknown parser phrase: " + parserPhrase;
            break;
        }

        return saveTasks(response);
    }

    /**
     * Handles the "bye" user input.
     *
     * @return The response string to be printed to the user.
     */
    private String handleBye() {
        return "Bye. Come back soon.";
    }

    /**
     * Handles the "list" user input.
     *
     * @return The response string to be printed to the user.
     */
    private String handleList() {
        if (tasks.isEmpty()) {
            return "The task list is empty.\n";
        }
        StringBuilder response = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            int number = i + 1;
            response.append(number).append(".").append(tasks.get(i).toString()).append("\n");

            if (i == tasks.size() - 1) {
                response.append("\n"); // adds a newline if it's the last element of the list
            }
        }
        return response.toString();
    }

    /**
     * Handles the "mark" user input.
     *
     * @return The response string to be printed to the user.
     */
    private String handleMark(String[] userInputParts) {
        try {
            int taskNumber = Integer.parseInt(userInputParts[1]);
            tasks.get(taskNumber - 1).markAsDone();
            return "Okay. I've marked this task as done:\n"
                    + "   ["
                    + tasks.get(taskNumber - 1).getStatusIcon()
                    + "] "
                    + tasks.get(taskNumber - 1).getDescription()
                    + "\n";
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            if (tasks.isEmpty()) {
                return "The task list is empty.\n";
            } else {
                return "Invalid task index\n";
            }
        } catch (NumberFormatException e) {
            return "Non-integer task index provided\n";
        }
    }

    /**
     * Handles the "unmark" user input.
     *
     * @return The response string to be printed to the user.
     */
    private String handleUnmark(String[] userInputParts) {
        try {
            int taskNumber = Integer.parseInt(userInputParts[1]);
            tasks.get(taskNumber - 1).markAsUndone();
            return "Okay. I've marked this task as not done yet:\n"
                    + "   ["
                    + tasks.get(taskNumber - 1).getStatusIcon()
                    + "] "
                    + tasks.get(taskNumber - 1).getDescription()
                    + "\n";
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            if (tasks.isEmpty()) {
                return "The task list is empty.\n";
            } else {
                return "Invalid task index\n";
            }
        } catch (NumberFormatException e) {
            return "Non-integer task index provided\n";
        }
    }

    /**
     * Handles the "delete" user input.
     *
     * @return The response string to be printed to the user.
     */
    private String handleDelete(String[] userInputParts) {
        try {
            int taskNumber = Integer.parseInt(userInputParts[1]);
            Task removed = tasks.remove(taskNumber - 1);
            return "Noted. I've removed this task:\n   " + removed.toString() + "\n"
                    + "Now you have " + tasks.size() + " tasks in the list.\n";

        } catch (IndexOutOfBoundsException | NullPointerException e) {
            if (tasks.isEmpty()) {
                return "The task list is empty.\n";
            } else {
                return "Invalid task index\n";
            }
        } catch (NumberFormatException e) {
            return "Non-integer task index provided\n";
        }
    }

    /**
     * Handles the "find" user input.
     *
     * @param userInput The user input.
     * @return The response string to be printed to the user.
     */
    private String handleFind(String userInput) {
        String[] findArr = userInput.split("find ");
        if (tasks.isEmpty()) {
            return "The task list is empty; no tasks to be found.\n";
        }
        ArrayList<Task> matchingTasks = findMatchingTasks(findArr);
        if (matchingTasks.isEmpty()) {
            return "Sorry, no matching tasks were found. "
                    + "Please double check your search phrase and task list again.\n";
        }
        StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int j = 0; j < matchingTasks.size(); j++) {
            Task t = matchingTasks.get(j);
            response.append((j + 1)).append(".").append(t);
        }
        return response.toString();
    }

    /**
     * Finds and returns a list of matching tasks with respect to the specified key phrase.
     *
     * @param findArr The user's input split into multiple strings.
     * @return An ArrayList of tasks that contain the specified key phrase.
     */
    private ArrayList<Task> findMatchingTasks(String[] findArr) {
        String searchPhrase = findArr[1];
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task: tasks.getAll()) {
            if (task.getDescription().contains(searchPhrase)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Handles the "todo" user input.
     *
     * @param userInput The user input.
     * @return The response string to be printed to the user.
     */
    private String handleToDo(String userInput) {
        try {
            String[] todoArr = userInput.split("todo ");
            tasks.add(new ToDo(todoArr[1]));
        } catch (IndexOutOfBoundsException e) {
            return "Wrong input syntax. Correct syntax is 'todo (task name)'.\n";
        }
        // this point will only be reached if the input is valid
        return returnValidTaskResponse();
    }

    /**
     * Handles the "deadline" user input.
     *
     * @param userInput The user input.
     * @return The response string to be printed to the user.
     */
    private String handleDeadline(String userInput) {
        /* given the command deadline finish homework /by 2026-01-25 2000:
                deadlineArr1 = [, finish homework /by 2026-01-25 2000]
                deadlineArr2 = [finish homework, 2026-01-25 2000]
            */
        try {
            String[] deadlineArr1 = userInput.split("deadline ");
            String[] deadlineArr2 = deadlineArr1[1].split(" /by ");
            String description = deadlineArr2[0];
            String dateStr = deadlineArr2[1];

            LocalDateTime dateTime =
                    LocalDateTime.parse(dateStr,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            tasks.add(new Deadline(description, dateTime));
        } catch (IndexOutOfBoundsException | DateTimeParseException e) {
            return """
                    Wrong input syntax. Correct syntax is 'deadline (task name) /by (deadline)'.
                    Format for deadline is 'YYYY-MM-DD time'.
                    Times should be in 24-hour format i.e XXXX.
                    """;
        }
        // this point will only be reached if the input is valid
        return returnValidTaskResponse();
    }

    /**
     * Handles the "event" user input.
     *
     * @param userInput The user input.
     * @return The response string to be printed to the user.
     */
    private String handleEvent(String userInput) {
        /* given the command event family dinner /from 2026-01-25 1800 /to 2026-01-25 1900:
                eventArr1 = [, family dinner /from 2026-01-25 1800 /to 2026-01-25 1900]
                eventArr2 = [family dinner, 2026-01-25 1800 /to 2026-01-25 1900]
                eventArr3 = [2026-01-25 1800, 2026-01-25 1900]
            */
        try {
            String[] eventArr1 = userInput.split("event ");
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
            return """
                    Wrong input syntax. Correct syntax is 'event (task name) /from (start) /to (end)'.
                    Format for both start and end is 'YYYY-MM-DD time'.
                    Times should be in 24-hour format i.e XXXX.
                    """;
        }
        // this point will only be reached if the input is valid
        return returnValidTaskResponse();
    }

    /**
     * Handles an invalid response from the user, including empty inputs.
     *
     * @return The response string to be printed out to the user.
     */
    private String handleInvalid() {
        return "Your input is either empty or not recognised. Please try again.\n";
    }

    /**
     * Handles and returns a response in the case of a valid task being added to the task list.
     *
     * @return The response string to be printed to the user.
     */
    private String returnValidTaskResponse() {
        String response = "\n" + "Got it. I've added this task: " + tasks.get(tasks.size() - 1).toString() + "\n";
        response += "Now you have " + tasks.size() + " tasks in the list.\n";
        assert !tasks.isEmpty() : "Valid command but no task was added";
        return response;
    }

    /**
     * Saves the tasks to the task list in storage, and returns a response depending on the saving status.
     *
     * @param response The existing response to be returned to the user.
     * @return The response String to be printed, if an error is encountered.
     */
    private String saveTasks(String response) {
        try {
            storage.save(tasks.getAll());
        } catch (Exception e) {
            response = "Error saving tasks.";
        }
        return response;
    }

    /**
     * Returns the greeting message of Shiho.
     * @return Shiho's greeting message.
     */
    public String getStartupMessage() {
        return startupMessage;
    }
}




