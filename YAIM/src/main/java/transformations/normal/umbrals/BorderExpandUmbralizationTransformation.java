package transformations.normal.umbrals;

import backend.DenormalizedColor;
import backend.combiners.MaxCombiner;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;

public class BorderExpandUmbralizationTransformation extends WindowOperator implements FullTransformation {
    public BorderExpandUmbralizationTransformation() {
        super(FiltersRepository.getOnesFilter(1), new MaxCombiner(DenormalizedColor.WHITE));
    }

    @Override
    public String getDescription() {
        return "Expand the maximum value";
    }
}
