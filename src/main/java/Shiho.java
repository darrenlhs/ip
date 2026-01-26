public class Shiho {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Shiho(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadError();
            tasks = new TaskList();
        }
        // cause parser relies on both TaskList and Ui
        parser = new Parser(tasks, ui);
    }

    public void run() {
        ui.show("Hello, I'm Shiho Hinomori.");
        ui.show("What do you need?\n");

        while (true) {
            String input = ui.readCommand();

            if (input.equals("bye")) {
                parser.parse(input);
                break;
            }

            parser.parse(input);

            try {
                storage.save(tasks.getAll());
            } catch (Exception e) {
                ui.show("Error saving tasks.");
            }
        }

    }

    public static void main(String[] args) {
        new Shiho("data/tasks.txt").run();
    }
}




