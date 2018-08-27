package transformations;

import javafx.scene.image.WritableImage;

public class NoChangeTransformation implements Transformation {
    @Override
    public WritableImage transform(WritableImage writableImage) {
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Does not change the image";
    }
}
