package transformations.normal.borders;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanOperator;

public class LaplacianBordersTransformation extends WindowMeanOperator {

    public LaplacianBordersTransformation() {
        super(FiltersRepository.LAPLACIAN);
    }

    @Override
    public String getDescription() {
        return "Laplacian border";
    }
}
