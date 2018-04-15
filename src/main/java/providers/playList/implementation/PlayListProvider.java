package providers.playList.implementation;

import components.audioPlayer.api.IAudioPlayer;
import domain.AudioFile;
import domain.exeptions.NullOrEmpty;
import domain.exeptions.UnprovidedException;
import providers.IProviderBase;
import providers.playList.api.IPlayListProvider;
import providers.player.api.IPlayerProvider;
import providers.status.api.IStatusProvider;

import java.util.ArrayList;

/**
 * Play list provider implementation
 */
public class PlayListProvider implements IPlayListProvider, IProviderBase {
    //region Fields

    //region Providers

    private final IStatusProvider statusProvider;
    private final IPlayerProvider playerProvider;

    //endregion

    private ArrayList<AudioFile> files;
    private AudioFile currentFile;
    private int currentFileIndex;
    private boolean playing = false;

    private final IAudioPlayer audioPlayer;

    //endregion

    //region Constructor

    /**
     * New instance of play list provider
     *
     * @param statusProvider Status provider
     * @param playerProvider Player provider
     * @throws UnprovidedException UnprovidedException
     */
    public PlayListProvider(IStatusProvider statusProvider, IPlayerProvider playerProvider) throws UnprovidedException {
        this.checkProviders(statusProvider, playerProvider);
        this.playerProvider = playerProvider;
        this.statusProvider = statusProvider;
        this.audioPlayer = playerProvider.audioPlayer();
        this.statusProvider.setStatus("PlayListProvider initialized");
    }

    //endregion

    //region Implementation

    @Override
    public void setList(ArrayList<AudioFile> fileList) throws Exception {
        NullOrEmpty.check(fileList);
        this.files = fileList;

        this.currentFileIndex = 0;
        this.currentFile = this.files.get(this.currentFileIndex);

        this.audioPlayer.setFile(this.currentFile.file);

        this.statusProvider.setStatus("Basic playlist set");
    }

    @Override
    public void togglePlay() {
        if (playing) {
            this.audioPlayer.pause();
            playing = false;
        } else {
            this.audioPlayer.play();
            playing = true;
        }
    }

    @Override
    public void playNext() {
        this.audioPlayer.stop();
        this.currentFileIndex = this.currentFileIndex + 1 < this.files.size() ? this.currentFileIndex + 1 : 0;
        this.currentFile = this.files.get(this.currentFileIndex);
        this.audioPlayer.setFile(this.currentFile.file);
        this.playing = true;
        this.audioPlayer.play();
    }

    @Override
    public void playPrevious() {
        this.audioPlayer.stop();
        this.currentFileIndex = this.currentFileIndex - 1 >= 0 ? this.currentFileIndex - 1 : this.files.size() - 1;
        this.currentFile = this.files.get(this.currentFileIndex);
        this.audioPlayer.setFile(this.currentFile.file);
        this.playing = true;
        this.audioPlayer.play();
    }

    //endregion
}
