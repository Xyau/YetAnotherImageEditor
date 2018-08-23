package backend;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import transformations.Transformation;

import java.util.ArrayList;
import java.util.List;

public class TransformationManager {
    private List<ToggableTransformation> transformations;
    private Image initialImage;
    private WritableImage imageResult;

    public Integer size(){
        return transformations.size();
    }

    public TransformationManager(Image initialImage) {
        this.transformations = new ArrayList<>();
        this.initialImage = initialImage;
        imageResult = ImageUtils.copyImage(initialImage);
    }

    public Image getImage(){
        if(imageResult == null){
            imageResult = recalculateImage();
        }
        return imageResult;
    }

    public boolean toggleTransformation(Integer index){
        Boolean result = transformations.get(index).toggle();
        imageResult = recalculateImage();
        return result;
    }

    public Integer addTransformation(Transformation transformation){
        if(transformation == null) throw new IllegalStateException("Null transformation");
        ToggableTransformation toggableTransformation = new ToggableTransformation(transformation);
        transformations.add(toggableTransformation);
        imageResult =  transformation.transform(imageResult);
        toggableTransformation.setLastProcesedImage(ImageUtils.copyImage(imageResult));

        return transformations.size()-1;
    }

    private WritableImage recalculateImage(){
        WritableImage result = ImageUtils.copyImage(initialImage);
        for (int i = 0; i < transformations.size(); i++) {
            ToggableTransformation transformation = transformations.get(i);
            if(transformation.isEnabled()){
                result = transformation.getTransformation().transform(result);
                transformation.setLastProcesedImage(ImageUtils.copyImage(result));
            }
        }
        return result;
    }

    public Image getImageAt(Integer index){
        return transformations.get(index).getLastProcesedImage();
    }

    public Boolean isTransformationEnabled(Integer index){
        return transformations.get(index).isEnabled();
    }

    public void setInitialImage(Image initialImage) {
        this.initialImage = initialImage;
        recalculateImage();
    }

    private class ToggableTransformation{
        private Transformation transformation;

        private Boolean enabled;

        private Image lastProcesedImage;

        public Image getLastProcesedImage() {
            return lastProcesedImage;
        }

        public void setLastProcesedImage(Image lastProcesedImage) {
            this.lastProcesedImage = lastProcesedImage;
        }

        public Transformation getTransformation() {
            return transformation;
        }

        public Boolean isEnabled() {
            return enabled;
        }

        public ToggableTransformation(Transformation transformation) {
            this.transformation = transformation;
            enabled = true;
        }

        public Boolean toggle(){
            return enabled = !enabled;
        }
    }
}
