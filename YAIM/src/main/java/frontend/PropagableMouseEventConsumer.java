package frontend;

import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

public class PropagableMouseEventConsumer implements Consumer<MouseEvent> {
    private Consumer<MouseEvent> eventConsumer;
    private Boolean propagate;

    public PropagableMouseEventConsumer(Consumer<MouseEvent> eventConsumer, Boolean propagate) {
        this.eventConsumer = eventConsumer;
        this.propagate = propagate;
    }

    @Override
    public void accept(MouseEvent mouseEvent) {
        eventConsumer.accept(mouseEvent);
    }

    public Boolean propagates() {
        return propagate;
    }
}
