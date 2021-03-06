package providers.timeTrack.implementation;

import components.util.RichConsole;
import domain.exeptions.UnprovidedException;
import providers.IProviderBase;
import providers.status.api.IStatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

import static components.util.RichConsole.style;

/**
 * Time track implementation
 */
public class TimeTrackProvider implements ITimeTrackProvider, IProviderBase {
    //region Fields

    //region Providers

    private final IStatusProvider statusProvider;

    //endregion

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //endregion

    //region Constructors

    /**
     * New instance
     *
     * @param statusProvider Status provider
     * @throws UnprovidedException UnprovidedException
     */
    public TimeTrackProvider(IStatusProvider statusProvider) throws UnprovidedException {
        this.checkProviders(statusProvider);
        this.statusProvider = statusProvider;
    }

    //endregion

    //region Implementation

    @Override
    public <T> T track(Callable<T> f, String startMessage, String endMessage) throws Exception {
        long mStart = System.currentTimeMillis();
        System.out.println((String.format(startMessage, millisDate(mStart))));

        T res = f.call();

        long mEnd = System.currentTimeMillis();
        System.out.println((String.format(endMessage, millisDate(mEnd), ((mEnd - mStart)) + " ms")));

        return res;
    }

    @Override
    public <T, R> ArrayList<R> trackProgress(ArrayList<T> items, TriFunction<T, Double, String, R> function, String taskName) {
        int progressStepsTotal = items.size();
        int progressStepsCompleted = 0;

        System.out.println((taskName + " has started. " + style("Progress: 0%", RichConsole.BoldColors.CYAN)));

        ArrayList<R> result = new ArrayList<R>();

        Thread thread = Thread.currentThread();

        for (T item : items) {
            if (thread.isInterrupted())
                break;

            result.add(function.apply(item, (double) progressStepsCompleted / (double) progressStepsTotal, prettyProgress(progressStepsTotal, progressStepsCompleted)));
            progressStepsCompleted++;

            System.out.println((taskName + " is in progress. "
                    + style("Progress: " + prettyProgress(progressStepsTotal, progressStepsCompleted), RichConsole.BoldColors.CYAN)));
        }

        System.out.println((taskName + " has finished: " + style("Progress: 100%", RichConsole.BoldColors.GREEN)));
        return result;
    }

    //endregion

    //region Helpers

    /**
     * Converts progress data to a string
     *
     * @param progressStepsTotal     Total steps count
     * @param progressStepsCompleted Completed steps count
     * @return Progress as a string (e.g. 55%)
     */
    private String prettyProgress(int progressStepsTotal, int progressStepsCompleted) {
        return " " + (int) ((float) progressStepsCompleted / (float) progressStepsTotal * 100.0) + "%";
    }

    /**
     * Cast milliseconds to date string
     *
     * @param millis Date in milliseconds
     * @return Date as string
     */
    private static String millisDate(long millis) {
        return dateFormat.format(new Date(millis));
    }

    //endregion
}
