package transformations.borders;

import repositories.FiltersRepository;
import transformations.filters.WeighedMeanFilterTranformation;

public class VerticalSobelBordersTransformation extends WeighedMeanFilterTranformation {
    public VerticalSobelBordersTransformation() {
        super(FiltersRepository.SOBEL_VERTICAL);
    }
}
