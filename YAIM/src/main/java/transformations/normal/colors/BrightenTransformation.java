package transformations.normal.colors;

import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

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
