package transformations.denormalized.filter;

import backend.combiners.MeanCombiner;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;

import java.util.Objects;

public class WindowMeanTransformation implements FullTransformation {
	private WindowOperator windowOperator;

	public WindowMeanTransformation(Double[][] filter) {
		this.windowOperator = new WindowOperator(filter,new MeanCombiner());
	}

	@Override
	public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
		return windowOperator.transformDenormalized(denormalizedImage);
	}

	@Override
	public String getDescription() {
		return "Custom Filter transformation";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WindowMeanTransformation that = (WindowMeanTransformation) o;
		return Objects.equals(windowOperator, that.windowOperator);
	}

	@Override
	public int hashCode() {
		return Objects.hash(windowOperator);
	}
}
