package transformations.normal.common;

import backend.DenormalizedColor;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import javafx.scene.image.WritableImage;
import transformations.normalizers.MultiChannelRangeNormalizer;

import java.util.Objects;
import java.util.function.Function;

public class PixelByPixelTransformation implements FullTransformation {
    private Function<DenormalizedColor, DenormalizedColor> colorTransformer;

    public PixelByPixelTransformation(Function<DenormalizedColor, DenormalizedColor> colorTransformer) {
        this.colorTransformer = colorTransformer;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        return FullTransformation.transform(writableImage, this,new MultiChannelRangeNormalizer());
    }

    @Override
    public String getDescription() {
        return "Base pixel by pixel transformation";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixelByPixelTransformation that = (PixelByPixelTransformation) o;
        return Objects.equals(colorTransformer, that.colorTransformer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorTransformer);
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        for (int i = 0; i < denormalizedImage.getWidth(); i++) {
            for (int j = 0; j < denormalizedImage.getHeight(); j++) {
                DenormalizedColor c = colorTransformer.apply(denormalizedImage.getColorAt(i,j));
                denormalizedImage.setColor(i,j,c);
            }
        }
        return denormalizedImage;
    }
}
