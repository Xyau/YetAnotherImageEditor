package transformations.common;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import transformations.Transformation;

import java.util.Objects;
import java.util.function.Function;

public class PixelByPixelTransformation implements Transformation {
    private Function<Color,Color> colorTransformer;

    public PixelByPixelTransformation(Function<Color, Color> colorTransformer) {
        this.colorTransformer = colorTransformer;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        PixelReader pixelReader = writableImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = colorTransformer.apply(pixelReader.getColor(i,j));
                pixelWriter.setColor(i,j,c);
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Base pixel by pixel transformation";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixelByPixelTransformation that = (PixelByPixelTransformation) o;
        return Objects.equals(colorTransformer, that.colorTransformer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorTransformer);
    }
}
