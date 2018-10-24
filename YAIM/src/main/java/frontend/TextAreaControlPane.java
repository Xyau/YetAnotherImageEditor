package frontend;

import frontend.builder.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TextAreaControlPane extends GridPane implements Control {
    private TextArea textArea;
    private Text label;
    private Consumer<String> onUpdate;
    private Integer order;

    public TextAreaControlPane(String label){
        this(label,x -> {},1);
    }

    public TextAreaControlPane(String label,Integer width){
        this(label,x->{},width);
    }

    public TextAreaControlPane(String label, Consumer<String> onUpdate, Integer width) {
        this.onUpdate = onUpdate;
        this.label = new Text(label);

        textArea = new TextArea();
        textArea.setPrefColumnCount(width);
        textArea.setPrefRowCount(1);
        textArea.setOnKeyPressed(event -> onUpdate.accept(event.getText()));

        add(this.label,0,0);
        add(textArea,1,0,2,1);
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
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
        return Arrays.asList(Double.parseDouble(getText()));
    }
}
