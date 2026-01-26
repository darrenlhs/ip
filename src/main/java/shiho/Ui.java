package shiho;

import java.util.Scanner;

public class Ui {

    private Scanner scanner = new Scanner(System.in);

    public String readCommand() {
        return scanner.nextLine();
    }

    public void show(String msg) {
        System.out.println(msg);
    }

    public void showLoadError() {
        System.out.println("No existing task list found in storage. Creating empty task list.");
    }
}
