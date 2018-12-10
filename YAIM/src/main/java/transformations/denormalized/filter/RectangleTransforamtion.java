package transformations.denormalized.filter;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.combiners.Combiner;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.DenormalizedTransformation;
import backend.transformators.FullTransformation;
import backend.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RectangleTransforamtion implements FullTransformation {
    DenormalizedImage integral;

    public RectangleTransforamtion(Double shiftDelta, Double scaleDelta) {

    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {


        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
