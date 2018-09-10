package transformations.normal.colors;

import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.function.Function;

public class GrayscaleTransformation extends PixelByPixelTransformation {
    private static Function<Color,Color> GRAYSCALE = Color::grayscale;
    public GrayscaleTransformation() {
        super(GRAYSCALE);
    }

    @Override
    public String getDescription() {
        return "Returns the image in grayscale";
    }
}
