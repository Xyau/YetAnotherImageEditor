package transformations;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Objects;


public class GammaTransformation implements Transformation {

    double gamma;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GammaTransformation that = (GammaTransformation) o;
        return Double.compare(that.gamma, gamma) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(gamma);
    }

    public GammaTransformation(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = writableImage.getPixelReader().getColor(i, j);
                double red = Math.pow(c.getRed(), gamma);
                double green = Math.pow(c.getGreen(), gamma);
                double blue = Math.pow(c.getBlue(), gamma);
                writableImage.getPixelWriter().setColor(i, j, new Color(red, green, blue, c.getOpacity()));
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Binary";
    }
}
