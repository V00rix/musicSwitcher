package providers.player.api;

import components.audioPlayer.api.IAudioPlayer;

/**
 * Player provider API
 */
public interface IPlayerProvider {
    /**
     * Get audio player instance
     * @return Audio player
     */
    IAudioPlayer audioPlayer();
}
