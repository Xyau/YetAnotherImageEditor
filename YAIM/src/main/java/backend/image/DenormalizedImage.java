package backend.image;

import backend.DenormalizedColor;

public class DenormalizedImage implements AnormalizedImage{
	private DenormalizedColor[][] image;
	private Integer height;
	private Integer width;

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
			throw new IllegalStateException("Pixel is not within bounds");
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
