package se.kth.olof.beyar.labb.examples;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TimerExample extends Application {

	private ImageView imView;
	
    @Override
    public void start(Stage stage) {

        // Load the image
        Image image = new Image(
                this.getClass()
                .getResource("resources/devil.png").toString());

        // Display the image as is.
        imView = new ImageView();
        imView.setImage(image);

        BorderPane root = new BorderPane();
        root.setCenter(imView);
        Scene scene = new Scene(root);

        stage.setTitle("Timer example");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        
        imView.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		imView.setRotate(180);
        		Timer timer = new Timer();
        		timer.schedule(new RotateBackTask(), 2000);
        	}
        });
    }
    
    private class RotateBackTask extends TimerTask {
    	@Override
    	public void run() {
    		imView.setRotate(0); // rotate back
    	}
    };

    public static void main(String[] args) {
        launch(args);
    }
}
