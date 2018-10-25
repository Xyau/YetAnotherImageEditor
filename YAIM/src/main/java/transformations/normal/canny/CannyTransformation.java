package transformations.normal.canny;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.transformators.Transformation;
import backend.utils.ColorUtils;
import backend.utils.ImageUtils;
import backend.utils.Utils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import transformations.denormalized.ImageOperator;
import transformations.normal.filters.GaussianMeanFilterTransformation;
import transformations.normal.image.MultiplyImageTransformation;
import transformations.normal.umbrals.HisteresisUmbralizationTransformation;
import transformations.normal.umbrals.SingleChannelBinaryTransformation;


/**
 * Created by zion on 2018-08-25.
 */
public class CannyTransformation implements FullTransformation {

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedImage first = new GaussianMeanFilterTransformation(1, 1.0).transformDenormalized(new DenormalizedImage(denormalizedImage));
        DenormalizedImage second = new GaussianMeanFilterTransformation(2, 2.0).transformDenormalized(new DenormalizedImage(denormalizedImage));

        first = new NonMaximalBorderSupressionTransformation().transformDenormalized(first);
        second = new NonMaximalBorderSupressionTransformation().transformDenormalized(second);

        first = new HisteresisUmbralizationTransformation(0.1, 0.3).transformDenormalized(first);
        second= new HisteresisUmbralizationTransformation(0.1, 0.3).transformDenormalized(second);

        return new ImageOperator(second, ColorUtils::addClampColors).transformDenormalized(first);
    }



    @Override
    public String getDescription () {
        return "Canny Transformation";
    }

}