package frontend;

import backend.EventManageableImageView;
import backend.Pixel;
import backend.PropagableMouseEventConsumer;
import backend.Utils;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Optional;
import java.util.function.Consumer;

public class PixelPickerControlPane extends GridPane
{
    private TextAreaControlPane fieldX = new TextAreaControlPane("X:" );
    private TextAreaControlPane fieldY = new TextAreaControlPane("Y:" );
    private Text label;
    private EventManageableImageView imageView;

    private Pixel pixel;

    public PixelPickerControlPane(EventManageableImageView imageView, String label){
        this.imageView = imageView;
        this.label = new Text(label);
        add(fieldX,1,0);
        add(fieldY,2,0);
        add(this.label,0,0);
    }

    public Optional<Pixel> getChosenPixel(){
        Integer x,y;
        try {
            x = Integer.valueOf(fieldX.getText());
            y = Integer.valueOf(fieldY.getText());
        } catch (Exception e){
            return Optional.empty();
        }
        if (imageView.getImage().getHeight() < y ||
                imageView.getImage().getWidth() < x ||
                x < 0 || y < 0){
            return Optional.empty();
        }

        return Optional.of(new Pixel(x,y));
    }

    public Consumer<MouseEvent> getEventConsumer(){
        return new PropagableMouseEventConsumer( event -> {
            Integer x = Utils.toInteger(event.getX());
            Integer y = Utils.toInteger(event.getY());
            System.out.println(label.getText() + " read X:" + x + ", Y:" + y);

            fieldX.setText(x.toString());
            fieldY.setText(y.toString());
        }, false);
    }
}
