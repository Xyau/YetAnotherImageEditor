package transformations.normal;

import backend.utils.ImageUtils;
import backend.transformators.Transformation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.*;


public class GradientTransformation implements Transformation {

    Integer x1,y1,x2,y2;
    Color color1, color2;

    public GradientTransformation(Integer x1, Integer y1, Integer x2, Integer y2, Color color1, Color color2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color1 = color1;
        this.color2 = color2;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        GraphicsContext gc = ImageUtils.getGraphicsContextFromImage(writableImage);
//        Stop[] stops = new Stop[] { new Stop(0, color1), new Stop(1, color2)};
        Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
        LinearGradient linearGradient = new LinearGradient(x1, y1, x2, y2, false, CycleMethod.NO_CYCLE, stops);
        gc.setFill(linearGradient);
        gc.fillRect(0, 0, writableImage.getWidth(), writableImage.getHeight());
        return ImageUtils.getImageFromGraphicsContext(gc,writableImage);
    }

    @Override
    public String getDescription() {
        return "Gradient from (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")";
    }
}
