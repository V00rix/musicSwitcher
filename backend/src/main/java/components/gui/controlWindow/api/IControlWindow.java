package components.gui.controlWindow.api;

import javafx.stage.Stage;

import java.util.function.Consumer;

public interface IControlWindow {

    public void setHost(String host);

    public void changeDirectory();

    public void setOnExit(Runnable r);

    public void setOnDirectoryChanged(Consumer<String> c);
}
