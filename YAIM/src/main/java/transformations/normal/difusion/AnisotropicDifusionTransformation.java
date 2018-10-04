package transformations.normal.difusion;

import backend.combiners.Combiner;
import backend.combiners.TropicCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.Utils;
import repositories.FiltersRepository;
import repositories.FunctionsRepository;
import transformations.denormalized.filter.WindowOperator;
import transformations.normal.filters.LaplacianFilterTransformation;

import java.util.Objects;

public class AnisotropicDifusionTransformation implements FullTransformation {
    private Double std;
    private Integer iterations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnisotropicDifusionTransformation that = (AnisotropicDifusionTransformation) o;
        return Objects.equals(std, that.std) &&
                Objects.equals(iterations, that.iterations);
    }

    @Override
    public int hashCode() {

        return Objects.hash(std, iterations);
    }

    private WindowOperator anisotropicOperator;

    public AnisotropicDifusionTransformation(Double std, Integer iterations) {
        this.iterations = iterations;
        this.std = std;
        anisotropicOperator = new WindowOperator(FiltersRepository.getOnesFilter(1),
                new TropicCombiner(std, FunctionsRepository.LECLERC_DETECTOR));
    }


    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
//        denormalizedImage = new LaplacianFilterTransformation().transformDenormalized(denormalizedImage);
        for (int i = 0; i < iterations; i++) {
            denormalizedImage = anisotropicOperator.transformDenormalized(denormalizedImage);
        }
        return denormalizedImage;
    }


    @Override
    public String getDescription() {
        return "Anisotropic Difusion STD:"+ Utils.roundToRearestFraction(std,0.01) + " Rounds:" + iterations;
    }
}
