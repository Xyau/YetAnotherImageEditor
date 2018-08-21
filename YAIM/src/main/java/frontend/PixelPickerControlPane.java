package frontend;

import backend.Pixel;
import backend.Utils;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PixelPickerControlPane extends GridPane
{
    private TextArea xArea;
    private TextArea yArea;

    private Text xLabel = new Text("X:");
    private Text yLabel = new Text("Y:");

    private ImageView imageView;

    private AtomicReference<Pixel> pixel;

    public PixelPickerControlPane(ImageView imageView){
        this.imageView = imageView;
        pixel = new AtomicReference<>();
    }

    public Optional<Pixel> getChosenPixel(){
        return Optional.ofNullable(pixel.get());
    }

    public void focus(ImageView imageView){
        imageView.setOnMouseClicked(event -> {
            Integer x = Utils.toInteger(event.getX());
            Integer y = Utils.toInteger(event.getY());

            pixel.set(new Pixel(x,y));
        });
    }
}
