package transformations.normal.filters;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;

public class LowpassFilterTransformation extends WindowMeanOperator {
    public LowpassFilterTransformation() {
        super(FiltersRepository.LOWPASS);
    }

    @Override
    public String getDescription() {
        return "Lowpass Filter";
    }
}
