package transformations;

import repositories.FiltersRepository;

public class HorizontalBordersTransformation extends WeighedMeanFilterTranformation {
    public HorizontalBordersTransformation() {
        super(FiltersRepository.HORIZONTAL);
    }
}
