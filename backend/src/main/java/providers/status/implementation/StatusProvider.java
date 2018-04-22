package providers.status.implementation;

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

    private String status;

    //endregion

    //region Constructor

    /**
     * New instance of default status provider
     */
    public StatusProvider() {
        this.setStatus("StatusProvider initialized");
    }

    //endregion

    //region Implementation

    @Override
    public String setStatus(String status) {
        this.status = status;
        return this.getStatus();
    }

    @Override
    public String getStatus(OutputStream out) {
        return this.printStatus(out);
    }

    //endregion

    //region Helpers

    /**
     * Prints current status to output stream
     */
    private String printStatus(OutputStream out) {
        System.out.println(this.status);
        return this.status;
    }

    //endregion
}
