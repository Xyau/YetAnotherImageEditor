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
import java.util.function.BiFunction;

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

    public static BufferedImage readBufferedImage(String path){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WritableImage transformImages(WritableImage imageToWriteIn, Image image, BiFunction<Color,Color,Color> function){
        for (int i = 0; i < imageToWriteIn.getWidth(); i++) {
            for (int j = 0; j < imageToWriteIn.getHeight(); j++) {
                if(isPixelInImage(image,new Pixel(i,j))){
                    Color writableColor = imageToWriteIn.getPixelReader().getColor(i,j);
                    Color color = image.getPixelReader().getColor(i,j);
                    Color resultColor = function.apply(writableColor,color);
                    imageToWriteIn.getPixelWriter().setColor(i,j,resultColor);
                }
            }
        }
        return imageToWriteIn;
    }

   public static WritableImage transformImagesNormalized(WritableImage imageToWriteIn, Image image, BiFunction<Color,Color,DenormalizedColor> function){
        DenormalizedColor[][] denormalizedColors = new DenormalizedColor[Utils.toInteger(imageToWriteIn.getHeight())]
                [Utils.toInteger(imageToWriteIn.getWidth())];
        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;
        for (int i = 0; i < imageToWriteIn.getWidth(); i++) {
            for (int j = 0; j < imageToWriteIn.getHeight(); j++) {
                if(isPixelInImage(image,new Pixel(i,j))){
                    Color writableColor = imageToWriteIn.getPixelReader().getColor(i,j);
                    Color color = image.getPixelReader().getColor(i,j);
                    DenormalizedColor resultColor = function.apply(writableColor,color);
                    min = Utils.getMin(min,resultColor);
                    max = Utils.getMax(max,resultColor);
                }
            }
        }
        ImageUtils.transferImageTo(imageToWriteIn,denormalizedColors,min,max);
        return imageToWriteIn;
    }

    public static WritableImage transferImageTo(WritableImage writableImage, Image image){
        for (int y = 0; y < writableImage.getHeight(); y++){
            for (int x = 0; x < writableImage.getWidth(); x++){
                Color color = image.getPixelReader().getColor(x, y);
                writableImage.getPixelWriter().setColor(x, y, color);
            }
        }
        return writableImage;
    }

    public static WritableImage transferImageTo(WritableImage writableImage, DenormalizedColor[][] denormalizedColors,
                                                Double min, Double max){
        PixelWriter px = writableImage.getPixelWriter();
        for (int y = 0; y < writableImage.getHeight(); y++){
            for (int x = 0; x < writableImage.getWidth(); x++){
                Double red = ColorUtils.normalize(denormalizedColors[y][x].getRed(),min,max);
                Double green = ColorUtils.normalize(denormalizedColors[y][x].getGreen(),min,max);
                Double blue = ColorUtils.normalize(denormalizedColors[y][x].getBlue(),min,max);
                Double alpha = denormalizedColors[y][x].getAlpha();

                px.setColor(x,y,new Color(red,green,blue,alpha));
            }
        }
        return writableImage;
    }

    public static Boolean isPixelInImage(Image image, Pixel pixel){
        return pixel.getX() > 0 && pixel.getX() < image.getWidth() &&
                pixel.getY() > 0 && pixel.getY() < image.getHeight();
    }

    public static Boolean isPixelInImage(Image image, Integer x, Integer y){
        return x > 0 && x < image.getWidth() &&
                y > 0 && y < image.getHeight();
    }

    public static WritableImage readImage(String path){
        return copyImage(new Image(new File(path).toURI().toString()));
    }

    public static WritableImage readImage(File file){
        return copyImage(new Image(file.toURI().toString()));
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


        WritableImage writableImage = new WritableImage(width,height);
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

    public static Color reescalateColorLinearly(double min, double max, double newMin, double newMax, double r, double g, double b, double a) {
        double red = r;
        double green = g;
        double blue = b;
        red = Math.max(0, Math.min(newMax, (red - min) / (max - min) * (newMax + newMin) + newMin));
        green = Math.max(0, Math.min(newMax, (green - min) / (max - min) * (newMax + newMin) + newMin));
        blue = Math.max(0, Math.min(newMax, (blue - min) / (max - min) * (newMax + newMin) + newMin));
        if (red < 0 || green < 0 || blue < 0)
            System.out.println(red + " " + green + " " + blue);
        return new Color(red, green, blue, a);
    }

}
