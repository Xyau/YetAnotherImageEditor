package transformations.normal;

import backend.ColorPixel;
import backend.transformators.Transformation;
import backend.utils.ImageUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

public class LineHoughTransformation implements Transformation {
    private Map<Pair<Integer, Integer>, Integer> votationGrid; // angles wil be conveted to integers for precision reasons
    private List<ColorPixel> whitePixels;
    private Integer maxLines;
    private Double epsilon;

    private final Double thetaStep = Math.toRadians(3.0);
    private final Integer pStep = 2;

    public LineHoughTransformation(Double epsilon, Integer maxLines) {
        this.maxLines = maxLines;
        this.epsilon = epsilon;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        votationGrid = new HashMap<>();
        whitePixels = new ArrayList<>();
        initializeWhitePixels(writableImage);

        Double maxLineDistance = Math.sqrt(2) * Math.max(writableImage.getWidth(), writableImage.getHeight());
        for (Double a = -Math.PI; a < Math.PI ; a += thetaStep) {
            for (int p = 0; p < maxLineDistance; p += pStep) {
                for (ColorPixel pixel: whitePixels) {
                    if (Math.abs(p - pixel.getPixel().getX() * Math.cos(a) - pixel.getPixel().getY() * Math.sin(a)) < epsilon) {
                        addVotationToGrid(p, a);
                    }
                }
            }
        }
        PriorityQueue<Map.Entry<Pair<Integer, Integer>, Integer>> pq = getOrderedVotation();
        Integer tmpMaxLines = this.maxLines;
        while (tmpMaxLines-- != 0 && !pq.isEmpty()) {
            Map.Entry<Pair<Integer, Integer>, Integer> entry = pq.poll();
            writableImage = drawLine(entry.getKey().getKey(), integer2angle(entry.getKey().getValue()), writableImage);
        }
        return writableImage;
    }

    private void initializeWhitePixels(WritableImage writableImage) {
        for (int i = 0; i < writableImage.getWidth(); i++) {
            for (int j = 0; j < writableImage.getHeight(); j++) {
                Color color = writableImage.getPixelReader().getColor(i, j);
                if (color.getRed() == 1.0 && color.getGreen() == 1.0 && color.getBlue() == 1.0) {
                    whitePixels.add(new ColorPixel(i, j, color));
                }
            }
        }
    }

    private WritableImage drawLine(Integer p, Double a, WritableImage writableImage) {
        GraphicsContext gc = ImageUtils.getGraphicsContextFromImage(writableImage);
        gc.drawImage(writableImage,0,0);
        gc.setStroke(new Color(1.0, 0.0, 1.0, 1.0));
        gc.setLineWidth(1);
        gc.strokeLine(0, p / Math.sin(a),
                writableImage.getWidth(), (p - writableImage.getWidth() * Math.cos(a)) / Math.sin(a));
        return ImageUtils.getImageFromGraphicsContext(gc,writableImage);
    }

    private void addVotationToGrid(Integer p, Double angle) {
        Integer previousAmount = votationGrid.get(new Pair<>(p, angle2integer(angle)));
        Integer newAmount = previousAmount != null ?  previousAmount + 1 : 1;
        votationGrid.put(new Pair<>(p, angle2integer(angle)), newAmount);
    }

    private PriorityQueue<Map.Entry<Pair<Integer, Integer>, Integer>> getOrderedVotation() {
        PriorityQueue<Map.Entry<Pair<Integer, Integer>, Integer>> pq = new PriorityQueue<>(1,
                (o1, o2) -> o2.getValue() - o1.getValue());
        pq.addAll(votationGrid.entrySet());
        return pq;
    }

    // Converts an angle to an integer saving 5 decimal numbers
    private Integer angle2integer(Double angle) {
        return new Double(angle * 1E8).intValue();
    }

    private Double integer2angle(Integer angle) {
        return angle / 1E8;
    }

    @Override
    public String getDescription() {
        return "Hough Transform";
    }
}
