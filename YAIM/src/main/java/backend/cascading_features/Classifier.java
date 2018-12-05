package backend.cascading_features;

public class Classifier {
	Feature feature;
	//If the sum is less than the feature thresshold, it passes else it fails
	Double featureThreshold;
	Double thresholdPassWeight;
	Double thresholdFailWeight;

	public Classifier(Feature feature, Double featureThreshold, Double thresholdPassWeight, Double thresholdFailWeight) {

		this.feature = feature;
		this.featureThreshold = featureThreshold;
		this.thresholdPassWeight = thresholdPassWeight;
		this.thresholdFailWeight = thresholdFailWeight;
	}

	public Feature getFeature() {
		return feature;
	}

	public Double getFeatureThreshold() {
		return featureThreshold;
	}

	public Double getThresholdPassWeight() {
		return thresholdPassWeight;
	}

	public Double getThresholdFailWeight() {
		return thresholdFailWeight;
	}
}
