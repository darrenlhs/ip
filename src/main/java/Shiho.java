import java.util.ArrayList;
import java.util.Scanner;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;

public class Shiho {
    public static void main(String[] args) throws IOException {
        ArrayList<Task> user_inputs = new ArrayList<>();
        int counter = 0;

        System.out.println("Hello, I'm Shiho Hinomori.\n" + "What do you need?\n");

        Scanner myScan = new Scanner(System.in);

        while (true) {
            String userReply = myScan.nextLine();
            String[] parts = userReply.split(" ");

            if (userReply.equals("bye")) {
                // bye command ends the current instance

                System.out.println("Bye. Come back soon.");
                break;
            } else if (userReply.equals("list")) {
                // list command displays current list of tasks

                if (user_inputs.isEmpty()) {
                    System.out.println("The task list is empty.\n");
                } else {
                    System.out.println("Here are your tasks:\n");
                    for (int i = 0; i < user_inputs.size(); i++) {
                        int number = i + 1;
                        System.out.println(number
                                + "."
                                + user_inputs.get(i).toString()
                        );
                    }
                }
            } else if (parts[0].equals("mark")) {
                // mark command marks a specific task as done

                try {
                    int taskNumber = Integer.parseInt(parts[1]);
                    user_inputs.get(taskNumber - 1).markAsDone();
                    System.out.println(
                            "Okay. I've marked this task as done:\n"
                                    + "   ["
                                    + user_inputs.get(taskNumber - 1).getStatusIcon()
                                    + "] "
                                    + user_inputs.get(taskNumber - 1).description
                    );
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    if (user_inputs.isEmpty()) {
                        System.out.println("The task list is empty.\n");
                    } else {
                        System.out.println("Invalid task index\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Non-integer task index provided\n");
                }


            } else if (parts[0].equals("unmark")) {
                // unmark command marks a specific task as undone

                try {
                    int taskNumber = Integer.parseInt(parts[1]);
                    user_inputs.get(taskNumber - 1).markAsUndone();
                    System.out.println(
                            "Okay. I've marked this task as not done yet:\n"
                                    + "   ["
                                    + user_inputs.get(taskNumber - 1).getStatusIcon()
                                    + "] "
                                    + user_inputs.get(taskNumber - 1).description
                    );
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    if (user_inputs.isEmpty()) {
                        System.out.println("The task list is empty.\n");
                    } else {
                        System.out.println("Invalid task index\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Non-integer task index provided\n");
                }
            } else if (parts[0].equals("delete")) {
                // delete command removes a specific task from the list
                try {
                    int taskNumber = Integer.parseInt(parts[1]);
                    counter--;
                    System.out.println(
                            "Noted. I've removed this task:\n"
                                    + "   "
                                    + user_inputs.get(taskNumber - 1).toString()
                    );
                    if (counter == 1) {
                        System.out.println("Now you have 1 task in the list.\n");
                    } else {
                        System.out.println("Now you have " + counter + " tasks in the list.\n");

                        user_inputs.remove(taskNumber - 1);
                    }
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    if (user_inputs.isEmpty()) {
                        System.out.println("The task list is empty.\n");
                    } else {
                        System.out.println("Invalid task index\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Non-integer task index provided\n");

                }
            } else {
                // todo, deadline and event commands indicate specific types of tasks to be added to the list

                boolean isValid = true;

                if (userReply.startsWith("todo")) {
                    try {
                        String[] todoArr = userReply.split("todo ");
                        user_inputs.add(new ToDo(todoArr[1]));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(
                                "Wrong input syntax. Correct syntax is 'todo (task name)'.\n"
                        );
                        isValid = false;
                    }

                } else if (userReply.startsWith("deadline")) {
                    /* given the command deadline finish homework /by 8pm:
                    deadlineArr1 = [finish homework /by 8pm]
                    deadlineArr2 = [finish homework, 8pm]
                     */
                    try {
                        String[] deadlineArr1 = userReply.split("deadline ");
                        String[] deadlineArr2 = deadlineArr1[1].split(" /by ");
                        user_inputs.add(new Deadline(deadlineArr2[0], deadlineArr2[1]));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(
                                "Wrong input syntax. Correct syntax is 'deadline (task name) /by (deadline)'.\n"
                        );
                        isValid = false;
                    }

                } else if (userReply.startsWith("event")) {
                    /* given the command event family dinner /from today 6pm /to 7pm:
                    eventArr1 = [, family dinner /from today 6pm /to 7pm]
                    eventArr2 = [family dinner, today 6pm /to 7pm]
                    eventArr3 = [today 6pm, 7pm]
                     */
                    try {
                        String[] eventArr1 = userReply.split("event ");
                        String[] eventArr2 = eventArr1[1].split(" /from ");
                        String[] eventArr3 = eventArr2[1].split(" /to ");

                        user_inputs.add(new Event(eventArr2[0], eventArr3[0], eventArr3[1]));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(
                                "Wrong input syntax. Correct syntax is 'event (task name) /from (start) /to (end)'.\n"
                        );
                        isValid = false;
                    }
                } else {
                    // invalid command or empty input

                    System.out.println("Your input is either empty or not recognised. Please try again.\n");
                    isValid = false;
                }
                if (isValid) {
                    // only increments and adds to task list if task command is valid

                    System.out.println("\n" + "Got it. I've added this task: " + user_inputs.get(counter).toString() + "\n");
                    counter++;

                    if (counter == 1) {
                        System.out.println("Now you have 1 task in the list.\n");
                    } else {
                        System.out.println("Now you have " + counter + " tasks in the list.\n");
                    }

                    // creates a new file for task list if it doesn't exist yet. otherwise updates it

                    Path dataDir = Paths.get("data");
                    Path filePath = dataDir.resolve("tasks.txt");
                    Files.createDirectories(dataDir);

                    try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                        for (Task t : user_inputs) {
                            writer.write(t.toFileString());
                            writer.newLine();
                        }


                    } catch (IOException e) {

                    }








                }

            }
        }

    }
}




