package frontend;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.function.Consumer;

public class TextAreaControlPane extends GridPane {
    private TextArea textArea;
    private Text label;
    private Consumer<String> onUpdate;

    public TextAreaControlPane(String label){
        this(label,x -> {});
    }

    public TextAreaControlPane(String label, Consumer<String> onUpdate) {
        this.onUpdate = onUpdate;
        this.label = new Text(label);

        textArea = new TextArea();
        textArea.setPrefColumnCount(1);
        textArea.setPrefRowCount(1);

        textArea.setOnKeyPressed(event -> onUpdate.accept(event.getText()));

        add(this.label,0,0);
        add(textArea,1,0);
    }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }
}
