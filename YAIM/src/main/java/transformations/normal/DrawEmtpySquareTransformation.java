package transformations.normal;

import backend.DenormalizedColor;
import backend.Pixel;
import backend.image.DenormalizedImage;
import backend.transformators.FullTransformation;
import backend.transformators.Transformation;
import backend.utils.ImageUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class DrawEmtpySquareTransformation implements FullTransformation {

    Integer x,y,height,width;
    DenormalizedColor color;

    public DrawEmtpySquareTransformation(Integer x, Integer y, Integer height, Integer width, DenormalizedColor color) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.color = color;
    }


    @Override
    public String getDescription() {
        return "Draw emtpy square";
    }

    @Override
    public DenormalizedImage transformDenormalized(DenormalizedImage denormalizedImage) {
        for (int i = x; i < x+width; i++) {
            for (int j = y; j < y+height; j++) {
                if(ImageUtils.isPixelInImage(denormalizedImage,new Pixel(i,j)) && (i == x || j == y || i == x+width-1 || j== y+width-1)){
                    denormalizedImage.setColor(i,j,color);
                }
            }
        }
        return denormalizedImage;
    }
}
