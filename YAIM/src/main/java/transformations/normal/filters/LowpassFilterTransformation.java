package transformations.normal.filters;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;

public class LowpassFilterTransformation extends WindowMeanTransformation {
    public LowpassFilterTransformation() {
        super(FiltersRepository.LOWPASS);
    }

    @Override
    public String getDescription() {
        return "Lowpass Filter";
    }
}
