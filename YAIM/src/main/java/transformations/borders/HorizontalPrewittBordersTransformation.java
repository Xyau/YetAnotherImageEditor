package transformations.borders;

import repositories.FiltersRepository;
import transformations.filters.WeighedMeanFilterTranformation;

public class HorizontalPrewittBordersTransformation extends WeighedMeanFilterTranformation {
    public HorizontalPrewittBordersTransformation() {
        super(FiltersRepository.PREWITT_VERTICAL);
    }
}
