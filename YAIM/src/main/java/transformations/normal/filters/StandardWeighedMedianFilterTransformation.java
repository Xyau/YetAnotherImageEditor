package transformations.normal.filters;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;

public class StandardWeighedMedianFilterTransformation extends WindowMeanOperator {
    public StandardWeighedMedianFilterTransformation() {
        super(FiltersRepository.WEIGHTED_3x3);
    }
}
