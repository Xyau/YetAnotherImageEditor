package transformations.normal.filters;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;

public class HighpassFilterTransformation extends WindowMeanOperator {
    public HighpassFilterTransformation() {
        super(FiltersRepository.HIGHPASS);
    }
}
