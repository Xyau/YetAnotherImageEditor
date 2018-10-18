package backend.combiners.multicombiner;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.Filter;
import backend.Pixel;
import backend.utils.ColorUtils;
import backend.utils.CombinerUtils;

import java.util.List;

public class NonMaximalBorderSupressionCombiner implements MultiCombiner {
    @Override
    public DenormalizedColor combineMulti(List<List<DenormalizedColorPixel>> multiColorPixels, Filter filter) {
        List<DenormalizedColorPixel> intensities = multiColorPixels.get(0);
        List<DenormalizedColorPixel> angles = multiColorPixels.get(1);

        DenormalizedColorPixel intensity = CombinerUtils.getCenterPixel(intensities,filter);
        DenormalizedColorPixel angle = CombinerUtils.getCenterPixel(angles,filter);

        return ColorUtils.combineColors(angle.getColor(), intensity.getColor(), (a, i) -> {
            Double nextIntensity;
            Double prevIntensity;
            a += 90.0;
            if ((a >= 0.0 && a < 22.5) || (a > 157.5 && a <= 180.0)) {
                nextIntensity = CombinerUtils.getPixel(intensities, intensity.getPixel().getX() + 1, intensity.getPixel().getY()).getColor().getRed();
                prevIntensity = CombinerUtils.getPixel(intensities, intensity.getPixel().getX() - 1, intensity.getPixel().getY()).getColor().getRed();
                if (i < prevIntensity || i < nextIntensity) return 0.0;
            } else if (a >= 22.5 && a < 67.5) {
                nextIntensity = CombinerUtils.getPixel(intensities, intensity.getPixel().getX()+1, intensity.getPixel().getY()+1).getColor().getRed();
                prevIntensity = CombinerUtils.getPixel(intensities, intensity.getPixel().getX()-1, intensity.getPixel().getY()-1).getColor().getRed();
                if (i < prevIntensity || i < nextIntensity) return 0.0;
            } else if (a >= 67.5 && a < 112.5) {
                nextIntensity = CombinerUtils.getPixel(intensities, intensity.getPixel().getX(), intensity.getPixel().getY() + 1).getColor().getRed();
                prevIntensity = CombinerUtils.getPixel(intensities, intensity.getPixel().getX(), intensity.getPixel().getY() - 1).getColor().getRed();
                if (i < prevIntensity || i < nextIntensity) return 0.0;
            } else if (a >= 112.5 && a < 157.5) {
                 nextIntensity = CombinerUtils.getPixel(intensities, intensity.getPixel().getX() - 1, intensity.getPixel().getY() + 1).getColor().getRed();
                 prevIntensity = CombinerUtils.getPixel(intensities, intensity.getPixel().getX() + 1, intensity.getPixel().getY() - 1).getColor().getRed();
                 if (i < prevIntensity || i < nextIntensity) return 0.0;
             } else {
                throw new IllegalStateException("Incorrect angle: " + a);
            }
          return i;
        });
    }
}
