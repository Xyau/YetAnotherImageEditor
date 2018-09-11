package tests;

import backend.combiners.MeanCombiner;
import org.junit.jupiter.api.Test;
import repositories.FiltersRepository;
import transformations.denormalized.filter.WindowOperator;

public class WidowOperatorTest {

    @Test
    public void testGetNeighbourPixels(){
        WindowOperator windowOperator = new WindowOperator(FiltersRepository.HORIZONTAL_ZEROES,new MeanCombiner());
    }
}
