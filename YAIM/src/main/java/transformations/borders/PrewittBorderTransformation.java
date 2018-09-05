package transformations.borders;

import backend.ImageUtils;
import javafx.scene.image.WritableImage;
import transformations.Transformation;
import transformations.image.AverageImageTransformation;

import java.util.Objects;

public class PrewittBorderTransformation implements Transformation {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(1);
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        Transformation verticalTransformation = new VerticalPrewittBordersTransformation();
        WritableImage verticalImage = verticalTransformation.transform(ImageUtils.copyImage(writableImage));
        Transformation horizontalTransformation = new HorizontalPrewittBordersTransformation();
        WritableImage horizontalImage = horizontalTransformation.transform(ImageUtils.copyImage(writableImage));

        Transformation avgImgTransformation = new AverageImageTransformation(verticalImage);
        WritableImage resultImage = avgImgTransformation.transform(horizontalImage);

        return ImageUtils.transferImageTo(writableImage,resultImage);
    }

    @Override
    public String getDescription() {
        return "Prewitt Filter Transformation";
    }
}
