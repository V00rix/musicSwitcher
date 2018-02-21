package business.utiliy;

import java.util.Date;
import java.util.concurrent.Callable;

import static business.utiliy.Status.SetStatus;

public class TimeTrack {
    public static <T> T Track(Callable<T> f) throws Exception {
        return Track(f, "Unanimous operation");
    }

    public static <T> T Track(Callable<T> f, String taskName) throws Exception {
        return Track(f, taskName + " has started at %s",
                taskName + " has finished at %s.\n" + taskName + " has taken %s");
    }


    public static <T> T Track(Callable<T> f, String startMessage, String endMessage) throws Exception {
        Date mStart = new Date();
        SetStatus(String.format(startMessage, mStart));

        T res = f.call();

        Date mEnd = new Date();
        SetStatus(String.format(endMessage, mEnd, (double) (mEnd.getTime() - mStart.getTime()) / 1000.0));

        return res;
    }
}
