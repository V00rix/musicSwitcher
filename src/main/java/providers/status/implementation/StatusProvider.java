package providers.status.implementation;

import org.springframework.stereotype.Service;
import providers.IProviderBase;
import providers.status.api.IStatusProvider;

import java.io.OutputStream;
import java.util.concurrent.Callable;

/**
 * Default status provider
 */
@Service
public class StatusProvider implements IStatusProvider, IProviderBase {
    private String status;

    private int progressStepsTotal;
    private int progressStepsCompleted;

    /**
     * New instance of default status provider
     */
    public StatusProvider() {
        progressStepsTotal = -1;
        progressStepsCompleted = 0;
        this.setStatus("StatusProvider initialized");
    }

    @Override
    public String setStatus(String status) {
        this.status = status;
        return this.getStatus();
    }

    @Override
    public String getStatus(OutputStream out) {
        return this.printStatus(out);
    }

    @Override
    public <T> T trackProgress(Callable<T> f, int steps) throws Exception {
        progressStepsTotal = steps;
        progressStepsCompleted = 0;

        T res = f.call();

        progressStepsTotal = -1;

        return res;
    }

    /**
     * Prints current status to output stream
     */
    private String printStatus(OutputStream out) {
        System.out.println(this.status + this.prettyProgress(this.progressStepsTotal, this.progressStepsCompleted));
        return this.status;
    }

    /**
     * Converts progress data to a string
     * @param progressStepsTotal Total steps count
     * @param progressStepsCompleted Completed steps count
     * @return Progress as a string (e.g. 55%)
     */
    private String prettyProgress(int progressStepsTotal, int progressStepsCompleted) {
        return this.progressStepsTotal > -1
                ? " " + (int) (progressStepsCompleted / progressStepsTotal * 100.0) + "%"
                : "";
    }
}
