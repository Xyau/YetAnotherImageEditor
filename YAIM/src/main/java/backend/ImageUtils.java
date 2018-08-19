package backend;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static BufferedImage createImage(int rgb, int height, int width){
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                image.setRGB(j,i,rgb);
            }
        }
        return image;
    }

    public static boolean writeImage(BufferedImage image, File file){
        try {
            ImageIO.write(image,"png",file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static BufferedImage readImage(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static WritableImage copyImage(Image image){
        Integer height = Utils.toInteger(image.getHeight());
        Integer width = Utils.toInteger(image.getWidth());


        WritableImage writableImage
                = new WritableImage(width,height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = image.getPixelReader().getColor(x, y);
                pixelWriter.setColor(x, y, color);
            }
        }
        return writableImage;
    }

    public static GraphicsContext getGraphicsContextFromImage(WritableImage writableImage){
        javafx.scene.canvas.Canvas canvas = new Canvas(writableImage.getWidth(), writableImage.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(writableImage,0,0);
        return gc;
    }

    public static WritableImage getImageFromGraphicsContext(GraphicsContext gc, WritableImage writableImage){
        return gc.getCanvas().snapshot(null,writableImage);
    }
}
