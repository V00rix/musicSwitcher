package business.utiliy;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Status {
    public static CountDownLatch playerLatch = new CountDownLatch(1);
    public static String status = "initialized";

    public static int progressTotal = -1;
    public static int progressCompleted;

    public static void SetStatus(String status) {
        System.out.println(status + (progressTotal > -1
                        ? " " + (int) ((float) progressCompleted / (float) progressTotal * 100.0) + "%"
                        : ""));
        Status.status = status;
    }

    public static <T> T TrackProgress(Callable<T> f, int steps) throws Exception {
        progressTotal = steps;
        progressCompleted = 0;

        T res = f.call();

        progressTotal = -1;

        return res;
    }
}
