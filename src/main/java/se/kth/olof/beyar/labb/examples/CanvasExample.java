package se.kth.olof.beyar.labb.examples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CanvasExample extends Application {

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Canvas canvas = new Canvas(200, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        draw(gc);
        root.getChildren().add(canvas);

        primaryStage.setTitle("Canvas example");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void draw(GraphicsContext gc) {

        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);

        gc.strokeLine(40, 10, 10, 40);

        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);

        gc.fillRect(10, 110, 30, 30);
        gc.strokeRect(60, 110, 30, 30);

        gc.strokePolygon(new double[]{10, 40, 10, 40},
                new double[]{160, 160, 200, 200}, 4);

        gc.setFont(Font.font("Consolas", 20));
        gc.setFill(Color.BLACK);
        gc.fillText("GraphicsContext example", 10, 240);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
