package transformations.normal.difusion;

import backend.combiners.TropicCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.Utils;
import repositories.FiltersRepository;
import repositories.FunctionsRepository;
import transformations.denormalized.filter.WindowOperator;

import java.util.Objects;

public class IsotropicDifusionTransformation implements FullTransformation {
    private Double std;
    private Integer iterations;
    private WindowOperator anisotropicOperator;


    public IsotropicDifusionTransformation(Double std, Integer iterations) {
        this.iterations = iterations;
        this.std = std;
        anisotropicOperator = new WindowOperator(FiltersRepository.getOnesFilter(1),
                new TropicCombiner(std,(delta, lambda) -> 1.0));
    }


    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        for (int i = 0; i < iterations; i++) {
            denormalizedImage = anisotropicOperator.transformDenormalized(denormalizedImage);
        }
        return denormalizedImage;
    }


    @Override
    public String getDescription() {
        return "Isotropic Difusion STD:"+ Utils.roundToRearestFraction(std,0.01) + " Rounds:" + iterations;
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
}
