package transformations;

import backend.ImageUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class DrawLineTransformation implements Transformation {

    Integer x1,y1,x2,y2;
    Color color;

    public DrawLineTransformation(Integer x1, Integer y1, Integer x2, Integer y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        GraphicsContext gc = ImageUtils.getGraphicsContextFromImage(writableImage);
        gc.drawImage(writableImage,0,0);
        gc.fillText("asda",0,500);
        gc.setStroke(color);
        gc.setLineWidth(5);
        gc.strokeLine(x1, y1, x2, y2);

        return ImageUtils.getImageFromGraphicsContext(gc,writableImage);
    }

    @Override
    public String getDescription() {
        return "asd";
    }
}
