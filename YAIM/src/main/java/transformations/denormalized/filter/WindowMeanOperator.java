package transformations.denormalized.filter;

import backend.combiners.Combiner;
import backend.combiners.MeanCombiner;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;

import java.util.Objects;

public class WindowMeanOperator implements FullTransformation {
	private WindowOperator windowOperator;

	public WindowMeanOperator(Double[][] filter) {
		this.windowOperator = new WindowOperator(filter,new MeanCombiner());
	}

	@Override
	public DenormalizedImage transformDenormalized(AnormalizedImage anormalizedImage) {
		return windowOperator.transformDenormalized(anormalizedImage);
	}

	@Override
	public String getDescription() {
		return "Window Mean operator";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WindowMeanOperator that = (WindowMeanOperator) o;
		return Objects.equals(windowOperator, that.windowOperator);
	}

	@Override
	public int hashCode() {

		return Objects.hash(windowOperator);
	}
}
