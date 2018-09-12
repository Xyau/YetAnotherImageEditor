package transformations.normal.filters;

import backend.combiners.MedianCombiner;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;

import java.util.Objects;

public class MedianFilterTransformation extends WindowOperator implements FullTransformation {
    Integer filterSize;

    public MedianFilterTransformation(Integer filterSize) {
        super(FiltersRepository.getOnesFilter(filterSize), new MedianCombiner());
        this.filterSize = filterSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedianFilterTransformation that = (MedianFilterTransformation) o;
        return Objects.equals(filterSize, that.filterSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterSize);
    }

    @Override
    public String getDescription() {
        return "Median Filter. Size:" + filterSize ;
    }
}
