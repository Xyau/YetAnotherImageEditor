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

}
