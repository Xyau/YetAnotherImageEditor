package frontend.builder;

import backend.transformators.Transformation;
import backend.utils.Utils;
import frontend.EventManageableImageView;
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

public class ControlGridPaneBuilder {
    private List<Control> controls;
    private TransformationManagerView transformationManagerView;
    private Function<ParamsHolder,Transformation> transformationBuilder;
    private Boolean built = false;
    private ParamsHolder params;

    public ControlGridPaneBuilder(Function<ParamsHolder,Transformation> transformationBuilder,
                                  TransformationManagerView transformationManagerView,
                                  EventManageableImageView imageView) {
        this.transformationBuilder = transformationBuilder;
        this.transformationManagerView = transformationManagerView;
        controls = new ArrayList<>();
    }



    public ControlGridPaneBuilder addSlider(String sliderName, Double min, Double max, Double increment){
        AtomicReference<Double> value= new AtomicReference<>(min);
        SliderControl sliderControl = new SliderControl(sliderName,min,max,increment,(old,curr)->{
            if(!Utils.roundToRearestFraction(old.doubleValue(), increment).equals(Utils.roundToRearestFraction(curr.doubleValue(), increment))){
                value.set(curr.doubleValue());
                params.reset();
                transformationManagerView.preview(transformationBuilder.apply(params));
            }
        });
        params.putDouble(value);
        controls.add(sliderControl);
        return this;
    }

    public ControlGridPaneBuilder addIntegerTextBox(String textboxName){
        AtomicReference<Number> value = new AtomicReference<>();
        TextAreaControlPane textControl = new TextAreaControlPane(textboxName,x->
                value.set(Double.parseDouble(x)),3);

        controls.add(textControl);
        return this;
    }

   public GridPane build(){
        if (built){
            throw new IllegalStateException("Builder already builded a MultiSliderGridPane");
        }
        GridPane gridPane = new GridPane();

       for (int i = 0; i < controls.size(); i++) {
           gridPane.add(controls.get(i).getPane(),0,i,4,1);
       }

       Button applyButton = new Button("Apply");
       applyButton.setOnMouseClicked(e -> {
           transformationManagerView.addTransformation(transformationBuilder.apply(params));
       });

       gridPane.add(applyButton,0, controls.size());
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
