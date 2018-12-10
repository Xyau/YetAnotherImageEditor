package backend.image;

import backend.DenormalizedColor;
import backend.utils.Utils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Matrix{
	private Double[][] matrix;
	private Integer height;
	private Integer width;

	public Matrix(Integer width, Integer height) {
		this.width = width;
		this.height = height;
		matrix = new Double[height][width];
	}

	public Double get(Integer x, Integer y) {
		if( x < 0 || x >= width || y < 0 || y >= height){
			throw new IllegalStateException("Pixel is not within bounds: x:" + x + " y:" + y);
		}
		return matrix[y][x];
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}

	public void set(Integer x, Integer y, Double value){
		matrix[y][x] = value;
	}
}
