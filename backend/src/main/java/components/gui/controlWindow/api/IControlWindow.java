package components.gui.controlWindow.api;

import java.util.function.Consumer;

public interface IControlWindow {

    void setHost(String host);

    void changeDirectory();

    void setOnExit(Runnable r);

    void setOnDirectoryChanged(Consumer<String> c);
}
