package frontend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class WritableImageView extends ImageView {
    WritableImage writableImage;

    public WritableImage getWritableImage() {
        return writableImage;
    }

    public void setWritableImage(WritableImage writableImage) {
        this.writableImage = writableImage;
        setImage(writableImage);
    }
}
