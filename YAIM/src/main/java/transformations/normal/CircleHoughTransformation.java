package transformations.normal;

import backend.ColorPixel;
import backend.Pixel;
import backend.transformators.Transformation;
import backend.utils.ImageUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.*;

public class CircleHoughTransformation implements Transformation {
    private Map<Pair<Pixel, Integer>, Integer> votationGrid; // angles wil be conveted to integers for precision reasons
    private List<ColorPixel> whitePixels;
    private Integer maxCircles;
    private Double epsilon;

    private final Integer iStep = 5;
    private final Integer jStep = 5;
    private final Integer rStep = 5;

    public CircleHoughTransformation(Double epsilon, Integer maxCircles) {
        this.epsilon = epsilon;
        this.maxCircles = maxCircles;
    }

    @Override
    public WritableImage transform(WritableImage writableImage) {
        votationGrid = new HashMap<>();
        whitePixels = new ArrayList<>();
        initializeWhitePixels(writableImage);

        Double maxRadius = Math.sqrt(2) * Math.max(writableImage.getWidth(), writableImage.getHeight()) / 2;
        for (Integer i = 0; i < writableImage.getWidth(); i += iStep) {
            for (Integer j = 0; j < writableImage.getHeight(); j += jStep) {
                for (int r = 0; r < maxRadius; r += rStep) {
                    for (ColorPixel pixel : whitePixels) {
                        if (Math.abs(r*r - Math.pow(pixel.getPixel().getX() - i, 2) - Math.pow(pixel.getPixel().getY() - j, 2)) < epsilon) {
                            addVotationToGrid(new Pixel(i, j), r);
                        }
                    }
                }
            }
        }
        PriorityQueue<Map.Entry<Pair<Pixel, Integer>, Integer>> pq = getOrderedVotation();
        Integer tmpMaxCircles = this.maxCircles;
        while (tmpMaxCircles-- != 0 && !pq.isEmpty()) {
            Map.Entry<Pair<Pixel, Integer>, Integer> entry = pq.poll();
            writableImage = drawCircle(entry.getKey().getKey().getX(), entry.getKey().getKey().getY(), entry.getKey().getValue(), writableImage);
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

    private WritableImage drawCircle(Integer i, Integer j, Integer r, WritableImage writableImage) {
        GraphicsContext gc = ImageUtils.getGraphicsContextFromImage(writableImage);
        gc.drawImage(writableImage,0,0);
        gc.setStroke(new Color(1.0, 0.0, 1.0, 1.0));
        gc.setLineWidth(1);
        gc.strokeOval(i - r, j - r, r * 2, r * 2);
        return ImageUtils.getImageFromGraphicsContext(gc,writableImage);
    }

    private void addVotationToGrid(Pixel p, Integer radius) {
        Integer previousAmount = votationGrid.get(new Pair<>(p, radius));
        Integer newAmount = previousAmount != null ?  previousAmount + 1 : 1;
        votationGrid.put(new Pair<>(p, radius), newAmount);
    }

    private PriorityQueue<Map.Entry<Pair<Pixel, Integer>, Integer>> getOrderedVotation() {
        PriorityQueue<Map.Entry<Pair<Pixel, Integer>, Integer>> pq = new PriorityQueue<>(1,
                (o1, o2) -> o2.getValue() - o1.getValue());
        pq.addAll(votationGrid.entrySet());
        return pq;
    }

    @Override
    public String getDescription() {
        return "Hough Transform (Circles)";
    }
}
