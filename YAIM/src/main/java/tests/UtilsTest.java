package tests;

import backend.Utils;
import org.junit.jupiter.api.Test;

public class UtilsTest {
    @Test
    public void testMatrix(){
        Integer[][] mat = Utils.getGaussianMatrixWeight(1.0,2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                sb.append(mat[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
