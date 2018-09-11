package repositories;

import java.util.Arrays;

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
    public static Double[][] KIRSH = new Double[][]{            {5.0,5.0,5.0},
                                                                {-3.0,0.0,-3.0},
                                                                {-3.0,-3.0,-3.0}};
    public static Double[][] HORIZONTAL_ZEROES = new Double[][]{{1.0,1.0}};
    public static Double[][] VERTICAL_ZEROES = new Double[][]{{1.0},
                                                              {1.0}};

    public static Double[][] LOG = new Double[][]{
            {0.0008,0.0066,0.0215,0.031,0.0215,0.0066,0.0008},
            {0.0066,0.0438,0.0982,0.108,0.0982,0.0438,0.0066},
            {0.0215,0.0982, 0.0 ,-0.242,  0.0 ,0.0982,0.0251},
            {0.031,0.108,-0.242,-0.7979,-0.242,0.108,0.031},
            {0.0215,0.0982, 0.0 ,-0.242,  0.0 ,0.0982,0.0251},
            {0.0066,0.0438,0.0982,0.108,0.0982,0.0438,0.0066},
            {0.0008,0.0066,0.0215,0.031,0.0215,0.0066,0.0008},
    };

    public static Double[][] getLOGFilter(Integer filterSize){
        if(filterSize == 3) {
            return LOG;
        }
        Double[][] subset = new Double[2*filterSize+1][2*filterSize+1];
        Integer offset = ((LOG.length-1)/2)-filterSize;
        for (int i = 0; i < 2 * filterSize + 1; i++) {
            for (int j = 0; j < 2 * filterSize + 1; j++) {
                subset[i][j] = LOG[i+offset][j+offset];
            }
        }
        return subset;
    }

    public static Double[][] getOnesFilter(Integer filterSize){
        Double[][] ones = new Double[2*filterSize+1][2*filterSize+1];
        for (int i = 0; i < 2 * filterSize + 1; i++) {
            for (int j = 0; j < 2 * filterSize + 1; j++) {
                ones[i][j] = 1.0;
            }
        }
        return ones;
    }

    public static Double[][] getRotatedFilter(Double[][] filter, Integer fourthsOfRotation){
        Double[][] rotatedFilter = filter;
        for (int i = 0; i < fourthsOfRotation; i++) {
            rotatedFilter = getRotatedFilter(rotatedFilter);
        }
        return rotatedFilter;
    }

    public static Double[][] getRotatedFilter(Double[][] filter){
        if(filter.length == 0 || filter[0].length == 0){
            throw new IllegalStateException("Cant rotate a filter with 0 length");
        }
        Integer width = filter[0].length;
        Integer height = filter.length;
        Double[][] rotatedFilter = new Double[filter[0].length][filter.length];

        //rotatedFilter[y][x]
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedFilter[x][height-y-1] = filter[y][x];
            }
        }
        return rotatedFilter;
    }
}
