package se.kth.olof.beyar.labb.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileChooserExample extends Application {

    private TextArea textArea;
    private Label fileInfoLabel;
    private FileChooser fileChooser;
    private Stage stage;

    private VBox root;

    @Override
    public void start(Stage primaryStage) {

        root = new VBox();
        stage = primaryStage;

        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        createMenu();
        createUI();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAndReadFile() {

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            String path = file.getPath();
            fileInfoLabel.setText(path);

            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(path));
                String line = in.readLine();
                while (line != null) {
                    textArea.appendText(line + "\n");
                    line = in.readLine();
                }
            } catch (IOException ie) {
                textArea.appendText("Unable to read file.");
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    private void createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menuBar.getMenus().add(menu);
        MenuItem openItem = new MenuItem("Open");
        menu.getItems().add(openItem);

        openItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                openAndReadFile();
            }
        });
        root.getChildren().add(menuBar);
    }

    private void createUI() {
        textArea = new TextArea("");
        textArea.setEditable(false);
        textArea.setPrefRowCount(20);
        textArea.setPrefColumnCount(50);
        textArea.setWrapText(true);

        fileInfoLabel = new Label("No file selected");
        fileInfoLabel.setPadding(new Insets(5, 5, 5, 5));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(textArea);
        borderPane.setTop(fileInfoLabel);
        root.getChildren().add(borderPane);
        VBox.setVgrow(borderPane, Priority.ALWAYS);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
