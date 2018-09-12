package transformations.normal.umbrals;

import backend.DenormalizedColor;
import backend.utils.ColorUtils;
import backend.utils.Utils;
import javafx.scene.paint.Color;
import transformations.normal.common.PixelByPixelTransformation;

import java.util.Objects;


public class SingleChannelBinaryTransformation extends PixelByPixelTransformation {
    double threashold;

    public SingleChannelBinaryTransformation(double threashold) {
        super(c -> {
            Boolean pass = ColorUtils.getBrightness(c)>=threashold;
            return new DenormalizedColor(pass?1.0:0,pass?1.0:0,pass?1.0:0,c.getAlpha());
        });
        this.threashold = threashold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SingleChannelBinaryTransformation that = (SingleChannelBinaryTransformation) o;
        return Double.compare(that.threashold, threashold) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), threashold);
    }

    @Override
    public String getDescription() {
        return "Single Channel Binary with threshold:" + threashold;
    }
}
