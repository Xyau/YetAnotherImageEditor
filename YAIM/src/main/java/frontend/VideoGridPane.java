package frontend;

import backend.utils.Utils;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VideoGridPane extends GridPane {
    private List<Image> images;
    private SliderControl sliderControl;
    private AtomicInteger frameNumber;
    private TransformationManagerView transformationManagerView;


    public VideoGridPane(List<Image> images) {
        this.images = images;

    }
}
