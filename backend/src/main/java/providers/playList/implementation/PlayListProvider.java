package providers.playList.implementation;

import components.IRichConsole;
import components.audioPlayer.api.IAudioPlayer;
import domain.AudioFile;
import domain.exeptions.BaseException;
import domain.exeptions.checks.BoundariesCheck;
import domain.exeptions.checks.NullCheck;
import domain.exeptions.UnprovidedException;
import providers.IProviderBase;
import providers.playList.api.IPlayListProvider;
import providers.player.api.IPlayerProvider;
import providers.status.api.IStatusProvider;

import java.util.ArrayList;

/**
 * Play list provider implementation
 */
public class PlayListProvider implements IPlayListProvider, IProviderBase, IRichConsole {
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
    public void setList(ArrayList<AudioFile> fileList) {
        try {
            NullCheck.check(fileList);

            this.files = fileList;

            this.currentFileIndex = 0;
            this.currentFile = this.files.get(this.currentFileIndex);

            this.audioPlayer.setFile(this.currentFile.file);

            this.statusProvider.setStatus("Basic playlist set");
        } catch (NullCheck.NullOrEmptyException e) {

            this.statusProvider.setStatus("No audio files to play");
        }
    }

    @Override
    public void togglePlay() {
        if (playing) {
            this.audioPlayer.pause();
            this.playing = false;
        } else {
            this.onPlay();
        }
    }

    @Override
    public void playNext() throws BaseException {
        this.playFile(this.currentFileIndex + 1 < this.files.size() ? this.currentFileIndex + 1 : 0);
    }

    @Override
    public void playPrevious() throws BaseException {
        this.playFile(this.currentFileIndex - 1 >= 0 ? this.currentFileIndex - 1 : this.files.size() - 1);
    }

    @Override
    public void playFile(int index) throws BaseException {
        this.audioPlayer.stop();
        this.currentFileIndex = BoundariesCheck.check(index, this.files);
        this.currentFile = this.files.get(this.currentFileIndex);
        this.audioPlayer.setFile(this.currentFile.file);
        this.onPlay();
    }

    //endregion


    //region Helpers

    /**
     * On play helper method
     */
    private void onPlay() {
        this.playing = true;
        this.audioPlayer.play(() -> {
            try {
                playNext();
            } catch (BaseException e) {
                e.printStackTrace();
            }
        });
    }
    //endregion
}
