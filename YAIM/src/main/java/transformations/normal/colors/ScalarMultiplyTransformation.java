package transformations.normal.colors;

import backend.utils.Utils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import backend.transformators.Transformation;
import transformations.normal.common.PixelByPixelTransformation;

import javax.rmi.CORBA.Util;
import java.util.Objects;

public class ScalarMultiplyTransformation extends PixelByPixelTransformation {
    Double redScalar;
    Double greenScalar;
    Double blueScalar;

    public ScalarMultiplyTransformation(Double redScalar, Double greenScalar, Double blueScalar) {
        super(c -> new Color(c.getRed()*redScalar,c.getGreen()*greenScalar,c.getBlue()*blueScalar,c.getOpacity()));
        this.redScalar = redScalar;
        this.greenScalar = greenScalar;
        this.blueScalar = blueScalar;
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
        return "Multiply the colors by (r:" + Utils.roundToRearestFraction(redScalar,0.01)
                                        + " g:" + Utils.roundToRearestFraction(greenScalar,0.01)
                                        + " b:" + Utils.roundToRearestFraction(blueScalar,0.01);
    }
}
