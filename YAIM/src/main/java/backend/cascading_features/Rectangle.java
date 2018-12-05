package backend.cascading_features;

public class Rectangle {
	Integer x,y,height,width;
	Double weight;

	public Rectangle(Integer x, Integer y, Integer width, Integer height, Double weight) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.weight = weight;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}
}
