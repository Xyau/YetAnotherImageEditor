package tests;

import backend.utils.Utils;
import org.junit.jupiter.api.Test;
import repositories.FiltersRepository;

public class FiltersTest {
    @Test
    public void testRotation(){
        Double[][] filter = FiltersRepository.KIRSH;
        Utils.printFilter(filter);
        Utils.printFilter(FiltersRepository.getRotatedFilter(filter,2));
        Utils.printFilter(FiltersRepository.getRotatedFilter(filter,1));
        Utils.printFilter(FiltersRepository.getRotatedFilter(filter,3));
    }

    @Test
    public void testRotationEight(){
        Double[][] filter = FiltersRepository.KIRSH;
        Utils.printFilter(filter);
        Utils.printFilter(FiltersRepository.getRotatedEightsFilter(filter));
    }
}
