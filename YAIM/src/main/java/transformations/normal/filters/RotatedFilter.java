package transformations.normal.filters;

import backend.Filter;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;

import java.util.Objects;

public class RotatedFilter implements FullTransformation {
    private Filter activeFilter;
    private Integer cuarterRotations;
    private WindowMeanTransformation windowMeanOperator;

    public RotatedFilter(Filter filter, Integer cuarterRotations){
        this.cuarterRotations = cuarterRotations;
        activeFilter = new Filter(FiltersRepository.getRotatedFilter(filter.getFilter(),cuarterRotations),filter.toString());
        this.windowMeanOperator = new WindowMeanTransformation(activeFilter.getFilter());
    }

    public RotatedFilter(Double[][] filter, Integer cuarterRotations) {
        this(new Filter(filter,"Unknown"),cuarterRotations);
    }

    @Override
    public String getDescription() {
        return " filter rotated " + cuarterRotations + " cuarters";
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        return windowMeanOperator.transformDenormalized(denormalizedImage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RotatedFilter that = (RotatedFilter) o;
        return Objects.equals(windowMeanOperator, that.windowMeanOperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowMeanOperator);
    }
}
