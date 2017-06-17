package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    private String filePath;

    @FXML
    private Slider slider;

    @FXML
    private Slider seekSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void openFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File (*.mp4)","*.mp4");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toURI().toString();

        if (filePath != null){
            Media media = new Media(filePath);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitHeightProperty();

            width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));

            slider.setValue(mediaPlayer.getVolume() * 100);
            slider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(slider.getValue() / 100);
                }
            });

            seekSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    if (seekSlider.isPressed()){
                        mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(seekSlider.getValue() / 100));
                    }
                }
            });

            mediaPlayer.play();
        }
    }
    @FXML
    public void play(ActionEvent actionEvent) {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    @FXML
    public void pause(ActionEvent actionEvent) {
        mediaPlayer.pause();
    }

    @FXML
    public void stop(ActionEvent actionEvent) {
        mediaPlayer.stop();
    }

    @FXML
    public void fastBack(ActionEvent actionEvent) {
        mediaPlayer.setRate(0.75);
    }

    @FXML
    public void fasterBack(ActionEvent actionEvent) {
        mediaPlayer.setRate(0.5);
    }

    @FXML
    public void fastForward(ActionEvent actionEvent) {
        mediaPlayer.setRate(1.5);
    }

    @FXML
    public void fasterForward(ActionEvent actionEvent) {
        mediaPlayer.setRate(2);
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

}
