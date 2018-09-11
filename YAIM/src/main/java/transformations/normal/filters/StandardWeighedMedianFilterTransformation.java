package transformations.normal.filters;

import backend.combiners.MedianCombiner;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;
import transformations.denormalized.filter.WindowOperator;

public class StandardWeighedMedianFilterTransformation extends WindowOperator implements FullTransformation {
    public StandardWeighedMedianFilterTransformation() {
        super(FiltersRepository.WEIGHTED_3x3,new MedianCombiner());
    }


    @Override
    public String getDescription() {
        return "Standard Weighed Median Filter";
    }
}
