package transformations.normal.umbrals;

import backend.DenormalizedColor;
import backend.utils.Utils;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.Objects;
import java.util.function.Function;


public class MultiChannelTernaryTransformation extends PixelByPixelTransformation {
    private double minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue;

    public MultiChannelTernaryTransformation(double min, double max) {
        this(min, max, min, max, min, max);
    }

    public MultiChannelTernaryTransformation(double minRed, double maxRed,
                                             double minGreen, double maxGreen,
                                             double minBlue, double maxBlue) {
        super(c -> {
            double red = c.getRed() < minRed ? 0.0 : c.getRed() > maxRed ? 1.0 : c.getRed();
            double green = c.getGreen() < minGreen ? 0.0 : c.getGreen() > maxGreen ? 1.0 : c.getGreen();
            double blue = c.getBlue() < minBlue ? 0.0 : c.getBlue() > maxBlue ? 1.0 : c.getBlue();
            return new DenormalizedColor(red, green, blue, 1.0);
        });
        this.minRed = minRed;
        this.maxRed = maxRed;
        this.minGreen = minGreen;
        this.maxGreen = maxGreen;
        this.minBlue = minBlue;
        this.maxBlue = maxBlue;
    }

    @Override
    public String getDescription() {
        if(minBlue == minGreen && minBlue == minRed && maxBlue == maxGreen && maxBlue == maxRed){
            return "Multi channel ternary with thresholds:"
                    + "R:(" + Utils.roundToRearestFraction(minRed,0.01)
                    + ", "+ Utils.roundToRearestFraction(maxRed,0.01)
                    + ") G:(" + Utils.roundToRearestFraction(minGreen,0.01)
                    + ", "+ Utils.roundToRearestFraction(maxGreen,0.01)
                    + ") B:(" + Utils.roundToRearestFraction(minBlue,0.01)
                    + ", "+ Utils.roundToRearestFraction(maxBlue,0.01) + ")";
        } else {
            return "Multi channel Binary with threshold:"
                    + "min:" + Utils.roundToRearestFraction(minRed,0.01)
                    + " max:"+ Utils.roundToRearestFraction(maxRed,0.01);

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MultiChannelTernaryTransformation that = (MultiChannelTernaryTransformation) o;
        return Double.compare(that.minRed, minRed) == 0 &&
                Double.compare(that.maxRed, maxRed) == 0 &&
                Double.compare(that.minGreen, minGreen) == 0 &&
                Double.compare(that.maxGreen, maxGreen) == 0 &&
                Double.compare(that.minBlue, minBlue) == 0 &&
                Double.compare(that.maxBlue, maxBlue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minRed, maxRed, minGreen, maxGreen, minBlue, maxBlue);
    }
}
