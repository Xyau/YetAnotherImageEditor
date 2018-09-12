package repositories;

import backend.Filter;
import backend.utils.Utils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import javafx.util.Pair;

import javax.rmi.CORBA.Util;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

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

    public static final Double[][] TRIANGLE = new Double[][]{{1.0, 1.0, 1.0},
                                                             {1.0, -2.0, 1.0},
                                                             {-1.0, -1.0, -1.0}};

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

    private static Cache<Pair<Filter, Integer>, Filter> rotationCache = CacheBuilder.newBuilder()
            .maximumSize(30)
            .build();

    public static Double[][] getRotatedFilter(Double[][] filter, Integer fourthsOfRotation){
        Integer finalRotation = fourthsOfRotation%4;
        Filter rotatedFilter = null;
        try {
            rotatedFilter = rotationCache.get(new Pair<>(new Filter(filter),fourthsOfRotation),()->{
                Double[][] temporaryFilter = filter;
                for (int i = 0; i < finalRotation; i++) {
                    temporaryFilter = getRotatedFilter(temporaryFilter);
                }
                return new Filter(temporaryFilter);
            });
        } catch (ExecutionException e){
            e.printStackTrace();
        }
        if(rotatedFilter == null){
            throw new IllegalStateException("Rotation cache failed!");
        }
        return rotatedFilter.getFilter();
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
    private static Cache<Pair<Double, Integer>, Double[][]> gaussianCache = CacheBuilder.newBuilder()
            .maximumSize(30)
            .build();

    public static Double[][] getGaussianMatrixWeight(Double std, Integer filterSize) {
        Double[][] weights = null;
        Double roundedStd = Utils.roundToRearestFraction(std, 0.05);
        System.out.println("getting gauss cache");
        try {
            weights = gaussianCache.get(new Pair<>(std, filterSize), () -> {
                Double[][] wMatrix = new Double[2 * filterSize + 1][2 * filterSize + 1];
                for (int i = -filterSize; i <= filterSize; i++) {
                    for (int j = -filterSize; j <= filterSize; j++) {
                        wMatrix[i + filterSize][j + filterSize] = Utils.getGaussianFilterWeight(roundedStd, i, j);
                    }
                }
                return wMatrix;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("getted gauss cache");
        if (weights == null) {
            throw new IllegalStateException("Gaussian Cache failed!");
        }
        return weights;
    }
}
