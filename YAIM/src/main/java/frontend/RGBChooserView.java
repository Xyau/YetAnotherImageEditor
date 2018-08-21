package frontend;

import backend.Colors;
import backend.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class RGBChooserView {
    private TextField redArea;
    private TextField blueArea;
    private TextField greenArea;
    private TextField alphaArea;

    private Button setColorButton;

    private Text redText;
    private Text blueText;
    private Text greenText;
    private Text alphaText;

    private WritableImageView writableImageView;

    private static final Integer AREA_WIDTH = 30;
    private static final Integer TEXT_WIDTH = 30;
    private static final Integer AREA_SPACE_BETWEEN = 40;
    private static final Integer AREA_OFFSET = 20;

    public RGBChooserView(WritableImageView writableImageView){
        this.writableImageView = writableImageView;

        redArea = initTextField(0);
        blueArea = initTextField(1);
        greenArea = initTextField(2);
        alphaArea = initTextField(3);

        redText = initText(new Text(),0,"R:");
        blueText = initText(new Text(),1,"B:");
        greenText = initText(new Text(),2,"G:");
        alphaText = initText(new Text(),3,"A:");

        setColorButton = new Button();
        setColorButton.setText("Set Pixel Color");
        setColorButton.setLayoutX((AREA_WIDTH+AREA_SPACE_BETWEEN)*4);
        setColorButton.setLayoutY(20);
        setColorButton.setOnMouseClicked(event -> {
            double red = Double.parseDouble(redArea.getText())/255;
            double blue = Double.parseDouble(blueArea.getText())/255;
            double green = Double.parseDouble(greenArea.getText())/255;
            double alpha = Double.parseDouble(alphaArea.getText())/255;
            Color c = new Color(red,green,blue,alpha);
            int x = Utils.toInteger(event.getX());
            int y = Utils.toInteger(event.getY());
            writableImageView.getWritableImage().getPixelWriter().setColor(x,y,c);
        });
    }

    private Text initText(Text text, Integer index, String label){
        text.setLayoutX((AREA_WIDTH+AREA_SPACE_BETWEEN)*index);
        text.setLayoutY(40);
        text.setText(label);
        return text;
    }

    private TextField initTextField(Integer index){
        TextField textField = new TextField();
        textField.setLayoutX((AREA_WIDTH+AREA_SPACE_BETWEEN)*index+AREA_OFFSET);
        textField.setLayoutY(20);
        textField.maxHeight(40);
        textField.setPrefColumnCount(2);
        return textField;
    }

    public void updateAreasWith(Color color){
        redArea.setText(Double.toString(color.getRed()*255));
        greenArea.setText(Double.toString(color.getGreen()*255));
        blueArea.setText(Double.toString(color.getBlue()*255));
        alphaArea.setText(Double.toString(color.getOpacity()*255));
    }

    public Pane getPane(){
        Pane pane = new Pane();
        pane.setMaxHeight(80);
        pane.getChildren().add(redArea);
        pane.getChildren().add(redText);
        pane.getChildren().add(greenArea);
        pane.getChildren().add(greenText);
        pane.getChildren().add(blueArea);
        pane.getChildren().add(blueText);
        pane.getChildren().add(alphaArea);
        pane.getChildren().add(alphaText);
        pane.getChildren().add(setColorButton);
        pane.setLayoutX(20);
        pane.setLayoutY(0);
//        pane.setBackground(Colors.RED_BACKGROUND);
        return pane;
    }
}
