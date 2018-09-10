package tests;

import backend.utils.Utils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class UtilsTest {
    @Test
    public void testMatrix(){
        Double[][] mat = Utils.getGaussianMatrixWeight(1.0,2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                sb.append(mat[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void testRound(){
        System.out.println(Utils.roundToRearestFraction(0.1412,0.05));
        System.out.println(Utils.roundToRearestFraction(0.14122,0.05));
        System.out.println(Utils.roundToRearestFraction(0.141212114,0.05));
        System.out.println(Utils.roundToRearestFraction(0.1312,0.05));
        System.out.println(Utils.roundToRearestFraction(0.1812,0.05));
        Assert.assertThat(Utils.roundToRearestFraction(0.141212114,0.05), CoreMatchers.equalTo(Utils.roundToRearestFraction(0.1312,0.05)));
    }
}
