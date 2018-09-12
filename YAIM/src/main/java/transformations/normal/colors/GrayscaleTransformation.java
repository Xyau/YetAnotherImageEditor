package transformations.normal.colors;

import backend.DenormalizedColor;
import backend.utils.ColorUtils;
import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.function.Function;

public class GrayscaleTransformation extends PixelByPixelTransformation {
    private static Function<DenormalizedColor,DenormalizedColor> GRAYSCALE = ColorUtils::getGreyscale ;
    public GrayscaleTransformation() {
        super(GRAYSCALE);
    }

    @Override
    public String getDescription() {
        return "Returns the image in grayscale";
    }
}
