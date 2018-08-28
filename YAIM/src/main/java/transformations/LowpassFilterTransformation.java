package transformations;

public class LowpassFilterTransformation extends WeighedMeanFilterTranformation {
    public LowpassFilterTransformation() {
        super(LOWPASS);
    }
}
