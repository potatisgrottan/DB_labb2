package se.kth.olof.beyar.labb.examples;
/*
 Netbeans and resources, for example images, to be used by the application:
 ---------------------------------------------------------------------------
 For the specific project, right-click on ”Sources Packages” and select New/Folder; 
 name the folder, for example ”resources”. Copy images, and other resources, to the 
 new folder.
 Load the resource in the application code, e.g. an image: 
 Image im = new Image(”resources/devil.png”);
*/

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ImageAndGraphicsContext extends Application {

    private Canvas canvas;
    private Image image;
    private double x, y, width, height;

    @Override
    public void start(Stage primaryStage) {

        canvas = new Canvas(300, 300);
        canvas.setOnKeyPressed(new KeyHandler());

        Group root = new Group();
        root.getChildren().add(canvas);

        // Load the image
        image = new Image(
                this.getClass().
                getResource("resources/devil.png").
                toString());
        width = image.getWidth() / 5;
        height = image.getHeight() / 5;

        Scene scene = new Scene(root);
        primaryStage.setTitle("ImageAndGraphicsContext");
        primaryStage.setScene(scene);
        primaryStage.show();

        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        drawImage();
    }

    private void drawImage() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(image, x, y, width, height); // scaling
    }

    // Move the image using the "arrow keys".
    private class KeyHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case UP:
                    y = y - 1.0;
                    break;
                case DOWN:
                    y = y + 1.0;
                    break;
                case LEFT:
                    x = x - 1.0;
                    break;
                case RIGHT:
                    x = x + 1.0;
                    break;
                default:
            }

            drawImage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
