package components.gui.controlWindow.implementation;

import components.audioPlayer.api.IAudioPlayer;
import components.audioPlayer.implementation.AudioPlayer;
import components.gui.controlWindow.api.IControlWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class ControlWindow extends Application implements IControlWindow {
    //region Public references for sequential launch
    public static CountDownLatch latch = new CountDownLatch(1);
    public static IAudioPlayer audioPlayer;
    public static IControlWindow instance;
    //endregion

    //region Fields

    //region Scene controls
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label hostLabel;
    @FXML
    private Label pathLabel;
    @FXML
    private Label progressLabel;

    //endregion
    private String host = null;
    private Stage stage;
    private String path = null;
    private Consumer<String> afterDirectoryChanged;
    private Runnable afterExit;
    //endregion

    //region Basic initialization
    public ControlWindow() {
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException, IOException {


        // Launch audio player
        new AudioPlayer().start(this.stage = primaryStage);
        AudioPlayer.latch.await();
        audioPlayer = AudioPlayer.instance;

        // Configure self
        instance = this;

        // Build scene
        this.stage = new Stage();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource("gui/sample.fxml")));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene;
        this.stage.setScene(scene = new Scene(root, 450, 300));
        this.stage.initOwner(primaryStage);
        this.stage.setTitle("Music Switcher Controls");
        this.stage.setResizable(false);
        this.stage.setOnCloseRequest((s) -> onExit());
        this.stage.show();

        // Mark self as available
        ControlWindow.latch.countDown();
    }
    //endregion

    //region Event handlers

    /**
     * Directory label click handler
     *
     * @param mouseEvent Mouse event
     */
    public void onDirectoryLabelClicked(MouseEvent mouseEvent) {
        this.changeDirectoryDialog();
    }

    /**
     * Host label click handler
     *
     * @param mouseEvent Mouse event
     */
    public void onHostLabelClicked(MouseEvent mouseEvent) {
        if (this.host != null)
            getHostServices().showDocument("http://" + this.host);
    }
    //endregion

    //region Implementation
    @Override
    public void setHost(String host) {
        Platform.runLater(() -> this.hostLabel.setText(this.host = host));
    }

    @Override
    public void changeDirectoryDialog() {
        Platform.runLater(() -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Set music root path");
            File f = directoryChooser.showDialog(this.stage);
            if (f != null) {
                this.afterDirectoryChanged.accept(f.getAbsolutePath());
                if (this.afterDirectoryChanged != null) {
                    this.pathLabel.setText(f.getAbsolutePath());
                }
            }
        });
    }

    @Override
    public void setDirectoryPath(String path) {
        Platform.runLater(() -> this.pathLabel.setText("Root: " + path));
    }

    @Override
    public void setOnExit(Runnable r) {
        this.afterExit = r;
    }

    @Override
    public void setOnDirectoryChanged(Consumer<String> c) {
        this.afterDirectoryChanged = c;
    }

    @Override
    public void setProgress(double progress, String text) {
        Platform.runLater(() -> {
            this.progressBar.setProgress(progress);
            this.progressLabel.setText(text);
        });
    }
    //endregion

    //region Helpers

    /**
     * On exit logic
     */
    private void onExit() {
        try {
            audioPlayer.terminate();
            Platform.exit();
            if (this.afterExit != null)
                this.afterExit.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
