package transformations.normal.canny;

import backend.DenormalizedColor;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import transformations.normal.borders.SobelBorderTransformation;

public class NonMaximalBorderSupressionTransformation implements FullTransformation {

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedImage angles = new OrthogonalAngleDirectionTransformation().transformDenormalized(denormalizedImage);
        DenormalizedImage intensities = new SobelBorderTransformation().transformDenormalized(denormalizedImage);

        for (int y = 0; y < angles.getHeight(); y++) {
            for (int x = 0; x < angles.getWidth(); x++) {
                Double a = angles.getColorAt(x, y).getRed();
                Double i = intensities.getColorAt(x, y).getRed();

                Double nextIntensity = Double.NEGATIVE_INFINITY;
                Double prevIntensity = Double.NEGATIVE_INFINITY;
                a += 90.0;
                if ((a >= 0.0 && a < 22.5) || (a > 157.5 && a <= 180.0)) {
                    if (x + 1 < intensities.getWidth())
                        nextIntensity = intensities.getColorAt(x + 1, y).getRed();
                    if (x - 1 > 0)
                        prevIntensity = intensities.getColorAt(x - 1, y).getRed();
                } else if (a >= 22.5 && a < 67.5) {
                    if (x - 1 > 0 && y + 1 < intensities.getHeight())
                        nextIntensity = intensities.getColorAt(x - 1, y + 1).getRed();
                    if (x + 1 < intensities.getHeight() && y - 1 > 0)
                        prevIntensity = intensities.getColorAt(x + 1, y - 1).getRed();
                } else if (a >= 67.5 && a < 112.5) {
                    if (y + 1 < intensities.getHeight())
                        nextIntensity = intensities.getColorAt(x, y + 1).getRed();
                    if (y - 1 > 0)
                        prevIntensity = intensities.getColorAt(x, y - 1).getRed();
                } else if (a >= 112.5 && a <= 157.5) {
                    if (x + 1 < intensities.getWidth() && y + 1 < intensities.getHeight())
                        nextIntensity = intensities.getColorAt(x + 1, y + 1).getRed();
                    if (x - 1 > 0 && y - 1 > 0)
                        prevIntensity = intensities.getColorAt(x - 1, y - 1).getRed();
                } else {
                    throw new IllegalStateException("Incorrect angle: " + a);
                }

                if (i <= prevIntensity || i <= nextIntensity)
                    intensities.setColor(x, y, new DenormalizedColor(0.0, 0.0,0.0,1.0));
            }
        }

        return intensities;
    }

    @Override
    public String getDescription () {
        return "Angle Transformation";
    }

}
