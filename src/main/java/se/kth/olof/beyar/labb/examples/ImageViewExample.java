/*
IntelliJ and resources
---------------------------------------------------------------------------
Put images, text files, and similar static resources, in the "resources" folder
in your JavaFX project (there might be a package structure similar to the one
int the source code in the "resources folder, if so, use that folder).
 */
package se.kth.olof.beyar.labb.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;

public class ImageViewExample extends Application {

    @Override
    public void start(Stage stage) {

        // Load the image - see comment above on where to place the image file.
        URL resource = this.getClass().getResource("/images/skull_ct.png");
        assert resource != null;
        Image image = new Image(resource.toString());

        // Display the image as is.
        ImageView firstView = new ImageView();
        firstView.setImage(image); // toGrayScale(image) - image ops

        // Resize the image to have width of 100 pxs while preserving the ratio
        // and using higher quality filtering method. NB - we are using
        // the same image object.
        ImageView secondView = new ImageView();
        secondView.setImage(image);
        secondView.setFitWidth(100);
        secondView.setPreserveRatio(true);
        secondView.setSmooth(true);

        // Display the same image object rotated
        ImageView rotatedView = new ImageView();
        rotatedView.setImage(image);
        rotatedView.setRotate(180);

        HBox root = new HBox();
        root.getChildren().add(firstView);
        root.getChildren().add(secondView);
        root.getChildren().add(rotatedView);

        Scene scene = new Scene(root);
        scene.setFill(Color.WHITE);

        stage.setTitle("ImageView example");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /*
    Using WritableImage and PixelReader/Writer
    to manipulate the pixels of an image object.

    To get an int[] representing the pixels,
    use PixelReader.getPixels.
    */
    private static Image toGrayScale(Image origImg) {
        PixelReader origPixels = origImg.getPixelReader();
        int width = (int) origImg.getWidth();
        int height = (int) origImg.getHeight();
        System.out.println("width = " + width + ", height = " + height);
        System.out.println("Pixel Format: " + origPixels.getPixelFormat());

        WritableImage outImg = new WritableImage(width, height);
        PixelWriter outPixels = outImg.getPixelWriter();
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Color color = origPixels.getColor(x, y);
                double ave = (color.getRed()
                        + color.getGreen()
                        + color.getRed())/3.0;
                Color gray = Color.color(ave, ave, ave);
                outPixels.setColor(x, y, gray);
            }
        }

        return outImg;
    }
}

