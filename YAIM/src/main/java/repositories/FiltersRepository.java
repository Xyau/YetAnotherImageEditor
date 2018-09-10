package repositories;

public class FiltersRepository {
    public static Double[][] HIGHPASS = new Double[][]{{-1.0,-1.0,-1.0},
            {-1.0, 8.0,-1.0},
            {-1.0,-1.0,-1.0}};
    public static Double[][] LOWPASS = new Double[][]{{1.0,1.0,1.0},
            {1.0,1.0,1.0},
            {1.0,1.0,1.0}};
    public static Double[][] WEIGHTED_3x3 = new Double[][]{{1.0,2.0,1.0},
            {2.0,4.0,2.0},
            {1.0,2.0,1.0}};
    public static Double[][] PREWITT_HORIZONTAL = new Double[][]{{-1.0,-1.0,-1.0},
            {0.0,0.0,0.0},
            {1.0,1.0,1.0}};
    public static Double[][] PREWITT_VERTICAL = new Double[][]{ {-1.0,0.0,1.0},
                                                                {-1.0,0.0,1.0},
                                                                {-1.0,0.0,1.0}};

    public static Double[][] SOBEL_HORIZONTAL = new Double[][]{ {-1.0,-2.0,-1.0},
                                                                {0.0,0.0,0.0},
                                                                {1.0,2.0,1.0}};

    public static Double[][] SOBEL_VERTICAL = new Double[][]{   {-1.0,0.0,1.0},
                                                                {-2.0,0.0,2.0},
                                                                {-1.0,0.0,1.0}};

    public static Double[][] LAPLACIAN = new Double[][]{        {0.0,-1.0,0.0},
                                                                {-1.0,4.0,-1.0},
                                                                {0.0,-1.0,0.0}};

    public static Double[][] getOnesFilter(Integer filterSize){
        Double[][] ones = new Double[2*filterSize+1][2*filterSize+1];
        for (int i = 0; i < 2 * filterSize + 1; i++) {
            for (int j = 0; j < 2 * filterSize + 1; j++) {
                ones[i][j] = 1.0;
            }
        }
        return ones;
    }
}
