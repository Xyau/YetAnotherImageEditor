package frontend;

import backend.utils.ImageUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import repositories.ImagesRepository;
import backend.TransformationManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import backend.transformators.Transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TransformationManagerView extends GridPane {

    public static Integer TRANSFORMATION_SIZE = 30;

    private TransformationManager transformationManager;
    private List<ImageView> transformationImageViews;
    private ImageView linkedImageView;
    private ImageView previewImageView;
    private Text description;
    private Cache<Transformation, Image> cache = CacheBuilder.newBuilder()
            .maximumSize(30)
            .build();

    public TransformationManagerView(TransformationManager transformationManager, ImageView linkedImageView, ImageView previewImageView) {
        this.transformationManager = transformationManager;
        this.linkedImageView = linkedImageView;
        this.previewImageView = previewImageView;
        transformationImageViews = new ArrayList<>();
        this.description = new Text("No transformations");
        add(description,0,1,10,1);
        setHgap(1.5);
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
            description.setText(transformationManager.getDescription(index));
        });
        pane.getChildren().addAll(imageView);
        imageView.setOnMouseEntered(e -> description.setText(transformationManager.getDescription(index)));
        return pane;
    }

    public void addTransformation(Transformation transformation){
        Integer index = transformationManager.addTransformation(transformation);
        Pane pane = getNewTransPane(index, transformationManager);
        add(pane,index,0);
        cache.invalidateAll();
    }


    public Image getImage(){
        return transformationManager.getImage();
    }

    public Image getPreview() {
        return previewImageView.getImage();
    }

    public void preview(Transformation activeTransformation) {
        Image image = null;
        try {
            image = cache.get(activeTransformation, () ->
                    activeTransformation.transform(ImageUtils.copyImage(getImage())));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(image == null){
            image =activeTransformation.transform(ImageUtils.copyImage(getImage()));
            System.out.println("ERROR, CACHE FAILED");
        }
        previewImageView.setImage(image);
    }

    public void setInitialImage(Image initialImage){
        transformationManager.setInitialImage(initialImage);
        linkedImageView.setImage(transformationManager.getImage());
        cache.invalidateAll();
        repopulateTransformationsView();
    }

    private void repopulateTransformationsView(){
        getChildren().clear();
        transformationImageViews = new ArrayList<>();
        add(description,0,1,10,1);

        for (int i = 0; i < transformationManager.size(); i++) {
            Pane pane = getNewTransPane(i,transformationManager);
            add(pane,i,0);
        }
    }

    public void deleteUnusedTransformations(){
        transformationManager.deleteUnusedTransformations();
        repopulateTransformationsView();
        linkedImageView.setImage(transformationManager.getImage());
    }
}
