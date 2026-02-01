package shiho;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Shiho shiho;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/ichika.jpg"));
    private final Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/shiho.jpeg"));

    /**
     * Initialises the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Shiho's initial greeting to the user
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(
                        "Hello, I'm Shiho Hinomori. What do you need?",
                        dukeImage
                )
        );
    }

    /** Injects the Duke instance */
    public void setShiho(Shiho s) {
        shiho = s;

        // check for errors in loading the task list file from storage and display error message
        if (!shiho.getStartupMessage().isEmpty()) {
            dialogContainer.getChildren().add(
                    DialogBox.getDukeDialog(shiho.getStartupMessage(), dukeImage)
            );
        }
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = shiho.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();

        if (input.equals("bye")) {
            Platform.exit();
        }
    }
}
