package transformations.normal.difusion;

import backend.combiners.Combiner;
import backend.combiners.TropicCombiner;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;

public class AnisotropicDifusionTransformation extends WindowOperator implements FullTransformation {
    private Double std;

    public AnisotropicDifusionTransformation(Double std) {
        super(FiltersRepository.getOnesFilter(1), new TropicCombiner(std));
        this.std = std;
    }

    @Override
    public String getDescription() {
        return "Anisotropic Difusion STD:"+std;
    }
}
