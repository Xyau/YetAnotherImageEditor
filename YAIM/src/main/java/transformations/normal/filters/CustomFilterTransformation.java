package transformations.normal.filters;

import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;

import java.util.Objects;

public class CustomFilterTransformation implements FullTransformation {
    private Double[][] activeFilter;
    private Integer cuarterRotations;
    private WindowMeanTransformation windowMeanOperator;

    public CustomFilterTransformation(Double[][] filter, Integer cuarterRotations) {
        this.cuarterRotations = cuarterRotations;
        activeFilter = FiltersRepository.getRotatedFilter(filter,cuarterRotations);
        this.windowMeanOperator = new WindowMeanTransformation(activeFilter);
    }

    @Override
    public String getDescription() {
        return "Custom Hirsch filter rotated " + cuarterRotations + " cuarters";
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        return windowMeanOperator.transformDenormalized(denormalizedImage);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomFilterTransformation that = (CustomFilterTransformation) o;
        return Objects.equals(windowMeanOperator, that.windowMeanOperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowMeanOperator);
    }
}
