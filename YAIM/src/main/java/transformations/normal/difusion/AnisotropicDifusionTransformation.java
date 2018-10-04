package transformations.normal.difusion;

import backend.combiners.Combiner;
import backend.combiners.TropicCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.utils.Utils;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;

public class AnisotropicDifusionTransformation implements FullTransformation {
    private Double std;
    private Integer iterations;
    private WindowOperator anisotropicOperator;

    public AnisotropicDifusionTransformation(Double std, Integer iterations) {
        this.iterations = iterations;
        this.std = std;
        anisotropicOperator = new WindowOperator(FiltersRepository.getOnesFilter(1),new TropicCombiner(std));
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
        return "Anisotropic Difusion STD:"+ Utils.roundToRearestFraction(std,0.01) + " Rounds:" + iterations;
    }
}
