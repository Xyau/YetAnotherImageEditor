package transformations.normal.umbrals;

import backend.DenormalizedColor;
import backend.utils.ColorUtils;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.Objects;


public class SingleChannelBinaryTransformation extends PixelByPixelTransformation {
    double threshold;

    public SingleChannelBinaryTransformation(double threshold) {
        super(c -> {
            Boolean pass = ColorUtils.getBrightness(c)>=threshold;
            return new DenormalizedColor(pass?1.0:0,pass?1.0:0,pass?1.0:0,c.getAlpha());
        });
        this.threshold = threshold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SingleChannelBinaryTransformation that = (SingleChannelBinaryTransformation) o;
        return Double.compare(that.threshold, threshold) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), threshold);
    }

    @Override
    public String getDescription() {
        return "Single Channel Binary with threshold:" + threshold;
    }
}
