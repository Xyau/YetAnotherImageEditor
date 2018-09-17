package transformations.normal.umbrals;

import backend.Histogram;
import backend.transformators.Transformation;
import backend.utils.ImageUtils;
import javafx.scene.image.WritableImage;
import transformations.normal.HistogramEqualizationTransformation;

import java.util.*;


public class OtsuUmbralizationTransformation implements Transformation {
    @Override
    public WritableImage transform(WritableImage writableImage) {
        WritableImage tmpWritableImage = ImageUtils.copyImage(writableImage);
        Histogram equalizedHistogram = new Histogram(new HistogramEqualizationTransformation().transform(tmpWritableImage));

        List<Double> cummulativeSumsRed = new ArrayList<>(Collections.nCopies(256, 0.0));
        List<Double> cummulativeSumsGreen = new ArrayList<>(Collections.nCopies(256, 0.0));
        List<Double> cummulativeSumsBlue = new ArrayList<>(Collections.nCopies(256, 0.0));

        List<Double> cummulativeMeanRed = new ArrayList<>(Collections.nCopies(256, 0.0));
        List<Double> cummulativeMeanGreen = new ArrayList<>(Collections.nCopies(256, 0.0));
        List<Double> cummulativeMeanBlue = new ArrayList<>(Collections.nCopies(256, 0.0));

        for (int t = 0; t < 256; t++) {
            for (int i = 0; i <= t; i++) {
                cummulativeSumsRed.set(t, cummulativeSumsRed.get(t) + equalizedHistogram.getHistogramRed()[i]);
                cummulativeSumsGreen.set(t, cummulativeSumsGreen.get(t) + equalizedHistogram.getHistogramGreen()[i]);
                cummulativeSumsBlue.set(t, cummulativeSumsBlue.get(t) + equalizedHistogram.getHistogramBlue()[i]);

                cummulativeMeanRed.set(t, cummulativeMeanRed.get(t) +  i * equalizedHistogram.getHistogramRed()[i]);
                cummulativeMeanGreen.set(t, cummulativeMeanGreen.get(t) + i *  equalizedHistogram.getHistogramGreen()[i]);
                cummulativeMeanBlue.set(t, cummulativeMeanBlue.get(t) + i *  equalizedHistogram.getHistogramBlue()[i]);
            }
        }

        Double globalMeanRed = 0.0;
        Double globalMeanGreen = 0.0;
        Double globalMeanBlue = 0.0;

        for (int i = 0; i < 256; i++) {
            globalMeanRed += i * equalizedHistogram.getHistogramRed()[i];
            globalMeanGreen += i *  equalizedHistogram.getHistogramGreen()[i];
            globalMeanBlue += i *  equalizedHistogram.getHistogramBlue()[i];
        }

        List<Double> varianceRed = new ArrayList<>(Collections.nCopies(256, 0.0));
        List<Double> varianceGreen = new ArrayList<>(Collections.nCopies(256, 0.0));
        List<Double> varianceBlue = new ArrayList<>(Collections.nCopies(256, 0.0));

        for (int i = 0; i < 256; i++) {
            varianceRed.set(i, Math.pow(globalMeanRed * cummulativeSumsRed.get(i) - cummulativeMeanRed.get(i), 2)
                    / (cummulativeSumsRed.get(i) * (1.0 - cummulativeSumsRed.get(i))) );

            varianceGreen.set(i, Math.pow(globalMeanGreen * cummulativeSumsGreen.get(i) - cummulativeMeanGreen.get(i), 2)
                    / (cummulativeSumsGreen.get(i) * (1.0 - cummulativeSumsGreen.get(i))) );

            varianceBlue.set(i, Math.pow(globalMeanBlue * cummulativeSumsBlue.get(i) - cummulativeMeanBlue.get(i), 2)
                    / (cummulativeSumsBlue.get(i) * (1.0 - cummulativeSumsBlue.get(i))) );
        }

        Double redThreashold = getMaxValueIndex(varianceRed) / 256.0;
        Double greenThreashold = getMaxValueIndex(varianceGreen) / 256.0;
        Double blueThreashold = getMaxValueIndex(varianceBlue) / 256.0;

        return new MultiChannelBinaryTransformation(redThreashold, greenThreashold, blueThreashold).transform(writableImage);
    }

    // Returns index of maximum value. If more than one, it returns the mean of all of them
    private Integer getMaxValueIndex(List<Double> list) {
        List<Integer> maximumIndexes = new ArrayList<>();
        Integer currentMax = -1;
        for (int i = 0; i < list.size(); i++) {
            Integer value = new Double(list.get(i) * 256.0).intValue();
            if (value > currentMax) {
                maximumIndexes.clear();
                maximumIndexes.add(i);
                currentMax = value;
            } else if (value.equals(currentMax)) {
                maximumIndexes.add(i);
            }
        }

        Integer returnedIndex = 0;
        for (Integer i : maximumIndexes) {
            returnedIndex += i;
        }
        return returnedIndex / maximumIndexes.size();
    }

    @Override
    public String getDescription() {
        return "Otsu Binary";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass());
    }
}
