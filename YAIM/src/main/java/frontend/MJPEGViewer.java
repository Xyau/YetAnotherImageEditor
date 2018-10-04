package frontend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

class MJPEGViewer extends ImageView {

    private VideoStream vs;

    private String uri ;
    MJPEGViewer() {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-background-color: cornsilk;");
        layout.setAlignment(Pos.CENTER);
//        layout.getChildren().setAll(
//                viewer,
//                createControls(timeline)
//        );

        // setup a thread which processes the input stream.
        // the processing thread invokes onNewData for each new frame.
    }

//    private void onNewData(byte[] jpegData) {
//        imageView.set(
//                new Image(
//                        new ByteArrayInputStream(jpegData);
//      )
//    );
//    }

    private HBox createControls(final Timeline timeline) {
        Button play = new Button("Play");
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.play();
            }
        });

        Button pause = new Button("Pause");
        pause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                timeline.pause();
            }
        });

        Button restart = new Button("Restart");
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    timeline.stop();
//                    vs = new VideoStream(MOVIE_FILE);
                    timeline.playFromStart();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().setAll(
                play,
                pause,
                restart
        );
        return controls;
    }
    private Timeline createTimeline(final ImageView viewer) {
        final Timeline timeline = new Timeline();
        final byte[] buf = new byte[15000];

        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent event) {
                        try {
                            int len = vs.getnextframe(buf);
                            if (len == -1) {
                                timeline.stop();
                                return;
                            }
                            viewer.setImage(
                                    new Image(
                                            new ByteArrayInputStream(
                                                    Arrays.copyOf(buf, len)
                                            )
                                    )
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }),
                new KeyFrame(Duration.seconds(1.0/24))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);

        return timeline;
    }


}