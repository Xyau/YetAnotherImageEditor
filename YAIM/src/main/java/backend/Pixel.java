package backend;

import java.util.Objects;

public class Pixel {
    private Integer x;
    private Integer y;

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Pixel(Integer x, Integer y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return Objects.equals(x, pixel.x) &&
                Objects.equals(y, pixel.y);
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y);
    }
}
