package providers.player.implementation;

import components.audioPlayer.api.IAudioPlayer;
import components.audioPlayer.implementation.AudioPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import providers.IProviderBase;
import providers.player.api.IPlayerProvider;
import providers.status.api.IStatusProvider;

import java.util.concurrent.CountDownLatch;

/**
 * Player provider implementation
 */
@Service
public class PlayerProvider implements IPlayerProvider, IProviderBase {
    //region Fields

    //region Providers

    private final IStatusProvider statusProvider;

    //endregion

    public final IAudioPlayer audioPlayer;

    //endregion

    //region Constructors

    /**
     * New instance on player provider
     *
     * @param statusProvider
     * @throws Exception
     */
    @Autowired
    public PlayerProvider(IStatusProvider statusProvider) throws Exception {
        checkProviders(statusProvider);

        this.statusProvider = statusProvider;

        CountDownLatch latch = new CountDownLatch(1);
        this.audioPlayer = new AudioPlayer(this.statusProvider, latch);
        latch.await(); // wait till the player is initialized, running and responding
    }

    //endregion

    //region Implementation

    @Override
    public IAudioPlayer audioPlayer() {
        return this.audioPlayer;
    }

    //endregion
}
