package backend.cascading_features;

import com.google.common.collect.Lists;

import java.util.List;

public class Stage {
	List<Classifier> classifiers;
	Double stageThreshold;
	Integer height;
	Integer width;

	public Stage(List<Classifier> classifiers, Double stageThreshold) {
		this.classifiers = classifiers;
		this.stageThreshold = stageThreshold;
		this.height = 24;
		this.width = 24;

	}

	public List<Classifier> getClassifiers() {
		return classifiers;
	}

	public Double getStageThreshold() {
		return stageThreshold;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getWidth() {
		return width;
	}
}
