package backend;

import frontend.EventManageableImageView;
import javafx.scene.layout.GridPane;

import java.util.function.Consumer;

public class FocusablePane extends GridPane {
    Consumer<EventManageableImageView> consumer;

    public FocusablePane(Consumer<EventManageableImageView> consumer) {
        this.consumer = consumer;
    }

    public void recievedFocus(EventManageableImageView imageView){
        consumer.accept(imageView);
    }
}
