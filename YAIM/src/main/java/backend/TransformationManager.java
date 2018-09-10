package backend;

import backend.utils.ImageUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import backend.transformators.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if(result && index == transformations.size()-1){
            imageResult = ImageUtils.copyImage(transformations.get(index).getLastProcesedImage());
        } else {
            imageResult = recalculateImage(index);
        }
        return result;
    }

    public Integer addTransformation(Transformation transformation){
        if(transformation == null) throw new IllegalStateException("Null transformation");
        ToggableTransformation toggableTransformation = new ToggableTransformation(transformation);
        imageResult = transformation.transform(imageResult);
        toggableTransformation.setLastProcesedImage(ImageUtils.copyImage(imageResult));
        transformations.add(toggableTransformation);

        return transformations.size()-1;
    }

    private WritableImage recalculateImage() {
        return recalculateImage(0);
    }

    //We have to find a precalculated image from a previous transformation that is enabled
    //And return a copy of that image
    private WritableImage getCopyOfLastCorrectImage(Integer startIndex){
        if(startIndex > transformations.size() || startIndex < 0 ) {
            throw new IllegalStateException("illegal index" + startIndex + " max index: " + transformations.size());
        }

        //If we need to recalculate 0, we have to use initial image
        if(startIndex == 0 ){
            return ImageUtils.copyImage(initialImage);
        }
        //Start looking for a enabled transformation that is before the startIndex
        startIndex -= 1;
        //Try to find an enabled transformation
        for (int i = startIndex; i >= 0 ; i--) {
            if(transformations.get(i).isEnabled()){
                return ImageUtils.copyImage(transformations.get(i).getLastProcesedImage());
            }
        }
        //If no transformation was enabled, return the initial image, as there is no valid lastProcecedImage
        return ImageUtils.copyImage(initialImage);
    }

    public String getDescription(Integer index){
        if(index <0 || index>transformations.size()){
            throw new IllegalStateException("Illegal index for description");
        }
        ToggableTransformation transformation = transformations.get(index);
        return transformation.getTransformation().getDescription() + (transformation.isEnabled()?"(ENABLED)":"(DISABLED)");
    }

    private WritableImage recalculateImage(Integer startIndex){
        WritableImage result = getCopyOfLastCorrectImage(startIndex);
        for (int i = startIndex; i < transformations.size(); i++) {
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

    public WritableImage setInitialImage(Image initialImage) {
        this.initialImage = initialImage;
        imageResult = ImageUtils.copyImage(initialImage);
        return imageResult = recalculateImage();
    }

    public void deleteUnusedTransformations(){
        transformations = transformations.stream()
                .filter(ToggableTransformation::isEnabled)
                .collect(Collectors.toList());
        imageResult = recalculateImage();
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
