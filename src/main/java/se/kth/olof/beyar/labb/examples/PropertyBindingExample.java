package se.kth.olof.beyar.labb.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PropertyBindingExample extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();

        Line line1 = new Line(0, 300, 300, 0);
        line1.setStrokeWidth(2);
        line1.setStroke(Color.BLUE);

        Line line2 = new Line(0, 0, 300, 300);
        line2.endXProperty().bind(root.widthProperty());
        line2.endYProperty().bind(root.heightProperty());
        line2.setStrokeWidth(2);
        line2.setStroke(Color.RED);
        
        Text text = new Text(10, 30, 
                "The red lines endpoint property is bound to \n"
                + "the panes width and height - resize the window!");

        root.getChildren().addAll(line1, line2, text);

        primaryStage.setTitle("Property binding example");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
