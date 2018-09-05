package transformations.colors;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import transformations.Transformation;
import transformations.common.PixelByPixelTransformation;

import java.util.function.Function;

public class BrightenTransformation extends PixelByPixelTransformation {
    private static Function<Color,Color> BRIGTHEN = Color::brighter;
    public BrightenTransformation() {
        super(BRIGTHEN);
    }

    @Override
    public String getDescription() {
        return "Brightens the image";
    }
}
