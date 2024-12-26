package se.kth.olof.beyar.labb.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXShapesExample extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        Shape[] shapes = this.createShapes();
        root.getChildren().addAll(shapes);

        primaryStage.setTitle("JavaFX Shapes example");
        primaryStage.setScene(new Scene(root, 200, 250));
        primaryStage.show();
    }

    private Shape[] createShapes() {
        Line line = new Line(40, 10, 10, 40);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(5);

        Circle filledCircle = new Circle(25, 75, 15);
        filledCircle.setFill(Color.GREEN);

        Circle circle = new Circle(75, 75, 15);
        circle.setStroke(Color.BLUE);
        circle.setStrokeWidth(5.0);
        circle.setFill(Color.WHITE);

        Rectangle filledRect = new Rectangle(10, 110, 30, 30);
        filledRect.setFill(Color.GREEN);

        Rectangle rect = new Rectangle(60, 110, 30, 30);
        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(5.0);
        rect.setFill(Color.WHITE);

        double[] vertices = {10, 160, 40, 160, 10, 200, 40, 200};
        Polygon polygon = new Polygon(vertices);
        polygon.setStroke(Color.BLUE);
        polygon.setStrokeWidth(5);
        polygon.setFill(Color.WHITE);

        Text text = new Text(10, 240, "JavaFX Shapes");
        text.setFont(Font.font("Consolas", 20));
        text.setFill(Color.BLACK);

        Shape[] shapes =
                {line, filledCircle, circle, filledRect, rect, polygon, text};
        return shapes;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
