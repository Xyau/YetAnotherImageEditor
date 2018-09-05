package transformations.borders;

import repositories.FiltersRepository;
import transformations.filters.WeighedMeanFilterTranformation;

public class HorizontalSobelBordersTransformation extends WeighedMeanFilterTranformation {
    public HorizontalSobelBordersTransformation() {
        super(FiltersRepository.SOBEL_HORIZONTAL);
    }
}
