package repositories;

import java.util.function.BiFunction;

public class FunctionsRepository {

    public static BiFunction<Double,Double,Double> LECLERC_DETECTOR = (intensity, std) -> Math.exp(-intensity*intensity/(std*std));
    public static BiFunction<Double,Double,Double> LORENTZIAN_DETECTOR = (intensity, std) -> 1/(1+(intensity*intensity/(std*std)));

}
