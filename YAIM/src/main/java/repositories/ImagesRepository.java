package repositories;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.io.File;

public class ImagesRepository {
    public static Image RED_CROSS = new Image(new File("./YAIM/src/main/resources/cross.png").toURI().toString());
    public static Image NO_IMAGE = new Image(new File("./YAIM/src/main/resources/noImage.jpg").toURI().toString());
    public static Image PINEAPPLE = new Image(new File("./YAIM/src/main/resources/pineapple.jpg").toURI().toString());
//    public static Media OVER = new Media(new File("./YAIM/src/main/resources/Overcooked.mp4").toURI().toString());
//    public static Media OVER = new Media(new File("./YAIM/src/main/resources/Overcooked.mp4").toURI().toString());
}
