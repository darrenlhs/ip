import java.util.Scanner;

public class Shiho {
    public static void main(String[] args) {
        String logo = "Shiho";

        System.out.println("Hello, I'm Shiho.\n"
                + "What do you need?\n");

        while (true) {
            Scanner myScan = new Scanner(System.in);
            String userReply = myScan.nextLine();

            if (userReply.equals("bye")) {
                System.out.println("Bye. Come back soon.");
                break;
            }

            System.out.println(userReply);
        }

    }



}

