package transformations.normal.colors;

import backend.DenormalizedColor;
import backend.utils.ColorUtils;
import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.function.Function;

public class BrightenTransformation extends PixelByPixelTransformation {
    private static Function<DenormalizedColor,DenormalizedColor> BRIGTHEN = ColorUtils::getBrighter;
    public BrightenTransformation() {
        super(BRIGTHEN);
    }

    @Override
    public String getDescription() {
        return "Brightens the image";
    }
}
