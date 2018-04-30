package providers.status.implementation;

import domain.StatusPackage;
import domain.enumeration.statuses.StatusBase;
import domain.enumeration.statuses.StatusGlobal;
import domain.enumeration.statuses.StatusLibrary;
import domain.enumeration.statuses.StatusPlayer;
import org.springframework.stereotype.Service;
import providers.IProviderBase;
import providers.status.api.IStatusProvider;

import java.io.OutputStream;

/**
 * Default status provider
 */
@Service
public class StatusProvider implements IStatusProvider, IProviderBase {

    private StatusPackage statusPackage;
    private StatusGlobal statusGlobal;
    private StatusLibrary statusLibrary;
    private StatusPlayer statusPlayer;


    //region Fields
    private StatusBase status;
    //endregion

    //region Constructor

    /**
     * New instance of default status provider
     */
    public StatusProvider() {
        this.statusPackage = new StatusPackage()
        this.setStatus("StatusProvider initialized");
    }
    //endregion

    //region Implementation
    @Override
    public StatusBase setStatus(StatusBase status) {
        if (status instanceof StatusGlobal) {
            this.status
        } else if (status instanceof StatusLibrary) {

        } else if (status instanceof StatusPlayer) {

        } else {

        }
        this.status = status;
        return this.getStatus();
    }

    @Override
    public StatusBase getStatus(OutputStream out) {
        return this.printStatus(out);
    }
    //endregion

    //region Helpers

    /**
     * Prints current status to output stream
     */
    private StatusBase printStatus(OutputStream out) {
        System.out.println(this.status);
        return this.status;
    }
    //endregion
}
