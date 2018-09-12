package transformations.normal.filters;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;

public class HighpassFilterTransformation extends WindowMeanTransformation {
    public HighpassFilterTransformation() {
        super(FiltersRepository.HIGHPASS);
    }
}
