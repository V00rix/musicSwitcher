package providers.control.api;

import components.audioPlayer.api.IAudioPlayer;

/**
 * API for communication with java swing window/thread
 */
public interface IControlProvider {
    /**
     * Get audio control API
     * @return Audio control
     */
    IAudioPlayer audioPlayer();

    // stuff like close window, refresh, etc. (requests, e.g. over Http)
}
