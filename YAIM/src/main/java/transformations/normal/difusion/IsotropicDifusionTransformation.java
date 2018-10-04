package transformations.normal.difusion;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.Utils;
import transformations.normal.filters.GaussianMeanFilterTransformation;

import java.util.Objects;

public class IsotropicDifusionTransformation implements FullTransformation {
    private Double std;
    private Integer iterations;
    private FullTransformation operator;


    public IsotropicDifusionTransformation(Double std, Integer iterations) {
        this.iterations = iterations;
        this.std = std;

        operator = new GaussianMeanFilterTransformation(std);
    }


    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
//        denormalizedImage = new LaplacianFilterTransformation().transformDenormalized(denormalizedImage);
        for (int i = 0; i < iterations; i++) {
            denormalizedImage = operator.transformDenormalized(denormalizedImage);
        }
        return denormalizedImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsotropicDifusionTransformation that = (IsotropicDifusionTransformation) o;
        return Objects.equals(std, that.std) &&
                Objects.equals(iterations, that.iterations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(std, iterations);
    }

    @Override
    public String getDescription() {
        return "Isotropic Difusion (Gauss) STD:"+ Utils.roundToRearestFraction(std,0.01) + " Rounds:" + iterations;
    }
}
