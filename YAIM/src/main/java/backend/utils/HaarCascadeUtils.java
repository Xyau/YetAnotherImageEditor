package backend.utils;

import backend.DenormalizedColor;
import backend.cascading_features.*;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.image.Matrix;

import java.util.ArrayList;
import java.util.List;

public class HaarCascadeUtils {
    public static DenormalizedColor rectangleEvaluator(DenormalizedImage integral, ScaledRectangle rectangle){
        DenormalizedColor sum = ColorUtils.addColors(integral.getColorAt(rectangle.getX(),rectangle.getY()),
                integral.getColorAt(rectangle.getX()+rectangle.getWidth(),rectangle.getY()+rectangle.getHeight()));
        DenormalizedColor resta = ColorUtils.addColors(integral.getColorAt(rectangle.getX(),rectangle.getY()+rectangle.getHeight()),
                integral.getColorAt(rectangle.getX()+rectangle.getWidth(),rectangle.getY()));
        return ColorUtils.substractColors(sum,resta);
    }

    public static List<ScaledRectangle> isThereAFaceInImage(AnormalizedImage anormalizedImage){
        List<Stage> stages = FileUtils.loadStages();
        List<ScaledRectangle> faces = new ArrayList<>();
        Integer height = 24, width = 24;
        Matrix integral =  getIntegralMatrix(anormalizedImage);
        System.out.println("Image h:" + integral.getHeight() + " w:" + integral.getWidth());

        for (Double scale = 3.0; scale*width < anormalizedImage.getWidth(); scale*=1.5) {
            Integer scaledHeight = Math.toIntExact(Math.round(height * scale));
            Integer scaledWidth = Math.toIntExact(Math.round(width * scale));
            for (int i = 0; i < anormalizedImage.getWidth() - scaledWidth -1; i++) {
                for (int j = 0; j < anormalizedImage.getHeight() - scaledHeight -1; j++) {
//                    System.out.println("Evaluating Scale: " + scale + " xOffset: " + i + " yOffset:" + j);
                    if(isThereAFaceInWindow(integral,stages,i,j,scale)){
                        faces.add(new ScaledRectangle(new Rectangle(0,0,width,height,0.0),scale,i,j));
                        System.out.println("Face found! Scale: " + scale + " xOffset: " + i + " yOffset:" + j);
                    }
                }
            }
        }
        return faces;
    }

    public static Boolean isThereAFaceInWindow(Matrix integral, List<Stage> stages, Integer xOffset, Integer yOffset, Double scale){
        for (int i = 0; i < stages.size(); i++) {
            if(!stageEvaluator(integral,stages.get(i),xOffset,yOffset,scale)){
//                System.out.println("Failed stage " + i);
                return false;
            }
        }
        return true;
    }

    public static Boolean stageEvaluator(Matrix integral, Stage stage, Integer xOffset, Integer yOffset, Double scale){
        Double evaluatedStage = stage.getClassifiers().stream()
                .mapToDouble( classifierValue -> classifierEvaluator(integral,classifierValue,xOffset,yOffset,scale))
                .sum();
        return evaluatedStage < stage.getStageThreshold() * 1.25;
    }

    public static Double classifierEvaluator(Matrix integral, Classifier classifier, Integer xOffset, Integer yOffset, Double scale){
        Double evaluatedValue = featureEvaluator(integral, classifier.getFeature(),xOffset,yOffset,scale);
        return evaluatedValue < classifier.getFeatureThreshold()? (classifier.getThresholdPassWeight()): (classifier.getThresholdFailWeight());
    }

    public static Double featureEvaluator(Matrix integral, Feature feature, Integer xOffset, Integer yOffset, Double scale){
        return feature.getRectangles().stream()
                .mapToDouble(rectangle -> rectangleEvaluator(integral, new ScaledRectangle(rectangle,scale,xOffset,yOffset)))
                .sum();
    }

    public static Double rectangleEvaluator(Matrix integral, ScaledRectangle rectangle){
         Double sum = integral.get(rectangle.getX(),rectangle.getY());
            sum += integral.get(rectangle.getX()+rectangle.getWidth(),rectangle.getY()+rectangle.getHeight());
            sum -= integral.get(rectangle.getX(),rectangle.getY()+rectangle.getHeight());
            sum -= integral.get(rectangle.getX()+rectangle.getWidth(),rectangle.getY());
        return  rectangle.getWeight()*sum;
    }

    public static Matrix getIntegralMatrix(AnormalizedImage anormalizedImage){
        Matrix integral = new Matrix(anormalizedImage.getWidth(),anormalizedImage.getHeight());

        for (int j = 0; j < integral.getHeight(); j++) {
            for (int i = 0; i < integral.getWidth(); i++) {
                //Asume its greyscale the image
                Double sums = anormalizedImage.getColorAt(i,j).getRed();
                Integer doubleSummed = 0;
                if(i != 0){
                    //There is an element to the left
                    doubleSummed++;
                    sums += integral.get(i-1,j);
                }
                if(j != 0){
                    //There is an element to the right
                    doubleSummed++;
                    sums += integral.get(i,j-1);
                }
                if(doubleSummed == 2){
                    sums -= integral.get(i-1,j-1);
                }
                integral.set(i,j,sums);
            }
        }
        return integral;
    }
}
