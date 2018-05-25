package components;

import javafx.application.Application;
import javafx.util.Pair;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public abstract class ISequentialLaunch extends Application {
    public CountDownLatch latch;

    public <T extends ISequentialLaunch> void start(Class<T> appClass, CountDownLatch latch) {
        this.latch = latch;
        new Thread(() -> Application.launch(appClass)).start();
    }
}
