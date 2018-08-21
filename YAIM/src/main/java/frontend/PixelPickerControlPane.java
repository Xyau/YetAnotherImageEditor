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
    TextAreaControlPane fieldX = new TextAreaControlPane("X:" );
    TextAreaControlPane fieldY = new TextAreaControlPane("Y:" );

    private ImageView imageView;

    public PixelPickerControlPane(ImageView imageView){
        this.imageView = imageView;
        add(fieldX,0,0);
        add(fieldY,1,0);
    }

    public Optional<Pixel> getChosenPixel(){
        Integer x,y;
        try {
            x = Integer.valueOf(fieldX.getText());
            y = Integer.valueOf(fieldY.getText());
        } catch (Exception e){
            return Optional.empty();
        }
        if (imageView.getImage().getHeight() > y ||
                imageView.getImage().getWidth() > x ||
                x < 0 || y < 0){
            return Optional.empty();
        }

        return Optional.of(new Pixel(x,y));
    }

    public void focus(ImageView imageView){
        imageView.setOnMouseClicked(event -> {
            Integer x = Utils.toInteger(event.getX());
            Integer y = Utils.toInteger(event.getY());

//            pixel.set(new Pixel(x,y));
        });
    }
}
