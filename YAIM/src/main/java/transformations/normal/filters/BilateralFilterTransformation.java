package transformations.normal.filters;

import backend.combiners.BilaterlalCombiner;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowMeanTransformation;
import transformations.denormalized.filter.WindowOperator;

public class BilateralFilterTransformation extends WindowOperator implements FullTransformation {
    private Double colorStd;
    private Double spatialStd;

    public BilateralFilterTransformation(Double colorStd, Double spatialStd) {
        super(FiltersRepository.getOnesFilter(3),new BilaterlalCombiner(colorStd,spatialStd));
        this.colorStd = colorStd;
        this.spatialStd = spatialStd;
    }

    @Override
    public String getDescription() {
        return "Bilateral Transformation, color Std:"+ colorStd + "spatial Std:" + spatialStd;
    }
}
