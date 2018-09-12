package frontend;

import backend.utils.Utils;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Optional;
import java.util.function.BiConsumer;

public class SliderControl extends GridPane {
    private Text text;
    private Slider slider;
    private TextArea value;

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
            value.setText(Utils.roundToRearestFraction(y.doubleValue(),increment).toString());
//            System.out.println("accepting: " + y);
        });
        text = new Text(label);
        value = new TextArea();
        value.setPrefColumnCount(2);
        value.setPrefRowCount(1);
        value.setOnKeyReleased( (x)->{
            try {
                Double value = Double.parseDouble(x.getText());
                setSliderValue(value);
            } catch (NumberFormatException ignored){
            };
        });
        add(value,2,0);
        add(text,0,0);
        add(slider,1,0);
        slider.widthProperty().addListener( (x) -> {
            slider.setMajorTickUnit(slider.getWidth()/10);
        });
        slider.setValue(min);
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
