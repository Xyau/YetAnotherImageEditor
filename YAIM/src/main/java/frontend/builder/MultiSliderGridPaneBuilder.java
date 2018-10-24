package frontend.builder;

import backend.transformators.Transformation;
import backend.utils.Utils;
import frontend.SliderControl;
import frontend.TextAreaControlPane;
import frontend.TransformationManagerView;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import repositories.StagesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultiSliderGridPaneBuilder {
    private List<Pair<Control,AtomicReference<Number>>> sliders;
    private TransformationManagerView transformationManagerView;
    private Function<List<Number>,Transformation> transformationBuilder;
    private Boolean built = false;

    public MultiSliderGridPaneBuilder(Function<List<Number>,Transformation> transformationBuilder,
                                      TransformationManagerView transformationManagerView) {
        this.transformationBuilder = transformationBuilder;
        this.transformationManagerView = transformationManagerView;
        sliders = new ArrayList<>();
    }

    private List<Number> getValues(){
        return sliders.stream()
                .map(Pair::getValue)
                .map(AtomicReference::get)
                .collect(Collectors.toList());
    }

    public MultiSliderGridPaneBuilder addSlider(String sliderName, Double min, Double max, Double increment){
        AtomicReference<Number> value= new AtomicReference<>(min);
        SliderControl sliderControl = new SliderControl(sliderName,min,max,increment,(old,curr)->{
            if(!Utils.roundToRearestFraction(old.doubleValue(), increment).equals(Utils.roundToRearestFraction(curr.doubleValue(), increment))){
                value.set(curr);
                transformationManagerView.preview(transformationBuilder.apply(getValues()));
            }
        });
        sliders.add(new Pair<>(sliderControl,value));
        return this;
    }

    public MultiSliderGridPaneBuilder addTextBox(String textboxName){
        AtomicReference<Number> value = new AtomicReference<>();
        TextAreaControlPane textControl = new TextAreaControlPane(textboxName,x->
                value.set(Double.parseDouble(x)),3);

        sliders.add(new Pair<>(textControl,value));
        return this;
    }

   public GridPane build(){
        if (built){
            throw new IllegalStateException("Builder already builded a MultiSliderGridPane");
        }
        GridPane gridPane = new GridPane();

       for (int i = 0; i < sliders.size(); i++) {
           gridPane.add(sliders.get(i).getKey().getPane(),0,i,4,1);
       }

       Button applyButton = new Button("Apply");
       applyButton.setOnMouseClicked(e -> {
           transformationManagerView.addTransformation(transformationBuilder.apply(getValues()));
       });

       gridPane.add(applyButton,0,sliders.size());
       built = true;
       return gridPane;
   }

   public MenuItem buildAndGetMenuItem(String name){
       MenuItem menuItem = new MenuItem(name+"...");
       GridPane gridPane = this.build();
       menuItem.setOnAction(event -> {
           StagesRepository.getStage(name, gridPane).show();
       });
       return menuItem;
   }
}
