package transformations.normal.filters;


import backend.Filter;
import backend.utils.Utils;
import javafx.stage.FileChooser;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;

import java.io.FilterReader;
import java.util.Objects;

public class GaussianMeanFilterTransformation extends WindowMeanTransformation {
    private Double std;
    private Integer filterSize;

    public GaussianMeanFilterTransformation(Double std) {
        this(Utils.toInteger(2*std+1),std);
    }
    public GaussianMeanFilterTransformation(Integer filterSize, Double std) {
        super(FiltersRepository.getGaussianMatrixWeight(std,filterSize));
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
