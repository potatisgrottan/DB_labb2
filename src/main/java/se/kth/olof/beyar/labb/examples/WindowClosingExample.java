package se.kth.olof.beyar.labb.examples;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WindowClosingExample extends Application {

	@Override
	public void start(Stage stage) {
		Text text = new Text("Hello World!");
		StackPane root = new StackPane();
		root.getChildren().add(text);

		stage.setScene(new Scene(root, 300, 100));
		stage.setTitle("WindowEvent example");

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == ButtonType.CANCEL) {
					event.consume();
				}
			}
		});

		stage.show();
	}

	private final Alert alert = new Alert(Alert.AlertType.INFORMATION, "Exit?", ButtonType.OK, ButtonType.CANCEL);
	
	public static void main(String[] args) {
		launch(args);
	}
}
