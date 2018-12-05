package backend.cascading_features;

import com.google.common.collect.Lists;

import java.util.List;

public class Stage {
	List<Classifier> classifiers;
	Double stageThreshold;

	public Stage(List<Classifier> classifiers, Double stageThreshold) {
		this.classifiers = classifiers;
		this.stageThreshold = stageThreshold;
	}

	public List<Classifier> getClassifiers() {
		return classifiers;
	}

	public Double getStageThreshold() {
		return stageThreshold;
	}
}
