package transformations.borders;

import repositories.FiltersRepository;
import transformations.filters.WeighedMeanFilterTranformation;

public class VerticalPrewittBordersTransformation extends WeighedMeanFilterTranformation {
    public VerticalPrewittBordersTransformation() {
        super(FiltersRepository.PREWITT_HORIZONTAL);
    }
}
