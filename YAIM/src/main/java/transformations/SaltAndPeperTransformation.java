package transformations;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Random;

public class SaltAndPeperTransformation implements Transformation {
    private Long seed;
    private Double saltRatio;
    private Double pepperRatio;

    public SaltAndPeperTransformation(Long seed, Double saltRatio, Double pepperRatio) {
        this.seed = seed;
        this.saltRatio = saltRatio;
        this.pepperRatio = pepperRatio;
        if(saltRatio+pepperRatio>1)throw new IllegalStateException("Illegal salt and pepper percentages");
    }

    public SaltAndPeperTransformation(String seed, Double saltRatio, Double pepperRatio) {
        this(new Long(seed.hashCode()),saltRatio,pepperRatio);
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        Random random = new Random(seed);
        PixelReader pixelReader = writableImage.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color c = pixelReader.getColor(i,j);
                Double r = random.nextDouble();
                if(r<saltRatio) {
                    c = Color.WHITE;
                } else if(r<saltRatio+pepperRatio){
                    c = Color.BLACK;
                }
                pixelWriter.setColor(i,j,c);
            }
        }
        return writableImage;
    }

    @Override
    public String getDescription() {
        return "Salt and Pepper transformation";
    }
}
