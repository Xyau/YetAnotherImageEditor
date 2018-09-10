package tests;

import backend.DenormalizedColor;
import backend.DenormalizedColorPixel;
import backend.combiners.Combiner;
import backend.combiners.MeanCombiner;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import repositories.FiltersRepository;

import java.util.Arrays;
import java.util.List;

public class CombinerTest {
	DenormalizedColor BLACK = new DenormalizedColor(0.0,0.0,0.0,1.0);
	DenormalizedColor WHITE = new DenormalizedColor(1.0,1.0,1.0,1.0);

	@Test
	public void testMeanCombiner(){
		Combiner combiner = new MeanCombiner();
		List<DenormalizedColorPixel> colorPixels = Arrays.asList(new DenormalizedColorPixel(0,0,BLACK),
				new DenormalizedColorPixel(0,1,BLACK),
				new DenormalizedColorPixel(0,2,BLACK),
				new DenormalizedColorPixel(1,0,BLACK),
				new DenormalizedColorPixel(1,1,BLACK),
				new DenormalizedColorPixel(1,2,WHITE),
				new DenormalizedColorPixel(2,0,BLACK),
				new DenormalizedColorPixel(2,1,BLACK),
				new DenormalizedColorPixel(2,2,BLACK));
		Assert.assertThat(combiner.combine(colorPixels,FiltersRepository.LOWPASS),CoreMatchers.equalTo(new DenormalizedColor(1.0/9,1.0/9,1.0/9,1.0)));
	}

	@Test
	public void testMeanCombinerComplex(){
		Combiner combiner = new MeanCombiner();
		List<DenormalizedColorPixel> colorPixels = Arrays.asList(new DenormalizedColorPixel(0,0,BLACK),
				new DenormalizedColorPixel(0,1,BLACK),
				new DenormalizedColorPixel(0,2,BLACK),
				new DenormalizedColorPixel(1,0,BLACK),
				new DenormalizedColorPixel(1,1,WHITE),
				new DenormalizedColorPixel(1,2,WHITE),
				new DenormalizedColorPixel(2,0,BLACK),
				new DenormalizedColorPixel(2,1,WHITE),
				new DenormalizedColorPixel(2,2,WHITE));
		Assert.assertThat(combiner.combine(colorPixels,FiltersRepository.WEIGHTED_3x3),CoreMatchers.equalTo(new DenormalizedColor(9.0/16,9.0/16,9.0/16,1.0)));
	}
}
