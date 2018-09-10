package transformations.normal.filters;


import backend.utils.Utils;
import transformations.denormalized.filter.WindowMeanOperator;

import java.util.Objects;

public class GaussianMeanFilterTransformation extends WindowMeanOperator {
    private Double std;
    private Integer filterSize;

    public GaussianMeanFilterTransformation(Integer filterSize, Double std) {
        super(Utils.getGaussianMatrixWeight(std,filterSize));
        this.filterSize = filterSize;
        this.std = std;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GaussianMeanFilterTransformation that = (GaussianMeanFilterTransformation) o;
        return Objects.equals(std, that.std) && Objects.equals(filterSize,that.filterSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), std, filterSize);
    }

    @Override
    public String getDescription() {
        return "Gaussian Mean size:" + filterSize + " std:" + std;
    }
}
