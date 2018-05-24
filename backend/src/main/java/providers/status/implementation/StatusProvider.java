package providers.status.implementation;

import domain.statuses.StatusBase;
import org.springframework.stereotype.Service;
import providers.IProviderBase;
import providers.status.api.IStatusProvider;

import java.io.PrintStream;

/**
 * Default status provider
 */
@Service
public class StatusProvider implements IStatusProvider, IProviderBase {

    //region Fields
    private StatusBase status;
    //endregion

    //region Constructor
    /**
     * New instance of default status provider
     */
    public StatusProvider() {
        System.out.println("StatusProvider initialized");
    }
    //endregion

    //region Implementation
    @Override
    public StatusBase setStatus(StatusBase status) {
        this.status = status;
        return this.getStatus();
    }

    @Override
    public StatusBase getStatus(PrintStream out) {
        return this.printStatus(out);
    }
    //endregion

    //region Helpers

    /**
     * Prints current status to output stream
     */
    private StatusBase printStatus(PrintStream out) {
        out.println(this.status.status.text());
        return this.status;
    }
    //endregion
}
