package transformations.normal.filters;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;

public class LaplacianFilterTransformation extends WindowMeanOperator {

    public LaplacianFilterTransformation() {
        super(FiltersRepository.LAPLACIAN);
    }

    @Override
    public String getDescription() {
        return "Laplacian border";
    }
}
