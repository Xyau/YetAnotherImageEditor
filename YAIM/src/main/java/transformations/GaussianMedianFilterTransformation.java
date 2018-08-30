package transformations;


import backend.Utils;

import java.util.Objects;

public class GaussianMedianFilterTransformation extends WeighedMedianFilterTransformation {
    private Double std;

    public GaussianMedianFilterTransformation(Integer filterSize, Double std) {
        super(Utils.getGaussianMatrixWeight(std,filterSize));
        this.std = std;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GaussianMedianFilterTransformation that = (GaussianMedianFilterTransformation) o;
        return Objects.equals(std, that.std) && Objects.equals(filterSize,that.filterSize);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), std, filterSize);
    }

    @Override
    public String getDescription() {
        return null;
    }
}
