package frontend;

import backend.ImageUtils;
import javafx.scene.image.Image;
import repositories.ImagesRepository;
import backend.TransformationManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import transformations.Transformation;

import java.util.ArrayList;
import java.util.List;

public class TransformationManagerView extends GridPane {

    public static Integer TRANSFORMATION_SIZE = 30;

    private TransformationManager transformationManager;
    private List<ImageView> transformationImageViews;
    private ImageView linkedImageView;
    private WritableImageView previewImageView;

    public TransformationManagerView(TransformationManager transformationManager, ImageView linkedImageView, WritableImageView previewImageView) {
        this.transformationManager = transformationManager;
        this.linkedImageView = linkedImageView;
        this.previewImageView = previewImageView;
        transformationImageViews = new ArrayList<>();
    }

    private Pane getNewTransPane(Integer index, TransformationManager transformationManager) {
        Pane pane = new Pane();
        ImageView imageView = new ImageView();
        transformationImageViews.add(imageView);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(TRANSFORMATION_SIZE);
        imageView.setFitWidth(TRANSFORMATION_SIZE);
        imageView.setImage(transformationManager.getImageAt(index));

        imageView.setOnMouseClicked(event ->{
            Boolean result = transformationManager.toggleTransformation(index);
            linkedImageView.setImage(transformationManager.getImage());
            imageView.setImage(result?transformationManager.getImageAt(index):ImagesRepository.RED_CROSS);
            for (int i = index+1; i < transformationManager.size(); i++) {
                if (transformationManager.isTransformationEnabled(i)) {
                    transformationImageViews.get(i).setImage(transformationManager.getImageAt(i));
                }
            }
        });
        pane.getChildren().addAll(imageView);
        return pane;
    }

    public void addTransformation(Transformation transformation){
        Integer index = transformationManager.addTransformation(transformation);
        Pane pane = getNewTransPane(index, transformationManager);
        add(pane,index,0);
    }

    public Image getImage(){
        return transformationManager.getImage();
    }

    public void preview(Transformation activeTransformation) {
        previewImageView.setWritableImage(activeTransformation.transform(previewImageView.getWritableImage()));
    }

    public void setInitialImage(Image initialImage){
        linkedImageView.setImage(initialImage);
        transformationManager.setInitialImage(initialImage);
    }
}