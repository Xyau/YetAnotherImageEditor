package frontend;

import backend.Colors;
import backend.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class RGBChooserView extends GridPane {
    private TextAreaControlPane red;
    private TextAreaControlPane green;
    private TextAreaControlPane blue;
    private TextAreaControlPane alpha;


    public RGBChooserView(WritableImageView writableImageView){
        red = new TextAreaControlPane("R:");
        green = new TextAreaControlPane("G:");
        blue = new TextAreaControlPane("B:");
        alpha = new TextAreaControlPane("A:");
        Button setColorButton = new Button();
        setColorButton.setText("Set Pixel Color");
        setColorButton.setOnMouseClicked(event -> {
            double red = Double.parseDouble(this.red.getText())/255;
            double blue = Double.parseDouble(this.blue.getText())/255;
            double green = Double.parseDouble(this.green.getText())/255;
            double alpha = Double.parseDouble(this.alpha.getText())/255;
            Color c = new Color(red,green,blue,alpha);
            int x = Utils.toInteger(event.getX());
            int y = Utils.toInteger(event.getY());
            writableImageView.getWritableImage().getPixelWriter().setColor(x,y,c);
        });

        add(red,0,0);
        add(green,1,0);
        add(blue,2,0);
        add(alpha,3,0);
        add(setColorButton,4,0);
    }

    public void updateAreasWith(Color color){
        red.setText(Double.toString(color.getRed()*255));
        green.setText(Double.toString(color.getGreen()*255));
        blue.setText(Double.toString(color.getBlue()*255));
        alpha.setText(Double.toString(color.getOpacity()*255));
    }
}
