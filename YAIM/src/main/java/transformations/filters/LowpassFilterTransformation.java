package transformations.filters;

import repositories.FiltersRepository;
import transformations.filters.WeighedMeanFilterTranformation;

public class LowpassFilterTransformation extends WeighedMeanFilterTranformation {
    public LowpassFilterTransformation() {
        super(FiltersRepository.LOWPASS);
    }
}
