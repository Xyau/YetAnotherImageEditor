package transformations.normal.filters;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;


public class MeanFilterTransformation extends WindowMeanTransformation {
    private Integer filterSize;
    public MeanFilterTransformation(Integer filterSize) {
        super(FiltersRepository.getOnesFilter(filterSize));
        this.filterSize = filterSize;
    }

    @Override
    public String getDescription() {
        return "Mean filter size:" + filterSize;
    }
}
