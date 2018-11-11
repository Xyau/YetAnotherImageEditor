package transformations.normal;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.ColorUtils;
import backend.utils.TriFunction;
import repositories.FiltersRepository;
import transformations.denormalized.TriImageOperator;
import transformations.denormalized.filter.WindowMeanTransformation;
import transformations.normal.colors.ScalarMultiplyTransformation;
import transformations.normal.common.PixelByPixelTransformation;
import transformations.normal.filters.GaussianMeanFilterTransformation;
import transformations.normal.image.HighlightRedImageTransformation;
import transformations.normal.image.MultiplyImageTransformation;
import transformations.normal.umbrals.GlobalUmbralizationTransformation;

public class HarrisOnlyFeaturesTransformation implements FullTransformation {

    private Double k = 0.04;
    private Double eps = 0.02;
    private TriFunction<Double, Double> cm1 = (ly2,lx2,lxy) -> (lx2*ly2 - lxy*lxy) - k*Math.pow(lx2+ly2,2);
    private TriFunction<Double, Double> cm2 = (ly2,lx2,lxy) -> (lx2*ly2 - lxy*lxy) / (lx2+ly2+eps);
    private TriFunction<Double, Double> cm3 = (ly2,lx2,lxy) -> (lx2*ly2 - lxy*lxy*lxy*lxy) - k*Math.pow(lx2+ly2,2);

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        FullTransformation gaussian = new GaussianMeanFilterTransformation(2,1.0);
        FullTransformation squared = new PixelByPixelTransformation(ColorUtils::squareColor);
        DenormalizedImage lx = new WindowMeanTransformation(FiltersRepository.SOBEL_HORIZONTAL)
                .transformDenormalized(new DenormalizedImage(denormalizedImage));
        DenormalizedImage ly = new WindowMeanTransformation(FiltersRepository.SOBEL_VERTICAL)
                .transformDenormalized(denormalizedImage);

        DenormalizedImage lx2 = gaussian.transformDenormalized(squared.transformDenormalized(lx));
        DenormalizedImage ly2 = gaussian.transformDenormalized(squared.transformDenormalized(ly));
        DenormalizedImage lxy = gaussian.transformDenormalized(new MultiplyImageTransformation(lx2).transformDenormalized(new DenormalizedImage(ly2)));

        TriFunction<Double, Double> cm = cm1;

        return new ScalarMultiplyTransformation(1.0,0.0,0.0).transformDenormalized(
                new NegativeTransformation().transformDenormalized(new TriImageOperator(lx2,lxy, (c1, c2, c3)->
                ColorUtils.combineColors(c1,c2,c3,cm))
                .transformDenormalized(ly2)));
    }

    @Override
    public String getDescription() {
        return "Harris Only Features Transformation";
    }
}
