package transformations;


import backend.Utils;

public class GaussianFilterTransformation extends WeighedMedianFilterTransformation {
    private Double std;

    public GaussianFilterTransformation(Integer filterSize, Double std) {
        super(Utils.getGaussianMatrixWeight(std,filterSize));
        this.std = std;
    }
    @Override
    public String getDescription() {
        return null;
    }
}
