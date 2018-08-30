package backend;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class SyntheticGenerator {

    private static int imageWidth = 300, imageHeight = 300;

    public static WritableImage squareGenerator() {
        WritableImage wr = new WritableImage(imageWidth, imageHeight);
        int width = new Double(wr.getWidth()).intValue();
        int height = new Double(wr.getHeight()).intValue();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color;
                if (i > width * 0.25 && i < width * 0.75 && j > height * 0.25 && j < height * 0.75) {
                    color = new Color(0, 0, 0, 1);
                } else {
                    color = new Color(1, 1, 1, 1);
                }
                wr.getPixelWriter().setColor(i, j, color);
            }
        }
        return wr;
    }

    public static WritableImage circleGenerator() {
        WritableImage wr = new WritableImage(imageWidth, imageHeight);
        int width = new Double(wr.getWidth()).intValue();
        int height = new Double(wr.getHeight()).intValue();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color;
                if (Math.sqrt(Math.pow((i-width/2),2) + Math.pow((j-height/2),2)) > imageWidth * 0.25) {
                    color = new Color(0, 0, 0, 1);
                } else {
                    color = new Color(1, 1, 1, 1);
                }
                wr.getPixelWriter().setColor(i, j, color);
            }
        }
        return wr;
    }

    public static WritableImage stripesGenerator() {
        WritableImage wr = new WritableImage(imageWidth, imageHeight);
        int width = new Double(wr.getWidth()).intValue();
        int height = new Double(wr.getHeight()).intValue();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color;
                if (new Double(i / 10).intValue() % 2 == 0) {
                    color = new Color(0, 0, 0, 1);
                } else {
                    color = new Color(1, 1, 1, 1);
                }
                wr.getPixelWriter().setColor(i, j, color);
            }
        }
        return wr;
    }

    public static WritableImage blankGenerator(Color c) {
        WritableImage wr = new WritableImage(imageWidth, imageHeight);
        int width = new Double(wr.getWidth()).intValue();
        int height = new Double(wr.getHeight()).intValue();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                wr.getPixelWriter().setColor(i, j, c);
            }
        }
        return wr;
    }

}
