package backend;


import javafx.scene.input.MouseEvent;

public class Utils {
    public static int toInteger(double d){
        return new Double(Math.floor(d)).intValue();
    }

    public static Pixel getPixelFromMouseEvent(MouseEvent mouseEvent){
        int x = Utils.toInteger(mouseEvent.getX());
        int y = Utils.toInteger(mouseEvent.getY());
        return new Pixel(x,y);
    }
}
