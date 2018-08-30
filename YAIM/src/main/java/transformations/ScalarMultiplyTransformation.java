package transformations;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Objects;

public class ScalarMultiplyTransformation implements Transformation {
    Double redScalar;
    Double greenScalar;
    Double blueScalar;

    public ScalarMultiplyTransformation(Double redScalar, Double greenScalar, Double blueScalar) {
        this.redScalar = redScalar;
        this.greenScalar = greenScalar;
        this.blueScalar = blueScalar;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        PixelReader pixelReader = writableImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = pixelReader.getColor(i,j);
                pixelWriter.setColor(i,j,new Color((c.getRed()*redScalar),
                        c.getGreen()*greenScalar,c.getBlue()*blueScalar,c.getOpacity()));
            }
        }
        return writableImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScalarMultiplyTransformation that = (ScalarMultiplyTransformation) o;
        return Objects.equals(redScalar, that.redScalar) &&
                Objects.equals(greenScalar, that.greenScalar) &&
                Objects.equals(blueScalar, that.blueScalar);
    }

    @Override
    public int hashCode() {

        return Objects.hash(redScalar, greenScalar, blueScalar);
    }

    @Override

    public String getDescription() {
        return "Changes the image by some scalar";
    }
}
