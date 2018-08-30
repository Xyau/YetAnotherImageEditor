package transformations;

import repositories.FiltersRepository;

public class VerticalBordersTransformation extends WeighedMeanFilterTranformation {
    public VerticalBordersTransformation() {
        super(FiltersRepository.VERTICAL);
    }
}
