package se.kth.olof.beyar.labb.examples;

import java.util.Date;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AnimationTimerExample extends Application {

    private Text text;
    private ClockTimer timer;
    private boolean isRunning;

    @Override
    public void start(Stage primaryStage) {

        text = new Text("-");
        text.setFont(Font.font("Consolas", FontWeight.SEMI_BOLD, 24));

        Button toggleButton = new Button("Start/Stop");
        toggleButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (isRunning) {
                    timer.stop();
                    isRunning = false;
                    System.out.println("stop");
                } else {
                    timer.start();
                    isRunning = true;
                    System.out.println("start");
                }
            }
        });

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(text, toggleButton);

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        timer = new ClockTimer();
        timer.start();
        isRunning = true;
    }

    protected class ClockTimer extends AnimationTimer {

        @Override
        public void handle(long nowNs) {

            text.setText((new Date().toString()));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
