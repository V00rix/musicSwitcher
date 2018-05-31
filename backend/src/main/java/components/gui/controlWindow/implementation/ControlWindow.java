package components.gui.controlWindow.implementation;

import components.audioPlayer.api.IAudioPlayer;
import components.audioPlayer.implementation.AudioPlayer;
import components.gui.controlWindow.api.IControlWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class ControlWindow extends Application implements IControlWindow {
    public static CountDownLatch latch = new CountDownLatch(1);
    public static IAudioPlayer audioPlayer;
    public static IControlWindow instance;

    private Scene scene;
    private Label hostLabel;
    private Label pathLabel;
    private Button filePathButton;
    private Button closeButton;
    private String host = null;
    private Stage stage;
    private String path = null;
    private Consumer<String> afterDirectoryChanged;
    private Runnable afterExit;
    private Runnable afterInit;

    public ControlWindow() {
    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        // Launch audio player
        new AudioPlayer().start(this.stage = primaryStage);
        AudioPlayer.latch.await();
        audioPlayer = AudioPlayer.instance;

        // Configure self
        instance = this;
        this.createStage();

        // Mark self as available
        ControlWindow.latch.countDown();
    }

    public void setHost(String host) {
        Platform.runLater(() -> {
            this.hostLabel.setText(this.host = host);
        });
    }

    /**
     * Create gui layout
     */
    private void createStage() {
        latch.countDown();

        this.stage.setTitle("Music Switcher");

        GridPane root = new GridPane();

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(50);
        root.getRowConstraints().add(row);

        row = new RowConstraints();
        row.setPercentHeight(20);
        root.getRowConstraints().add(row);

        row = new RowConstraints();
        row.setPercentHeight(30);
        root.getRowConstraints().add(row);

        GridPane buttonsPane = new GridPane();

        row = new RowConstraints();
        row.setPercentHeight(60);
        buttonsPane.getRowConstraints().add(row);

        row = new RowConstraints();
        row.setPercentHeight(30);
        buttonsPane.getRowConstraints().add(row);

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        buttonsPane.getColumnConstraints().add(col);

        this.filePathButton = new Button("Select Directory");
        this.filePathButton.setOnMouseClicked(mouseEvent -> {
            this.changeDirectory();
        });
        buttonsPane.add(this.filePathButton, 0, 0);

        this.closeButton = new Button("Exit");
        this.closeButton.setOnMouseClicked(mouseEvent -> {
            onExit();
        });
        buttonsPane.add(this.closeButton, 1, 0);

        this.hostLabel = new Label("[HOST ADDRESS]");
        this.hostLabel.setOnMouseClicked(mouseEvent -> {
            if (this.host != null)
                getHostServices().showDocument("http://" + this.host);
        });
        root.add(this.hostLabel, 0, 0);

        this.pathLabel = new Label("FilePath");
        root.add(this.pathLabel, 0, 1);

        root.add(buttonsPane, 0, 2);

        this.scene = new Scene(root);
        this.scene.getStylesheets().add("gui/styles/styles.css");
        this.stage.setOnCloseRequest((s) -> onExit());
        this.stage.setScene(this.scene);
        this.stage.show();
    }

    @Override
    public void changeDirectory() {
        Platform.runLater(() -> {
            var directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Set music root path");
            var f = directoryChooser.showDialog(this.stage);
            this.pathLabel.setText(f.getAbsolutePath());

            if (this.afterDirectoryChanged != null)
                this.afterDirectoryChanged.accept(f.getAbsolutePath());
        });
    }

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

    @Override
    public void setOnExit(Runnable r) {
        this.afterExit = r;
    }

    @Override
    public void setOnDirectoryChanged(Consumer<String> c) {
        this.afterDirectoryChanged = c;
    }
}
