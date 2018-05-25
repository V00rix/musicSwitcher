package providers.control.implementation;

import components.audioPlayer.api.IAudioPlayer;
import components.audioPlayer.implementation.AudioPlayer;
import components.gui.controlWindow.api.IControlWindow;
import components.gui.controlWindow.implementation.ControlWindow;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import providers.IProviderBase;
import providers.control.api.IControlProvider;
import providers.status.api.IStatusProvider;

import javax.naming.ldap.Control;
import java.net.InetAddress;

/**
 * Player provider implementation
 */
@Service
public class ControlProvider implements IControlProvider, IProviderBase {
    //region Fields
    //region Providers
    private final IStatusProvider statusProvider;

    //endregion
    public final IControlWindow controlWindow;
    public final IAudioPlayer audioPlayer;
    //endregion

    //region Constructors

    /**
     * New instance on control provider
     *
     * @param statusProvider
     * @throws Exception
     */
    @Autowired
    public ControlProvider(IStatusProvider statusProvider) throws Exception {
        checkProviders(statusProvider);

        this.statusProvider = statusProvider;

        ControlWindow.latch.await(); // wait till the control is initialized, running and responding
        this.controlWindow = ControlWindow.instance;
        this.audioPlayer = ControlWindow.audioPlayer;

        var host = InetAddress.getLocalHost().getHostAddress();

        // todo: change to auto hosting
        this.controlWindow.setHost(host + ":8190");
    }
    //endregion

    //region Implementation
    @Override
    public IAudioPlayer audioPlayer() {
        return this.audioPlayer;
    }
    //endregion
}
