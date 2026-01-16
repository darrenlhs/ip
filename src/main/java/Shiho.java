import java.util.Scanner;

public class Shiho {
    public static void main(String[] args) {
        String[] user_inputs = new String[100];
        int counter = 0;

        System.out.println("Hello, I'm Shiho.\n"
                + "What do you need?\n");

        while (true) {
            Scanner myScan = new Scanner(System.in);
            String userReply = myScan.nextLine();

            if (userReply.equals("bye")) {
                System.out.println("Bye. Come back soon.");
                break;
            } else if (userReply.equals("list")) {
                for (int i = 0; i < user_inputs.length; i++) {
                    if (user_inputs[i] == null) {
                        break;
                    }
                    int number = i + 1;
                    System.out.println(number + ". " + user_inputs[i]);
                }
            } else {
                user_inputs[counter] = userReply;
                counter++;
                System.out.println("added: " + userReply);
            }
        }

    }



}

