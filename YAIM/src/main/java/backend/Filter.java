package backend;

import java.util.Arrays;

public class Filter {
    Double[][] filter;

    public Filter(Double[][] filter) {
        this.filter = filter;
    }

    public Double[][] getFilter() {
        return filter;
    }

    public Double get(Integer x, Integer y){
        return filter[y][x];
    }

    public Integer getHeight(){
        return filter.length;
    }

    public Integer getWidth(){
        return filter[0].length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter filter1 = (Filter) o;
        return Arrays.equals(filter, filter1.filter);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(filter);
    }
}
