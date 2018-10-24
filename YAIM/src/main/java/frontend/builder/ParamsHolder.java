package frontend.builder;

import backend.Pixel;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ParamsHolder {
    List<Pair<Integer,AtomicReference<Double>>> doubles;
    List<Pair<Integer, AtomicReference<Pixel>>> pixels;
    List<Pair<Integer, AtomicReference<Integer>>> integers;

    Integer lastIndex = 0;
    Integer nextIndexToRead = 0;

    public void reset(){
        nextIndexToRead = 0;
    }

    public void putDouble(AtomicReference<Double> atomicDouble){
        putT(doubles,atomicDouble);
    }

    public Double getDouble(){
        return getT(doubles);
    }

    public void putPixel(AtomicReference<Pixel> pixel){
        putT(pixels,pixel);
    }

    public Pixel getPixel(){
        return getT(pixels);
    }

    public Integer getInteger(){
        return getT(integers);
    }

    public void putInteger(AtomicReference<Integer> integer){
        putT(integers,integer);
    }

    private <T> T getT(List<Pair<Integer,AtomicReference<T>>> list){
        Optional<T> value = list.stream().
                filter(x -> x.getKey().equals(nextIndexToRead))
                .findFirst().map(Pair::getValue).map(AtomicReference::get);

        if(!value.isPresent()){
            throw new IllegalStateException("There are no more double arguments");
        } else {
            nextIndexToRead++;
            return value.get();
        }
    }

    private <T> void putT(List<Pair<Integer,AtomicReference<T>>> list, AtomicReference<T> value){
        list.add(new Pair<>(lastIndex,value));
        lastIndex++;
    }
}
