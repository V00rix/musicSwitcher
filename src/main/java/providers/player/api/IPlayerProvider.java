package providers.player.api;

import java.io.File;


public interface IPlayerProvider {
    void setFile(File file);

    void setFile(String fileUri);

    void play();

    void stop();

    void pause();
}
