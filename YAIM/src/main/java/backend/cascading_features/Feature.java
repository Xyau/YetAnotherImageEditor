package backend.cascading_features;

import com.google.common.collect.Lists;
import org.w3c.dom.css.Rect;

import java.util.List;

public class Feature {
	List<Rectangle> rectangles;
	Integer id;

	public Feature(List<Rectangle> rectangles, Integer id) {

		this.rectangles = rectangles;
		this.id = id;
	}

	public List<Rectangle> getRectangles() {
		return rectangles;
	}

	public Integer getId() {
		return id;
	}
}
