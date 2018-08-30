package transformations;

import repositories.FiltersRepository;

public class LowpassFilterTransformation extends WeighedMeanFilterTranformation {
    public LowpassFilterTransformation() {
        super(FiltersRepository.LOWPASS);
    }
}
