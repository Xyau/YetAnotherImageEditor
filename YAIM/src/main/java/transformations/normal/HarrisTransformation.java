package transformations.normal;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.TriFunction;
import repositories.FiltersRepository;
import transformations.denormalized.ImageOperator;
import transformations.denormalized.TriImageOperator;
import transformations.denormalized.filter.WindowMeanTransformation;
import transformations.normal.colors.ScalarMultiplyTransformation;
import transformations.normal.common.PixelByPixelTransformation;
import transformations.normal.filters.GaussianMeanFilterTransformation;
import transformations.normal.image.HighlightRedImageTransformation;
import transformations.normal.image.ModulusImageTransformation;
import transformations.normal.image.MultiplyImageTransformation;
import transformations.normal.umbrals.GlobalUmbralizationTransformation;

public class HarrisTransformation implements FullTransformation {

    private Double k = 0.04;
    private Double eps = 0.02;
    private TriFunction<Double, Double> cm1 = (ly2,lx2,lxy) -> (lx2*ly2 - lxy*lxy) - k*Math.pow(lx2+ly2,2);
    private TriFunction<Double, Double> cm2 = (ly2,lx2,lxy) -> (lx2*ly2 - lxy*lxy) / (lx2+ly2+eps);
    private TriFunction<Double, Double> cm3 = (ly2,lx2,lxy) -> (lx2*ly2 - lxy*lxy*lxy*lxy) - k*Math.pow(lx2+ly2,2);

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedImage res =
                new ScalarMultiplyTransformation(1.0,0.0,0.0).transformDenormalized(
                new GlobalUmbralizationTransformation().transformDenormalized(
                        new HarrisOnlyFeaturesTransformation().transformDenormalized(denormalizedImage)
                ));
        return new HighlightRedImageTransformation(res).transformDenormalized(denormalizedImage);
    }

    @Override
    public String getDescription() {
        return "Harris Transformation";
    }
}
