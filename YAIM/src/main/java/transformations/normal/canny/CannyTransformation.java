package transformations.normal.canny;

import backend.image.DenormalizedImage;
import backend.transformators.Transformation;
import backend.utils.ImageUtils;
import backend.utils.Utils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import transformations.normal.filters.GaussianMeanFilterTransformation;


/**
 * Created by zion on 2018-08-25.
 */
public class CannyTransformation implements Transformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        DenormalizedImage first = new GaussianMeanFilterTransformation(1, 1.0).transformDenormalized(new DenormalizedImage(writableImage));
        DenormalizedImage second = new GaussianMeanFilterTransformation(2, 2.0).transformDenormalized(new DenormalizedImage(writableImage));

        return writableImage;
    }

    @Override
    public String getDescription () {
        return "Canny Transformation";
    }
}