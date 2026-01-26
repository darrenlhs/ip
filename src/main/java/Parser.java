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


    public void parse(String userInput) {
        String[] parts = userInput.split(" ");

        if (userInput.equals("bye")) {
            // bye command ends the current instance

            ui.show("Bye. Come back soon.");
        } else if (userInput.equals("list")) {
            // list command displays current list of tasks

            if (tasks.isEmpty()) {
                ui.show("The task list is empty.\n");
            } else {
                ui.show("Here are your tasks:\n");
                for (int i = 0; i < tasks.size(); i++) {
                    int number = i + 1;
                    ui.show(number
                            + "."
                            + tasks.get(i).toString()
                    );
                }
            }
        } else if (parts[0].equals("mark")) {
            // mark command marks a specific task as done

            try {
                int taskNumber = Integer.parseInt(parts[1]);
                tasks.get(taskNumber - 1).markAsDone();
                ui.show(
                    "Okay. I've marked this task as done:\n"
                            + "   ["
                            + tasks.get(taskNumber - 1).getStatusIcon()
                            + "] "
                            + tasks.get(taskNumber - 1).description
                );
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                if (tasks.isEmpty()) {
                    ui.show("The task list is empty.\n");
                } else {
                    ui.show("Invalid task index\n");
                }
            } catch (NumberFormatException e) {
                ui.show("Non-integer task index provided\n");
            }


        } else if (parts[0].equals("unmark")) {
            // unmark command marks a specific task as undone

            try {
                int taskNumber = Integer.parseInt(parts[1]);
                tasks.get(taskNumber - 1).markAsUndone();
                ui.show(
                    "Okay. I've marked this task as not done yet:\n"
                            + "   ["
                            + tasks.get(taskNumber - 1).getStatusIcon()
                            + "] "
                            + tasks.get(taskNumber - 1).description
                );
            } catch (IndexOutOfBoundsException | NullPointerException e) {
                if (tasks.isEmpty()) {
                    ui.show("The task list is empty.\n");
                } else {
                    ui.show("Invalid task index\n");
                }
            } catch (NumberFormatException e) {
                ui.show("Non-integer task index provided\n");
            }
        } else if (parts[0].equals("delete")) {
            // delete command removes a specific task from the list
            try {
                int taskNumber = Integer.parseInt(parts[1]);
                Task removed = tasks.remove(taskNumber - 1);
                ui.show(
                    "Noted. I've removed this task:\n   "
                            + removed.toString()
                );

                ui.show("Now you have " + tasks.size() + " tasks in the list.\n");

            } catch (IndexOutOfBoundsException | NullPointerException e) {
                if (tasks.isEmpty()) {
                    ui.show("The task list is empty.\n");
                } else {
                    ui.show("Invalid task index\n");
                }
            } catch (NumberFormatException e) {
                ui.show("Non-integer task index provided\n");

            }
        } else {
            // todo, deadline and event commands indicate specific types of tasks to be added to the list

            boolean isValid = true;

            if (userInput.startsWith("todo")) {
                try {
                    String[] todoArr = userInput.split("todo ");
                    tasks.add(new ToDo(todoArr[1]));
                } catch (IndexOutOfBoundsException e) {
                    ui.show(
                            "Wrong input syntax. Correct syntax is 'todo (task name)'.\n"
                    );
                    isValid = false;
                }

            } else if (userInput.startsWith("deadline")) {
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
                    ui.show(
                            "Wrong input syntax. Correct syntax is 'deadline (task name) /by (deadline)'.\n"
                                    + "Format for deadline is 'YYYY-MM-DD time'.\n"
                                    + "Times should be in 24-hour format i.e XXXX.\n"
                    );
                    isValid = false;
                }

            } else if (userInput.startsWith("event")) {
                    /* given the command event family dinner /from 2026-01-25 6pm /to 2026-01-25 7pm:
                    eventArr1 = [, family dinner /from 2026-01-25 6pm /to 2026-01-25 7pm]
                    eventArr2 = [family dinner, 2026-01-25 6pm /to 2026-01-25 7pm]
                    eventArr3 = [2026-01-25 6pm, 2026-01-25 7pm]
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
                    ui.show(
                            "Wrong input syntax. Correct syntax is 'event (task name) /from (start) /to (end)'.\n"
                                    + "Format for both start and end is 'YYYY-MM-DD time'.\n"
                                    + "Times should be in 24-hour format i.e XXXX.\n"
                    );
                    isValid = false;
                }
            } else {
                // invalid command or empty input
                ui.show("Your input is either empty or not recognised. Please try again.\n");
                isValid = false;
            }
            if (isValid) {
                // only increments and adds to task list if task command is valid

                ui.show("\n" + "Got it. I've added this task: " + tasks.get(tasks.size() - 1).toString() + "\n");

                ui.show("Now you have " + tasks.size() + " tasks in the list.\n");



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