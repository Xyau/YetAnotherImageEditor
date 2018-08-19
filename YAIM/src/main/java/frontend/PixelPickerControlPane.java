package frontend;

import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class PixelPickerControlPane extends GridPane
{
    private TextArea xArea;
    private TextArea yArea;

    private Text xLabel = new Text("X:");
    private Text yLabel = new Text("Y:");

    public PixelPickerControlPane(){
//        -
    }
}
