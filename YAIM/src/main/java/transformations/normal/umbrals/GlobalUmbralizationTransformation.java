package transformations.normal.umbrals;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.ImageUtils;

import java.util.Objects;


public class GlobalUmbralizationTransformation implements FullTransformation {
    private Double threashold = 0.0;

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        Double threshold = ImageUtils.getAverageBrightness(denormalizedImage);

        DenormalizedImage umbralized = denormalizedImage;
        Double prevThreshold = Double.MAX_VALUE;
        while (Math.abs(prevThreshold-threshold) > 0.01){
            umbralized = new SingleChannelBinaryTransformation(threshold)
                    .transformDenormalized(new DenormalizedImage(denormalizedImage));

            Double whitePixelAvgBrightness = 0.0;
            Integer whitePixelCount = 0;
            Double blackPixelAvgBrightness = 0.0;
            Integer blackPixelCount = 0;

            for (int j = 0; j < denormalizedImage.getHeight(); j++) {
                for (int i = 0; i < denormalizedImage.getWidth(); i++) {
                    Double brightness = ColorUtils.getBrightness(umbralized.getColorAt(i,j));
                    if(brightness.equals(1.0)){
                        whitePixelCount++;
                        whitePixelAvgBrightness+=ColorUtils.getBrightness(denormalizedImage.getColorAt(i,j));
                    } else if(brightness.equals(0.0)){
                        blackPixelCount++;
                        blackPixelAvgBrightness+=ColorUtils.getBrightness(denormalizedImage.getColorAt(i,j));
                    }
                }
            }

            whitePixelAvgBrightness = whitePixelAvgBrightness/whitePixelCount;
            blackPixelAvgBrightness = blackPixelAvgBrightness/blackPixelCount;

            prevThreshold = threshold;
            threshold = 0.5*(whitePixelAvgBrightness+blackPixelAvgBrightness);
        }
        this.threashold = threshold;
        return umbralized;
    }

    @Override
    public String getDescription() {
        return "Global Binary: " + this.threashold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass());
    }
}
