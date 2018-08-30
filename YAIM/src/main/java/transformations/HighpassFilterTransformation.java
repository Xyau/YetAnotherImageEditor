package transformations;

import repositories.FiltersRepository;

import java.io.FilterReader;

public class HighpassFilterTransformation extends WeighedMeanFilterTranformation {
    public HighpassFilterTransformation() {
        super(FiltersRepository.HIGHPASS);
    }
}
