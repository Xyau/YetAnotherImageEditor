package transformations.normal.canny;

import backend.combiners.multicombiner.NonMaximalBorderSupressionCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import repositories.FiltersRepository;
import transformations.denormalized.filter.MultiWindowOperator;
import transformations.normal.borders.SobelBorderTransformation;

public class NonMaximalBorderSupressionTransformation extends MultiWindowOperator implements FullTransformation {

    public NonMaximalBorderSupressionTransformation() {
        super(FiltersRepository.getOnesFilter(1), new NonMaximalBorderSupressionCombiner(), null);
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        DenormalizedImage angles = new OrthogonalAngleDirectionTransformation().transformDenormalized(new DenormalizedImage(denormalizedImage));
        DenormalizedImage intensities = new SobelBorderTransformation().transformDenormalized(denormalizedImage);
        extraImage = angles; // TODO: CHANGE

        return super.transformDenormalized(intensities);
    }

    @Override
    public String getDescription () {
        return "Angle Transformation";
    }

}
