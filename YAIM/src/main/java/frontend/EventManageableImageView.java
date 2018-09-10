package frontend;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.util.*;
import java.util.function.Consumer;

public class EventManageableImageView extends WritableImageView {

    private List<Consumer<MouseEvent>> passiveEvents;
    private Deque<PropagableMouseEventConsumer> activeEventQueue;
    private Optional<Runnable> whenQueueFinished;

    public EventManageableImageView() {
        passiveEvents = new ArrayList<>();
        activeEventQueue = new ArrayDeque<>();
        whenQueueFinished = Optional.empty();
        setOnMouseClicked( event -> {
            PropagableMouseEventConsumer consumer = null;

            if(!activeEventQueue.isEmpty()){
                consumer = activeEventQueue.removeFirst();
                consumer.accept(event);
                if(activeEventQueue.isEmpty() && whenQueueFinished.isPresent()){
                    whenQueueFinished.get().run();
                    whenQueueFinished = Optional.empty();
                }
                System.out.println("consumed event from the queue, remaining: " + activeEventQueue.size());
            } else {
                System.out.println("did no active events");
            }
            if(consumer == null || consumer.propagates()){
                passiveEvents.stream().forEach( eventConsumers -> eventConsumers.accept(event));
                System.out.println("processed passive events: " + passiveEvents.size());
            }
        });
    }

    public void addPasiveEvent(Consumer<MouseEvent> mouseEventConsumer){
        System.out.println("added passive event");
        passiveEvents.add(mouseEventConsumer);
    }

    public void addActiveEventToQueue(Consumer<MouseEvent> mouseEventConsumer){
        addActiveEventToQueue(mouseEventConsumer,true);
    }
    public void addActiveEventToQueue(Consumer<MouseEvent> mouseEventConsumer, Boolean propagate){
        if(whenQueueFinished.isPresent()){
            System.out.println("Tried to add a active event but a finisher was present. It Failed");
        } else {
            activeEventQueue.add(new PropagableMouseEventConsumer(mouseEventConsumer,propagate));
        }
    }

    public void setWhenQueueFinished(Runnable runnable){
        if(whenQueueFinished.isPresent()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Finish the current action before starting a new one!");
            alert.showAndWait();
        } else {
            if(activeEventQueue.isEmpty()){
                runnable.run();
            } else {
                whenQueueFinished = Optional.ofNullable(runnable);
            }
        }
    }
}
