package transformations.normal.filters;

import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;

public class LaplacianFilterTransformation extends WindowMeanTransformation {

    public LaplacianFilterTransformation() {
        super(FiltersRepository.LAPLACIAN);
    }

    @Override
    public String getDescription() {
        return "Laplacian border";
    }
}
