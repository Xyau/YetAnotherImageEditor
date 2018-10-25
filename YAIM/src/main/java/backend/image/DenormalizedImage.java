package backend.image;

import backend.DenormalizedColor;
import backend.utils.Utils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class DenormalizedImage implements AnormalizedImage{
	private DenormalizedColor[][] image;
	private Integer height;
	private Integer width;

	public DenormalizedImage(AnormalizedImage anormalizedImage){
		this.width = Utils.toInteger(anormalizedImage.getWidth());
		this.height = Utils.toInteger(anormalizedImage.getHeight());
		this.image = new DenormalizedColor[height][width];
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				DenormalizedColor c = anormalizedImage.getColorAt(i,j);
				this.image[j][i]= new DenormalizedColor(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
			}
		}
	}

	public DenormalizedImage(Image image){
		this.width = Utils.toInteger(image.getWidth());
		this.height = Utils.toInteger(image.getHeight());
		this.image = new DenormalizedColor[height][width];
		PixelReader px = image.getPixelReader();
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				Color c = px.getColor(i,j);
				this.image[j][i]= new DenormalizedColor(c.getRed(),c.getGreen(),c.getBlue(),c.getOpacity());
			}
		}
	}

	public DenormalizedImage(DenormalizedColor[][] image){
		this.image = image;
		this.height = image.length;
		this.width = image[0].length;
	}

	public DenormalizedImage(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		image = new DenormalizedColor[height][width];
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				image[j][i]= new DenormalizedColor(0.0,0.0,0.0,0.0);
			}
		}
	}

	@Override
	public DenormalizedColor getColorAt(Integer x, Integer y) {
		if( x < 0 || x >= width || y < 0 || y >= height){
			throw new IllegalStateException("Pixel is not within bounds: x:" + x + " y:" + y);
		}
		return image[y][x];
	}

	@Override
	public Integer getHeight() {
		return height;
	}

	@Override
	public Integer getWidth() {
		return width;
	}

	public void setColor(Integer x, Integer y, DenormalizedColor denormalizedColor){
		image[y][x] = denormalizedColor;
	}
}
