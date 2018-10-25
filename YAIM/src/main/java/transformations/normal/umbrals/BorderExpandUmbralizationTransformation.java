package transformations.normal.umbrals;

import backend.DenormalizedColor;
import backend.combiners.FillConnectivity;
import backend.combiners.MaxCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;

public class BorderExpandUmbralizationTransformation extends WindowOperator implements FullTransformation {
    public BorderExpandUmbralizationTransformation() {
        super(FiltersRepository.getOnesFilter(1), new FillConnectivity(DenormalizedColor.WHITE, DenormalizedColor.BLACK, 4));
    }

    @Override
    public String getDescription() {
        return "Expand the maximum value";
    }
}
