import java.util.Scanner;

public class Shiho {
    public static void main(String[] args) {
        Task[] user_inputs = new Task[100];
        int counter = 0;

        System.out.println("Hello, I'm Shiho.\n" + "What do you need?\n");

        while (true) {
            Scanner myScan = new Scanner(System.in);
            String userReply = myScan.nextLine();
            String[] parts = userReply.split(" ");

            if (userReply.equals("bye")) {
                System.out.println("Bye. Come back soon.");
                break;
            } else if (userReply.equals("list")) {
                System.out.println("Here are your tasks:");
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
            } else if (parts[0].equals("mark")) {
                int taskNumber = Integer.parseInt(parts[1]);
                user_inputs[taskNumber - 1].markAsDone();
                System.out.println(
                        "Okay. I've marked this task as done:\n"
                        + "   ["
                        + user_inputs[taskNumber - 1].getStatusIcon()
                        + "] "
                        + user_inputs[taskNumber - 1].description
                );


            } else if (parts[0].equals("unmark")) {
                int taskNumber = Integer.parseInt(parts[1]);
                user_inputs[taskNumber - 1].markAsUndone();
                System.out.println(
                        "Okay. I've marked this task as not done yet:\n"
                                + "   ["
                                + user_inputs[taskNumber - 1].getStatusIcon()
                                + "] "
                                + user_inputs[taskNumber - 1].description
                );
            } else {
                user_inputs[counter] = new Task(userReply);
                counter++;
                System.out.println("\n" + "added: " + userReply + "\n");
            }
        }

    }



}



