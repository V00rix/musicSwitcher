package providers.playList.implementation;

import components.util.IRichConsole;
import components.audioPlayer.api.IAudioPlayer;
import domain.AudioFile;
import domain.exeptions.BaseException;
import domain.exeptions.checks.BoundariesCheck;
import domain.exeptions.checks.NullCheck;
import domain.exeptions.UnprovidedException;
import domain.statuses.PlayerStatus;
import providers.IProviderBase;
import providers.library.api.ILibraryProvider;
import providers.playList.api.IPlayListProvider;
import providers.control.api.IControlProvider;
import providers.status.api.IStatusProvider;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Play list provider implementation
 */
public class PlayListProvider implements IPlayListProvider, IProviderBase, IRichConsole {
    //region Fields
    //region Providers
    private final IStatusProvider statusProvider;
    private final IControlProvider playerProvider;
    private final ILibraryProvider libraryProvider;
    private final IAudioPlayer audioPlayer;
    //endregion

    private PlayerStatus playerStatus = new PlayerStatus();
    private ArrayList<Integer> ids;
    private AudioFile currentFile;
    private int currentFileIndex;
    private boolean playing = false;
    //endregion

    //region Constructor

    /**
     * New instance of play list provider
     *
     * @param statusProvider Status provider
     * @param playerProvider Player provider
     * @throws UnprovidedException UnprovidedException
     */
    public PlayListProvider(IStatusProvider statusProvider, IControlProvider playerProvider, ILibraryProvider libraryProvider) throws UnprovidedException {
        this.checkProviders(statusProvider, playerProvider, libraryProvider);
        this.playerProvider = playerProvider;
        this.libraryProvider = libraryProvider;
        this.statusProvider = statusProvider;
        this.audioPlayer = playerProvider.audioPlayer();
        Runnable f = () -> {
            System.out.println(style("updating control data...", BoldColors.YELLOW));
            this.playerStatus.set(this.audioPlayer.getVolume(), this.currentFileIndex, this.ids, this.audioPlayer.getSeek(), this.playing);
        };
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                f.run();
            }
        }, 0, 500);
    }
    //endregion

    //region Implementation
    @Override
    public void setPlaylist(ArrayList<Integer> ids) throws BaseException {
        this.audioPlayer.stop();
        try {
            NullCheck.check(ids);
            this.ids = ids;
        } catch (NullCheck.NullOrEmptyException ignored) {
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
        this.playFile(this.currentFileIndex + 1 < this.ids.size() ? this.currentFileIndex + 1 : 0);
    }

    @Override
    public void playPrevious() throws BaseException {
        this.playFile(this.currentFileIndex - 1 >= 0 ? this.currentFileIndex - 1 : this.ids.size() - 1);
    }

    @Override
    public void playFile(int index) throws BaseException {
        this.audioPlayer.stop();
        this.currentFileIndex = BoundariesCheck.check(index, this.ids);
        this.currentFile = this.libraryProvider.file(this.currentFileIndex);
        this.audioPlayer.setFile(this.currentFile);
        this.onPlay();
    }

    @Override
    public PlayerStatus playerStatus() {
        return this.playerStatus;
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
