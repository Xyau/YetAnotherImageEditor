package transformations;

import repositories.FiltersRepository;

public class StandardWeighedMedianFilterTransformation extends WeighedMedianFilterTransformation {
    public StandardWeighedMedianFilterTransformation() {
        super(FiltersRepository.WEIGHTED_3x3);
    }
}
