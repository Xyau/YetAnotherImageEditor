package transformations.filters;

import repositories.FiltersRepository;
import transformations.filters.WeighedMeanFilterTranformation;

public class HighpassFilterTransformation extends WeighedMeanFilterTranformation {
    public HighpassFilterTransformation() {
        super(FiltersRepository.HIGHPASS);
    }
}
