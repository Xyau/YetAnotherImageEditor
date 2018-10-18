package backend.utils;

import backend.DenormalizedColorPixel;
import backend.Filter;

import java.util.List;

public class CombinerUtils {
    public static DenormalizedColorPixel getPixel(List<DenormalizedColorPixel> pixels, Integer x, Integer y){
        return pixels.stream()
                .filter( p -> p.getPixel().getX().equals(x) && p.getPixel().getY().equals(y))
                .findFirst()
                .get();
    }


    public static DenormalizedColorPixel getCenterPixel(List<DenormalizedColorPixel> pixels, Filter filter){
        Integer x = filter.getWidth()/2;
        Integer y = filter.getHeight()/2;

        return getPixel(pixels,x,y);
    }
}

