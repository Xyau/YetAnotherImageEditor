package transformations.borders;

import backend.ImageUtils;
import javafx.scene.image.WritableImage;
import transformations.Transformation;
import transformations.image.AverageImageTransformation;

public class SobelBorderTransformation implements Transformation {

    @Override
    public WritableImage transform(WritableImage writableImage) {
        Transformation verticalTransformation = new VerticalSobelBordersTransformation();
        WritableImage verticalImage = verticalTransformation.transform(ImageUtils.copyImage(writableImage));
        Transformation horizontalTransformation = new HorizontalSobelBordersTransformation();
        WritableImage horizontalImage = horizontalTransformation.transform(ImageUtils.copyImage(writableImage));

        Transformation avgImgTransformation = new AverageImageTransformation(verticalImage);
        WritableImage resultImage = avgImgTransformation.transform(horizontalImage);

        return ImageUtils.transferImageTo(writableImage,resultImage);
    }

    @Override
    public String getDescription() {
        return "Sobel border Transformation";
    }
}
