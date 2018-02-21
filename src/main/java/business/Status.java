package business;

import java.util.concurrent.CountDownLatch;

public class Status {
    public static CountDownLatch playerLatch = new CountDownLatch(1);
    public static String status = "initialized";

    /* Utils */
    public static void SetStatus(String status) {
        System.out.println(status);
        Status.status = status;
    }
}
