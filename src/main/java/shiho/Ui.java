package shiho;

import java.util.Scanner;

public class Ui {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Returns the command given by the user.
     *
     * @return String representing the user input
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the specified message out to the system.
     *
     * @param msg Message string to be printed out.
     */
    public void show(String msg) {
        System.out.println(msg);
    }

    /**
     * Prints out a specific error message when no existing task list is detected by the chatbot.
     *
     */
    public void showLoadError() {
        System.out.println("No existing task list found in storage. Creating empty task list.");
    }
}
