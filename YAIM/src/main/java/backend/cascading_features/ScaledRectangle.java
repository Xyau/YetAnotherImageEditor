package backend.cascading_features;

public class ScaledRectangle {
	Integer x,y,height,width;
	Double weight;

	public ScaledRectangle(Rectangle rectangle, Double scale, Integer offsetX, Integer offsetY) {
		this.x = rectangle.getX()+offsetX;
		this.y = rectangle.getY()+offsetY;
		this.height = Math.toIntExact(Math.round(rectangle.height * scale));
		this.width = Math.toIntExact(Math.round(rectangle.width * scale));
		this.weight = rectangle.weight;
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

	@Override
	public String toString() {
		return "ScaledRectangle{" +
				"x=" + x +
				",y=" + y +
				",h=" + height +
				",w=" + width +
				",weight=" + weight +
				'}';
	}

	public Double getWeight() {
		return weight;
	}
}
