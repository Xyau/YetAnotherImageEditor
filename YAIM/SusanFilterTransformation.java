package transformations.normal.filters;

import backend.combiners.MedianCombiner;
import backend.combiners.SusanCombiner;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;

import java.util.Objects;

public class SusanFilterTransformation extends WindowOperator implements FullTransformation {
    Integer filterSize;


    public SusanFilterTransformation() { //Integer filterSize
        super(FiltersRepository.SUSAN, new SusanCombiner());
        //this.filterSize = filterSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SusanFilterTransformation that = (SusanFilterTransformation) o;
        return Objects.equals(filterSize, that.filterSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filterSize);
    }

    @Override
    public String getDescription() {
        return "Susan Filter. Size:" + filterSize ;
    }
}
