package transformations.normal.umbrals;

import backend.DenormalizedColor;
import backend.transformators.Transformation;
import backend.utils.Utils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

import javax.rmi.CORBA.Util;
import java.util.Objects;


public class MultiChannelBinaryTransformation extends PixelByPixelTransformation {
    private double threasholdRed, threasholdGreen, threasholdBlue;

    public MultiChannelBinaryTransformation(double threashold) {
        this(threashold,threashold,threashold);
    }

    public MultiChannelBinaryTransformation(double threasholdRed, double threasholdGreen, double threasholdBlue) {
        super(c -> {
            double red = c.getRed() < threasholdRed ? 0.0 : 1.0;
            double green = c.getGreen() < threasholdGreen ? 0.0 : 1.0;
            double blue = c.getBlue() < threasholdBlue ? 0.0 : 1.0;
            return new DenormalizedColor(red, green, blue, 1.0);
        });
        this.threasholdRed = threasholdRed;
        this.threasholdGreen = threasholdGreen;
        this.threasholdBlue = threasholdBlue;
    }

    @Override
    public String getDescription() {
        return "Multi channel Binary with threshold:" + Utils.roundToRearestFraction(threasholdRed,0.01)
                + " " + Utils.roundToRearestFraction(threasholdGreen,0.01)
                + " " + Utils.roundToRearestFraction(threasholdBlue,0.01);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MultiChannelBinaryTransformation that = (MultiChannelBinaryTransformation) o;
        return Double.compare(that.threasholdRed, threasholdRed) == 0 &&
                Double.compare(that.threasholdGreen, threasholdGreen) == 0 &&
                Double.compare(that.threasholdBlue, threasholdBlue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), threasholdRed, threasholdGreen, threasholdBlue);
    }
}
