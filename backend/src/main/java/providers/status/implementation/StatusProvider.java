package providers.status.implementation;

import domain.statuses.StatusPackage;
import domain.statuses.StatusBase;
import domain.statuses.StatusGlobal;
import domain.statuses.StatusLibrary;
import domain.statuses.StatusPlayer;
import org.springframework.stereotype.Service;
import providers.IProviderBase;
import providers.status.api.IStatusProvider;

import java.io.OutputStream;

/**
 * Default status provider
 */
@Service
public class StatusProvider implements IStatusProvider, IProviderBase {

    //region Fields
    private StatusPackage statusPackage;
    private StatusGlobal statusGlobal;
    private StatusLibrary statusLibrary;
    private StatusPlayer statusPlayer;
    private StatusBase status;
    //endregion

    //region Constructor
    /**
     * New instance of default status provider
     */
    public StatusProvider() {
        this.statusPackage = new StatusPackage(0,0,0);
        System.out.println("StatusProvider initialized");
    }
    //endregion

    //region Implementation
    @Override
    public StatusBase setStatus(StatusBase status) {
        if (status instanceof StatusGlobal) {
            this.statusPackage.update(StatusPackage.GLOBAL);
        } else if (status instanceof StatusLibrary) {
            this.statusPackage.update(StatusPackage.LIBRARY);
        } else if (status instanceof StatusPlayer) {
            this.statusPackage.update(StatusPackage.PLAYER);
        }
        this.status = status;
        return this.getStatus();
    }

    @Override
    public StatusBase getStatus(OutputStream out) {
        return this.printStatus(out);
    }

    @Override
    public StatusPackage statusPackage() {
        return this.statusPackage;
    }
    //endregion

    //region Helpers

    /**
     * Prints current status to output stream
     */
    private StatusBase printStatus(OutputStream out) {
        System.out.println(this.status.status.text());
        return this.status;
    }
    //endregion
}
