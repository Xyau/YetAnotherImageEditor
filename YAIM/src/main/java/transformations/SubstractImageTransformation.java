package transformations;

import backend.ColorUtils;
import backend.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class SubstractImageTransformation implements Transformation {
    private Image image;

    public SubstractImageTransformation(Image image) {
        this.image = image;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        double[][] tmpRed = new double[new Double(writableImage.getWidth()).intValue()][new Double(writableImage.getHeight()).intValue()];
        double minRed = 1.0, maxRed = -1.0;

        double[][] tmpGreen = new double[new Double(writableImage.getWidth()).intValue()][new Double(writableImage.getHeight()).intValue()];
        double minGreen = 1.0, maxGreen = -1.0;

        double[][] tmpBlue = new double[new Double(writableImage.getWidth()).intValue()][new Double(writableImage.getHeight()).intValue()];
        double minBlue = 1.0, maxBlue = -1.0;

        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                tmpRed[i][j] = writableImage.getPixelReader().getColor(i,j).getRed() - image.getPixelReader().getColor(i,j).getRed();
                minRed = minRed > tmpRed[i][j] ? tmpRed[i][j] : minRed;
                maxRed = maxRed < tmpRed[i][j] ? tmpRed[i][j] : maxRed;

                tmpGreen[i][j] = writableImage.getPixelReader().getColor(i,j).getGreen() - image.getPixelReader().getColor(i,j).getGreen();
                minGreen = minGreen > tmpGreen[i][j] ? tmpGreen[i][j] : minGreen;
                maxGreen = maxGreen < tmpGreen[i][j] ? tmpGreen[i][j] : maxGreen;

                tmpBlue[i][j] = writableImage.getPixelReader().getColor(i,j).getBlue() - image.getPixelReader().getColor(i,j).getBlue();
                minBlue = minBlue > tmpBlue[i][j] ? tmpBlue[i][j] : minBlue;
                maxBlue = maxBlue < tmpBlue[i][j] ? tmpBlue[i][j] : maxBlue;
            }
        }

        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color newColor = ImageUtils.reescalateColorLinearly(-1.0, 1.0, 0.0, 1.0, tmpRed[i][j], tmpGreen[i][j], tmpBlue[i][j], writableImage.getPixelReader().getColor(i,j).getOpacity());
                writableImage.getPixelWriter().setColor(i,j,newColor);
            }
        }

        return writableImage;

    }

    @Override
    public String getDescription() {
        return "Substracts 2 images";
    }
}
