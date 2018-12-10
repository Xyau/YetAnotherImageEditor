package tests;

import backend.DenormalizedColor;
import backend.cascading_features.Rectangle;
import backend.cascading_features.ScaledRectangle;
import backend.image.AnormalizedImage;
import backend.image.DenormalizedImage;
import backend.image.Matrix;
import backend.utils.HaarCascadeUtils;
import org.junit.jupiter.api.Test;

public class HaarCascadeTests {
    @Test
    public void testIntegral(){
        DenormalizedImage test = new DenormalizedImage(5,5);
        for (int i = 0; i < test.getWidth(); i++) {
            for (int j = 0; j < test.getHeight(); j++) {
//                test.setColor(i,j,new DenormalizedColor(0.0,0.0,0.0,1.0));
                test.setColor(i,j,new DenormalizedColor(1.0,1.0,1.0,1.0));
            }
        }

        for (int i = 1; i < 3; i++) {
            for (int j = 2; j < 4; j++) {
            }
        }

        printImageRed(test);
        System.out.println("");
        Matrix matrix = HaarCascadeUtils.getIntegralMatrix(test);
        printMatrix(matrix);

        ScaledRectangle scaledRectangle = new ScaledRectangle(new Rectangle(2,1,2,2,1.0),1.0,0,0);
        System.out.println(scaledRectangle);
        Double value = HaarCascadeUtils.rectangleEvaluator(matrix,scaledRectangle);
        System.out.println(value);
    }

    @Test
    public void testArea(){
//        HaarCascadeUtils.rectangleEvaluator()
    }

    private void printImageRed(AnormalizedImage anormalizedImage){
        for (int j = 0; j < anormalizedImage.getHeight(); j++) {
            for (int i = 0; i < anormalizedImage.getWidth(); i++) {
                System.out.print(anormalizedImage.getColorAt(i,j).getRed() + " ");
            }
            System.out.println("");
        }
    }

    private void printMatrix(Matrix matrix){
        for (int j = 0; j < matrix.getHeight(); j++) {
            for (int i = 0; i < matrix.getWidth(); i++) {
                System.out.print(matrix.get(i,j) + " ");
            }
            System.out.println("");
        }
    }
}
