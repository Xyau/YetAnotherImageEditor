package transformations;


import javafx.scene.image.WritableImage;

public interface Transformation {
    WritableImage transform(WritableImage writableImage);

    String getDescription();
}
