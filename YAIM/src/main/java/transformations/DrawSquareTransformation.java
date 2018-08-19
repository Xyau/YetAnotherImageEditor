package transformations;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class DrawSquareTransformation implements Transformation {

    Integer x,y,height,width;
    Color color;

    public DrawSquareTransformation(Integer x, Integer y, Integer height, Integer width, Color color) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.color = color;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        for (int i = x; i < x+width; i++) {
            for (int j = y; j < y+width; j++) {
                writableImage.getPixelWriter().setColor(i,j,color);
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "asd";
    }
}
