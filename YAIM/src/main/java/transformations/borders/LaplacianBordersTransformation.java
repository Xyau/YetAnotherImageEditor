package transformations.borders;

import repositories.FiltersRepository;
import transformations.filters.WeighedMeanFilterTranformation;

public class LaplacianBordersTransformation extends WeighedMeanFilterTranformation {
    public LaplacianBordersTransformation() {
        super(FiltersRepository.LAPLACIAN);
    }
}
