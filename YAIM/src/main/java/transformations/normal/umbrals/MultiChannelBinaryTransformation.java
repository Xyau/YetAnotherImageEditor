package transformations.normal.umbrals;

import backend.DenormalizedColor;
import backend.transformators.Transformation;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.Objects;


public class MultiChannelBinaryTransformation extends PixelByPixelTransformation {
    private double threashold;

    public MultiChannelBinaryTransformation(double threashold) {
        super(c -> {
            double red = c.getRed() < threashold ? 0.0 : 1.0;
            double green = c.getGreen() < threashold ? 0.0 : 1.0;
            double blue = c.getBlue() < threashold ? 0.0 : 1.0;
            return new DenormalizedColor(red, green, blue, 1.0);
        });
        this.threashold = threashold;
    }

    @Override
    public String getDescription() {
        return "Multi channel Binary with threshold:" + threashold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiChannelBinaryTransformation that = (MultiChannelBinaryTransformation) o;
        return Double.compare(that.threashold, threashold) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(threashold);
    }

}
