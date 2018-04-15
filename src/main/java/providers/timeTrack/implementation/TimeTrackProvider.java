package providers.timeTrack.implementation;

import components.IRichConsole;
import domain.exeptions.UnprovidedException;
import providers.IProviderBase;
import providers.status.api.IStatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Time track implementation
 */
public class TimeTrackProvider implements ITimeTrackProvider, IProviderBase, IRichConsole {
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
        this.statusProvider.setStatus("TimeTrackProvider initialized");
    }

    //endregion

    //region Implementation

    @Override
    public <T> T track(Callable<T> f, String startMessage, String endMessage) throws Exception {
        long mStart = System.currentTimeMillis();
        this.statusProvider.setStatus(String.format(startMessage, millisDate(mStart)));

        T res = f.call();

        long mEnd = System.currentTimeMillis();
        this.statusProvider.setStatus(String.format(endMessage, millisDate(mEnd), ((mEnd - mStart)) + " ms"));

        return res;
    }

    @Override
    public <T, R> ArrayList<R> trackProgress(ArrayList<T> items, Function<T, R> function, String taskName) {

        int progressStepsTotal = items.size();
        int progressStepsCompleted = 0;

        this.statusProvider.setStatus(taskName + " has started. " + style("Progress: 0%", BoldColors.CYAN));

        ArrayList<R> result = new ArrayList<R>();

        for (T item : items) {

            result.add(function.apply(item));
            progressStepsCompleted++;


            this.statusProvider.setStatus(taskName + " is in progress. "
                    + style("Progress: " + prettyProgress(progressStepsTotal, progressStepsCompleted), BoldColors.CYAN));
        }


        this.statusProvider.setStatus(taskName + " has finished: " + style("Progress: 100%", BoldColors.GREEN));

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
        return " " + (int) ((float)progressStepsCompleted / (float)progressStepsTotal * 100.0) + "%";
    }

    /**
     * Cast milliseconds to date string
     * @param millis Date in milliseconds
     * @return Date as string
     */
    private static String millisDate(long millis) {
        return dateFormat.format(new Date(millis));
    }

    //endregion
}