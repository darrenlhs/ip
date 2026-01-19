import java.util.Arrays;
import java.util.Scanner;

public class Shiho {
    public static void main(String[] args) {
        Task[] user_inputs = new Task[100];
        int counter = 0;

        System.out.println("Hello, I'm Shiho Hinomori.\n" + "What do you need?\n");

        Scanner myScan = new Scanner(System.in);

        while (true) {
            String userReply = myScan.nextLine();
            String[] parts = userReply.split(" ");

            if (userReply.equals("bye")) {
                System.out.println("Bye. Come back soon.");
                break;
            }
            else if (userReply.equals("list")) {
                System.out.println("Here are your tasks:\n");
                for (int i = 0; i < user_inputs.length; i++) {
                    if (user_inputs[i] == null) {
                        break;
                    }
                    int number = i + 1;
                    System.out.println(number
                            + "."
                            + "["
                            + user_inputs[i].getStatusIcon()
                            + "] "
                            + user_inputs[i].description);
                }
            }
            else if (parts[0].equals("mark")) {
                try {
                    int taskNumber = Integer.parseInt(parts[1]);
                    user_inputs[taskNumber - 1].markAsDone();
                    System.out.println(
                            "Okay. I've marked this task as done:\n"
                                    + "   ["
                                    + user_inputs[taskNumber - 1].getStatusIcon()
                                    + "] "
                                    + user_inputs[taskNumber - 1].description
                    );
                }
                catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    System.out.println("Invalid task index\n");
                }
                catch (NumberFormatException e) {
                    System.out.println("Non-integer task index provided\n");
                }


            }
            else if (parts[0].equals("unmark")) {
                try {
                    int taskNumber = Integer.parseInt(parts[1]);
                    user_inputs[taskNumber - 1].markAsUndone();
                    System.out.println(
                            "Okay. I've marked this task as not done yet:\n"
                                    + "   ["
                                    + user_inputs[taskNumber - 1].getStatusIcon()
                                    + "] "
                                    + user_inputs[taskNumber - 1].description
                    );
                }
                catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    System.out.println("Invalid task index\n");
                }
                catch (NumberFormatException e) {
                    System.out.println("Non-integer task index provided\n");
                }
            }
            else {
                boolean isValid = true;

                if (userReply.startsWith("todo")) {
                    try {
                        String[] todoArr = userReply.split("todo ");
                        user_inputs[counter] = new ToDo(todoArr[1]);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(
                                "Wrong input syntax. Correct syntax is 'todo (task name)'.\n"
                        );
                        isValid = false;
                    }

                }
                else if (userReply.startsWith("deadline")) {
                    /* given the command deadline finish homework /by 8pm:
                    deadlineArr1 = [finish homework /by 8pm]
                    deadlineArr2 = [finish homework, 8pm]
                     */
                    try {
                        String[] deadlineArr1 = userReply.split("deadline ");
                        String[] deadlineArr2 = deadlineArr1[1].split(" /by ");
                        user_inputs[counter] = new Deadline(deadlineArr2[0], deadlineArr2[1]);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(
                                "Wrong input syntax. Correct syntax is 'deadline (task name) /by (deadline)'.\n"
                        );
                        isValid = false;
                    }

                }
                else if (userReply.startsWith("event")) {
                    /* given the command event family dinner /from today 6pm /to 7pm:
                    eventArr1 = [, family dinner /from today 6pm /to 7pm]
                    eventArr2 = [family dinner, today 6pm /to 7pm]
                    eventArr3 = [today 6pm, 7pm]
                     */
                    try {
                        String[] eventArr1 = userReply.split("event ");
                        String[] eventArr2 = eventArr1[1].split(" /from ");
                        String[] eventArr3 = eventArr2[1].split(" /to ");

                        user_inputs[counter] = new Event(eventArr2[0], eventArr3[0], eventArr3[1]);
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println(
                                "Wrong input syntax. Correct syntax is 'event (task name) /from (start) /to (end)'.\n"
                        );
                        isValid = false;
                    }
                }
                else {
                    System.out.println("Your input is either empty or not recognised. Please try again.\n");
                    isValid = false;
                }
                if (isValid) {
                    // only increments and adds to task list if task command is valid

                    System.out.println("\n" + "Got it. I've added this task: " + user_inputs[counter].toString() + "\n");
                    counter++;

                    if (counter == 1) {
                        System.out.println("Now you have 1 task in the list.\n");
                    }
                    else {
                        System.out.println("Now you have " + counter + " tasks in the list.\n");
                    }
                }



            }
        }

    }



}



