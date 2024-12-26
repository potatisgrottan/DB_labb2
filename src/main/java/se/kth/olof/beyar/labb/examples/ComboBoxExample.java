package se.kth.olof.beyar.labb.examples;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComboBoxExample extends Application {

    private VBox root;
    private ComboBox<String> comboBox;

    @Override
    public void start(Stage primaryStage) {

        root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "Red",
                        "Green",
                        "Blue"
                );
        comboBox = new ComboBox<>(options);
        comboBox.setPromptText("Pick a color");
        comboBox.setOnAction(new ComboBoxHandler());
        root.getChildren().add(comboBox);

        Scene scene = new Scene(root, 300, 300);

        primaryStage.setTitle("EventHandlerExample 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class ComboBoxHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String color
                    = comboBox.getSelectionModel()
                    .getSelectedItem().toString();
            String styleStr = "-fx-background-color: " + color; // CSS style
            root.setStyle(styleStr);
        }
    }
}
