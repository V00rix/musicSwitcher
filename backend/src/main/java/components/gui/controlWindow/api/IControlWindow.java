package components.gui.controlWindow.api;

import java.util.function.Consumer;

public interface IControlWindow {

    // TODO: 31-May-18 javadoc

    void setHost(String host);

    void changeDirectoryDialog();

    void setDirectoryPath(String path);

    void setOnExit(Runnable r);

    void setOnDirectoryChanged(Consumer<String> c);

    void setProgress(double progress, String text);
}
