package frontend;

import backend.utils.Utils;
import frontend.builder.Control;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class SliderControl extends GridPane implements Control {
    private Text text;
    private Slider slider;
    private TextArea value;
    private Integer order;

    public SliderControl(String label, Double min, Double max, Double increment, BiConsumer<Number, Number> consumer){
        slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(false);
        slider.setMinorTickCount(1);
//        slider.setValue(min);
        slider.valueProperty().addListener( (change,x,y) -> {
            consumer.accept(x,y);
            value.setText(Utils.roundToRearestFraction(y.doubleValue(),increment).toString());
//            System.out.println("accepting: " + y);
        });
        text = new Text(label);
        value = new TextArea();
        value.setText(Utils.roundToRearestFraction(min,increment).toString());
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
    }

    public Optional<Double> getSelectedValue(){
        return Optional.of(slider.getValue());
    }

    public void setSliderValue(Double value) {
        if(value <= slider.getMax() && value >= slider.getMin()){
            slider.setValue(value);
            text.setText(value.toString());
        }
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public GridPane getPane() {
        return this;
    }

    @Override
    public List<Number> getValues() {
        return getSelectedValue().isPresent()?Arrays.asList(getSelectedValue().get()):Arrays.asList();
    }
}
