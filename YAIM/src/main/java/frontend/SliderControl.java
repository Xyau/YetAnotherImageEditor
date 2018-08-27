package frontend;

import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Optional;
import java.util.function.BiConsumer;

public class SliderControl extends GridPane {
    private Text text;
    private Slider slider;

    public SliderControl(String label, Double min, Double max, Double increment, BiConsumer<Number, Number> consumer){
        slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(false);
        slider.setMinorTickCount(1);
        slider.valueProperty().addListener( (change,x,y) -> {
            consumer.accept(x,y);
            System.out.println("accepting: " + y);
        });
        text = new Text(label);

        add(text,0,0);
        add(slider,1,0);
        slider.widthProperty().addListener( (x) -> {
            slider.setMajorTickUnit(slider.getWidth()/10);
        });
    }

    public Optional<Double> getSelectedValue(){
        return Optional.of(slider.getValue());
    }

    public void setSliderValue(Double value) {
        if(value <= slider.getMax() && value >= slider.getMin()){
            slider.setValue(value);
        }
    }
}
