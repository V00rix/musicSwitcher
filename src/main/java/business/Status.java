package business;

public class Status {
    public static String status = "initialized";

    /* Utils */
    public static void SetStatus(String status) {
        System.out.println(status);
        Status.status = status;
    }
}
